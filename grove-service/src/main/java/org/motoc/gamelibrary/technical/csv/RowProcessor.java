package org.motoc.gamelibrary.technical.csv;

import org.motoc.gamelibrary.technical.csv.object.Row;
import org.motoc.gamelibrary.technical.csv.object.value.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Service
public class RowProcessor {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getCanonicalName());


    public void mapToValues(List<Row> rows,
                            List<AuthorValues> av,
                            List<IllustratorValues> iv,
                            List<GameValues> gv,
                            List<GameCopyValues> gcv,
                            List<PublisherValues> pv) {
        Instant start = Instant.now();
        for (Row row : rows) {
            extractAuthorData(row, av);
            extractIllustratorData(row, iv);
            extractGameData(row, gv);
            extractGameCopyData(row, gcv);
            extractPublisherData(row, pv);
        }
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        long nbOfObject = av.size() + iv.size() + gv.size() + gcv.size() + pv.size();
        log.info("Mapped " + rows.size() + " rows into " + nbOfObject + " objects. Processing time: " + duration.toMillis() + " milliseconds");
    }

    private void extractAuthorData(Row row, List<AuthorValues> av) {
        AuthorValues c = new AuthorValues();
        c.setName(row.getValues().get(15));
        c.setName(row.getValues().get(16));
        c.setName(row.getValues().get(12));
        av.add(c);
    }

    private void extractIllustratorData(Row row, List<IllustratorValues> iv) {
        IllustratorValues ill = new IllustratorValues();
        ill.setName(row.getValues().get(17));
        iv.add(ill);
    }

    private void extractGameData(Row row, List<GameValues> gv) {
        GameValues g = new GameValues();
        List<String> values = row.getValues();
        g.setObjectCodes(values.get(0));
        g.setTitle(values.get(1));
        g.setUnknown(values.get(2));
        g.setNature(values.get(3));
        g.setStat(values.get(5));
        g.setAgeRange(values.get(13));
        g.setNbOfPlayers(values.get(14));
        g.getAuthorNames().add(values.get(15));
        g.getAuthorNames().add(values.get(16));
        g.getAuthorNames().add(values.get(12));
        g.setIllustratorName(values.get(17));
        gv.add(g);
    }

    private void extractGameCopyData(Row row, List<GameCopyValues> gcv) {
        GameCopyValues gc = new GameCopyValues();
        List<String> values = row.getValues();
        gc.setObjectCode(values.get(0));
        gc.setLocation(values.get(4));
        gc.setWearCondition(values.get(6));
        gc.setGeneralState(values.get(7));
        gc.setDateOfPurchase(values.get(8));
        gc.setPrice(values.get(9));
        gc.setPublisherName(values.get(10));
        gcv.add(gc);
    }

    private void extractPublisherData(Row row, List<PublisherValues> pv) {
        PublisherValues p = new PublisherValues();
        p.setName(row.getValues().get(10));
        pv.add(p);
    }
}
