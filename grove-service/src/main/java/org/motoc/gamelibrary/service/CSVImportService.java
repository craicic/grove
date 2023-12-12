package org.motoc.gamelibrary.service;

import jakarta.persistence.EntityManagerFactory;
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
        persistCreators(rows);
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
        log.info(row.toString());
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
