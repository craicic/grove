package org.motoc.gamelibrary.service;

import jakarta.persistence.EntityManagerFactory;
import org.motoc.gamelibrary.domain.enumeration.CreatorRole;
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

    private final EntityManagerFactory emf;

    public CSVImportService(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public void importCSV(Path csvFilePath) {
        List<Row> rows = extractRowsFromCSV(csvFilePath);
        List<Row> oversizeGameRows = filterOversizeGame(rows);
        try {
            extractCreators(rows);
        } catch (IllegalStateException ise) {
            log.error("Error occurred while extracting creators from rows", ise);
        }
    }

    private List<CreatorValues> extractCreators(List<Row> oversizeGameRows) {
        List<CreatorValues> creatorList = new ArrayList<>();
        int nbOfFails = 0;
        for (Row row : oversizeGameRows) {
            CreatorValues creator;
            for (int i = 15; i < 18; i++) {
                String value = row.getValues().get(i);
                boolean isNotNull = value != null;
                if (isNotNull) {
                    if (value.matches(".*(&|(?i) ET ).*")) {
                        log.warn("Name value : " + value + " has been rejected because it contains a '&' or a 'ET'");
                        row.getValues().set(i, null);
                        nbOfFails++;
                    } else {
                        String[] parts = row.getValues().get(i).split(" ");
                        creator = new CreatorValues();
                        if (parts.length == 1) {
                            String[] split = parts[0].split("\\.");
                            if (split.length == 1) {
                                creator.setLastName(parts[0]);
                                row.getValues().set(i, "[lastName=" + parts[0] + ", lastName=null]");
                            } else {

                            }
                        } else if (parts.length == 2) {
                            row.getValues().set(i, "[firstName=" + parts[0] + ", lastName=" + parts[1] + "]");
                            creator.setFirstName(parts[0]);
                            creator.setLastName(parts[1]);
                        } else {
                            nbOfFails++;
                            StringBuilder sb = new StringBuilder("Unexpected number of parts. Parts are : ");
                            for (String part : parts) {
                                sb.append("[").append(part).append("]");
                            }
                            log.warn(sb.toString());
                            // throw new IllegalStateException(sb.toString());
                        }
                        if (i == 17) {
                            creator.setRole(CreatorRole.ILLUSTRATOR);
                        } else {
                            creator.setRole(CreatorRole.AUTHOR);
                        }
                        if (!creatorList.contains(creator)) {
                            creatorList.add(creator);
                        }
                    }
                }
            }
            //    log.info(row.toString());
        }
        creatorList.forEach(e -> log.info(e.toString()));
        log.info("Number of fails=" + nbOfFails);
        return creatorList;
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

    private void persistCreators(List<Row> rows) {

    }


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
