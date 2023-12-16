package org.motoc.gamelibrary.technical.csv;

import org.motoc.gamelibrary.domain.enumeration.CreatorRole;
import org.motoc.gamelibrary.technical.csv.object.Row;
import org.motoc.gamelibrary.technical.csv.object.processed.ProcessedCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreatorExtractor {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getCanonicalName());

    public List<ProcessedCreator> extractCreators(List<Row> oversizeGameRows) {
        List<ProcessedCreator> creators = new ArrayList<>();
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

    private int processRow(List<ProcessedCreator> creators, Row row, int i, int numberOfIgnoredEntries) {
        String value = row.getValues().get(i);

        if (value != null) {
            if (value.matches(".*(&|/|(?i) ET ).*")) {
                logAndNullifyValue(row, i, value);
                numberOfIgnoredEntries++;
            } else {
                ProcessedCreator creator = createCreator(row, i, value);
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

    private void logCreators(List<ProcessedCreator> creators) {
        creators.forEach(e -> log.trace(e.toString()));
    }

    private String logAndNullifyValue(Row row, int i, String value) {
        log.warn("Name value : " + value + " has been rejected because it contains a '&' or a 'ET' or '/'");
        return row.getValues().set(i, null);
    }

    private ProcessedCreator createCreator(Row row, int i, String value) {
        String[] parts = value.split(" ");
        ProcessedCreator creator = new ProcessedCreator();
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

    private boolean processSinglePart(Row row, int i, String[] parts, ProcessedCreator creator) {
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

    private void processDoubleParts(Row row, int i, String[] parts, ProcessedCreator creator) {
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
}
