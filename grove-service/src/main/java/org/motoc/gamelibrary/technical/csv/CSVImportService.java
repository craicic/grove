package org.motoc.gamelibrary.technical.csv;

import jakarta.persistence.*;
import org.motoc.gamelibrary.domain.model.Creator;
import org.motoc.gamelibrary.technical.csv.object.processed.ProcessedCreator;
import org.motoc.gamelibrary.technical.csv.object.Row;
import org.motoc.gamelibrary.technical.csv.object.value.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Service
public class CSVImportService {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getCanonicalName());

    private List<AuthorValues> authorValuesList = new ArrayList<>();
    private List<IllustratorValues> illustratorValuesList = new ArrayList<>();
    private List<GameValues> gameValuesList = new ArrayList<>();
    private List<GameCopyValues> gameCopyValuesList = new ArrayList<>();
    private List<PublisherValues> publisherValuesList = new ArrayList<>();

    @PersistenceUnit
    private final EntityManagerFactory emf;
    private final RowProcessor rp;

    public CSVImportService(EntityManagerFactory emf, RowProcessor rp) {
        this.emf = emf;
        this.rp = rp;
    }


    public void importCSV(Path csvFilePath) {
        List<Row> rows = extractRowsFromCSV(csvFilePath);
        List<Row> oversizeGameRows = filterOversizeGame(rows);
        log.warn("Number of rows :" + oversizeGameRows.size());
        rp.mapToValues(rows, authorValuesList, illustratorValuesList, gameValuesList, gameCopyValuesList, publisherValuesList);

        List<ProcessedCreator> creators = new ArrayList<>();
        persistCreators(creators);
    }


    private List<Row> filterOversizeGame(List<Row> rows) {
        List<Row> oversizeGameRows = new ArrayList<>();
        for (Row row : rows) {
            if (Objects.equals(row.getValues().get(3), "GRAND JEU")) {
                oversizeGameRows.add(row);
            }
        }
        return oversizeGameRows;
    }

    // BEGINNING OF PERSIST CREATORS
    private void persistCreators(List<ProcessedCreator> values) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Creator> entities = valueToEntity(values);
        for (Creator creator : entities) {
            TypedQuery<Creator> query = em.createQuery(
                    "SELECT c FROM Creator as c WHERE c.lowerCaseFirstName = :firstName AND c.lowerCaseLastName = :lastName"
                    , Creator.class);
            query.setParameter("firstName", creator.getFirstName() != null ? creator.getLowerCaseFirstName() : null);
            query.setParameter("lastName", creator.getLowerCaseLastName());
            try {
                Creator result = query.getSingleResult();
                log.warn("Creator already exists in the database: " + result);
            } catch (NoResultException nre) {
                em.persist(creator);
            }
        }
        em.getTransaction().commit();
        em.close();
    }

    private List<Creator> valueToEntity(List<ProcessedCreator> values) {
        List<Creator> entities = new ArrayList<>();
        Creator entity;
        for(ProcessedCreator value : values) {
            entity = new Creator();
            entity.setFirstName(value.getFirstName());
            if (value.getFirstName() != null)
                entity.setLowerCaseFirstName(value.getFirstName().toLowerCase());
            entity.setLastName(value.getLastName());
            entity.setLowerCaseLastName(value.getLastName().toLowerCase());
            entity.setRole(value.getRole());
            entities.add(entity);
        }
        return entities;
    }
    // END OF PERSIST CREATORS


    public List<Row> extractRowsFromCSV(Path csvFilePath) {
        Instant start = Instant.now();
        List<Row> rows = new ArrayList<>();
        Map<Integer, Integer> lineLengthFrequency = new HashMap<>();
        try (BufferedReader lineReader = Files.newBufferedReader(csvFilePath, StandardCharsets.UTF_8)) {
            lineReader.readLine(); // skip header line
            String lineText;
            while ((lineText = lineReader.readLine()) != null) {
                rows.add(processLine(lineText, lineLengthFrequency));
            }
        } catch (IOException e) {
            log.warn(e.getMessage());
        }
        validateLineLengthVariation(lineLengthFrequency);
        Instant end = Instant.now();
        Duration d = Duration.between(start, end);
        log.info("Extract csv into " + rows.size() + " rows. Processing time: " + d.toMillis() + " milliseconds");
        return rows;
    }

    private Row processLine(String lineText, Map<Integer, Integer> lineLengthFrequency) {
        String[] line = lineText.split(";");
        Row row = new Row();
        lineLengthFrequency.put(line.length, lineLengthFrequency.getOrDefault(line.length, 0) + 1);
        for (String value : line) {
            String valueStripped = value.strip();
            row.addValue(valueStripped.isBlank() ? null : valueStripped);
        }
        return row;
    }

    private void validateLineLengthVariation(Map<Integer, Integer> lineLengthFrequency) {
        if (lineLengthFrequency.size() > 1) {
            // if lineLengthFrequency have multiple entry, we log and throw an IllegalStateException
            Set<Map.Entry<Integer, Integer>> entries = lineLengthFrequency.entrySet();
            for (Map.Entry<Integer, Integer> entry : entries) {
                log.info("Length : " + entry.getKey() + " -> " + entry.getValue() + " occurrences.");
            }
            throw new IllegalStateException("At least one row has a different number of values");
        }
    }

}
