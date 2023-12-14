package org.motoc.gamelibrary.service;

import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.motoc.gamelibrary.domain.enumeration.CreatorRole;
import org.motoc.gamelibrary.domain.model.Creator;
import org.motoc.gamelibrary.technical.csv.CreatorValues;
import org.motoc.gamelibrary.technical.csv.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class CSVImportService {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getCanonicalName());

    @PersistenceUnit
    private final EntityManagerFactory emf;

    public CSVImportService(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public void importCSV(Path csvFilePath) {
        List<Row> rows = extractRowsFromCSV(csvFilePath);
        List<Row> oversizeGameRows = filterOversizeGame(rows);
        log.warn("Number of rows :" + oversizeGameRows.size());
        List<CreatorValues> creators = extractCreators(oversizeGameRows);
        persistCreators(creators);
    }

    // BEGINNING OF CREATOR'S EXTRACTION
    private List<CreatorValues> extractCreators(List<Row> oversizeGameRows) {
        List<CreatorValues> creators = new ArrayList<>();
        int numberOfIgnoredEntries = 0;

        for (Row row : oversizeGameRows) {
            for (int i = 15; i < 18; i++) {
                numberOfIgnoredEntries = processRow(creators, row, i, numberOfIgnoredEntries);
            }
        }
        logIgnoredEntries(numberOfIgnoredEntries);
        logCreators(creators);
        return creators;
    }

    private int processRow(List<CreatorValues> creators, Row row, int i, int numberOfIgnoredEntries) {
        String value = row.getValues().get(i);

        if (value != null) {
            if (value.matches(".*(&|/|(?i) ET ).*")) {
                value = logAndNullifyValue(row, i, value);
                numberOfIgnoredEntries++;
            } else {
                CreatorValues creator = createCreator(row, i, value);
                if (creator != null && !creators.contains(creator)) {
                    creators.add(creator);
                } else if (creator != null && creators.contains(creator)) {
                    log.warn("A creator homonym already exists :" + creator);
                }
            }
        }
        return numberOfIgnoredEntries;
    }

    private void logIgnoredEntries(int numberOfIgnoredEntries) {
        log.warn("Number of ignored entries=" + numberOfIgnoredEntries);
    }

    private void logCreators(List<CreatorValues> creators) {
        creators.forEach(e -> log.trace(e.toString()));
    }

    private String logAndNullifyValue(Row row, int i, String value) {
        log.warn("Name value : " + value + " has been rejected because it contains a '&' or a 'ET' or '/'");
        return row.getValues().set(i, null);
    }

    private CreatorValues createCreator(Row row, int i, String value) {
        String[] parts = value.split(" ");
        CreatorValues creator = new CreatorValues();
        boolean isInvalid = false;

        if (parts.length == 1) {
            isInvalid = processSinglePart(row, i, parts, creator);
        } else if (parts.length == 2) {
            processDoubleParts(row, i, parts, creator);
        } else {
            logUnexpectedParts(parts);
            isInvalid = true;
        }

        if (i == 17) {
            creator.setRole(CreatorRole.ILLUSTRATOR);
        } else {
            creator.setRole(CreatorRole.AUTHOR);
        }
        return isInvalid ? null : creator;
    }

    private boolean processSinglePart(Row row, int i, String[] parts, CreatorValues creator) {
        String[] nameParts = parts[0].split("\\.");
        if (nameParts.length == 1) {
            row.getValues().set(i, "[lastName=" + parts[0] + ", lastName=null]");
            creator.setLastName(parts[0]);
        } else if (nameParts.length == 2) {
            row.getValues().set(i, "[firstName=" + nameParts[0] + ", lastName=" + nameParts[1] + "]");
            creator.setLastName(nameParts[1]);
            creator.setFirstName(nameParts[0]);
            log.info("Found value with dot :" + parts[0]);
        }
        return nameParts.length > 2;
    }

    private void processDoubleParts(Row row, int i, String[] parts, CreatorValues creator) {
        row.getValues().set(i, "[firstName=" + parts[0] + ", lastName=" + parts[1] + "]");
        creator.setFirstName(parts[0]);
        creator.setLastName(parts[1]);
    }

    private void logUnexpectedParts(String[] parts) {
        StringBuilder sb = new StringBuilder("Unexpected number of parts. Parts are : ");
        for (String part : parts) {
            sb.append("[").append(part).append("]");
        }
        log.warn(sb.toString());
    }
    // END OF CREATOR'S EXTRACTION

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
    private void persistCreators(List<CreatorValues> values) {
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

    private List<Creator> valueToEntity(List<CreatorValues> values) {
        List<Creator> entities = new ArrayList<>();
        Creator entity;
        for (CreatorValues value : values) {
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
        List<Row> rows = new ArrayList<>();
        Map<Integer, Integer> lineLengthFrequency = new HashMap<>();
        try (BufferedReader lineReader = Files.newBufferedReader(csvFilePath, StandardCharsets.UTF_8)) {
            lineReader.readLine(); // skip header line
            String lineText = null;
            while ((lineText = lineReader.readLine()) != null) {
                rows.add(processLine(lineText, lineLengthFrequency));
            }
        } catch (IOException e) {
            log.warn(e.getMessage());
        }
        validateLineLengthVariation(lineLengthFrequency);
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
