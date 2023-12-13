package org.motoc.gamelibrary.service;

import jakarta.persistence.EntityManagerFactory;
import org.motoc.gamelibrary.domain.dto.CreatorDto;
import org.motoc.gamelibrary.technical.Row;
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
        extractCreators(oversizeGameRows);
    }

    private List<CreatorDto> extractCreators(List<Row> oversizeGameRows) {
        List<CreatorDto> creators = new ArrayList<>();
        for (Row row : oversizeGameRows) {
            CreatorDto creator;
            for (int i = 15; i < 18; i++) {
                String value = row.getValues().get(i);
                if (value != null) {
                    if (value.matches(".*(&|(?i) ET ).*")) {
                        log.warn("Name : " + value + " has been rejected because it contains a '&' or a 'ET'");
                    } else {
                        String[] parts = row.getValues().get(i).split(" ");
                        creator = new CreatorDto();
                        if (parts.length <= 2) {
                            creator.setFirstName(parts[0]);
                        }
                        if (parts.length == 2) {
                            creator.setLastName(parts[1]);
                        }
                        if (!creators.contains(creator)) {
                            creators.add(creator);
                        }
                    }
                }
            }
        }
        creators.forEach(e -> log.info(e.toString()));
        return creators;
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
