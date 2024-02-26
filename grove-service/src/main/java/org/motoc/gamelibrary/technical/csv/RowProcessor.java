package org.motoc.gamelibrary.technical.csv;

import org.motoc.gamelibrary.technical.csv.object.Row;
import org.motoc.gamelibrary.technical.csv.object.value.ArtistValue;
import org.motoc.gamelibrary.technical.csv.object.value.GameCopyValues;
import org.motoc.gamelibrary.technical.csv.object.value.GameValues;
import org.motoc.gamelibrary.technical.csv.object.value.PublisherValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class RowProcessor {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getCanonicalName());


    public void mapToValues(List<Row> rows,
                            List<ArtistValue> av,
                            List<ArtistValue> iv,
                            List<GameValues> gv,
                            List<GameCopyValues> gcv,
                            List<PublisherValues> pv) {
        Instant start = Instant.now();
        Integer code;
        for (Row row : rows) {
            code = parseObjectCode(row.getValues().get(0));
            extractAuthorData(row, av, code);
            extractIllustratorData(row, iv, code);
            extractGameData(row, gv, code);
            extractGameCopyData(row, gcv, code);
            extractPublisherData(row, pv, code);
        }
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        long nbOfObject = av.size() + iv.size() + gv.size() + gcv.size() + pv.size();
        log.info("Number of authors: " + av.size());
        log.info("Mapped " + rows.size() + " rows into " + nbOfObject + " objects. Processing time: " + duration.toMillis() + " milliseconds");
    }

    private void extractAuthorData(Row row, List<ArtistValue> av, Integer code) {
        ArtistValue c1 = new ArtistValue();
        ArtistValue c2 = new ArtistValue();
        ArtistValue c3 = new ArtistValue();

        c1.setName(row.getValues().get(15));
        c1.setObjectCode(code);
        c2.setName(row.getValues().get(16));
        c2.setObjectCode(code);
        c3.setName(row.getValues().get(12));
        c3.setObjectCode(code);
        av.addAll(List.of(c1, c2, c3));
    }

    private void extractIllustratorData(Row row, List<ArtistValue> iv, Integer code) {
        ArtistValue ill = new ArtistValue();
        ill.setName(row.getValues().get(17));
        ill.setObjectCode(code);
        iv.add(ill);
    }

    private void extractGameData(Row row, List<GameValues> gv, Integer code) {
        GameValues g = new GameValues();
        List<String> values = row.getValues();
        g.setObjectCodes(values.get(0));
        g.setObjectCode(code);
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

    private void extractGameCopyData(Row row, List<GameCopyValues> gcv, Integer code) {
        GameCopyValues gc = new GameCopyValues();
        List<String> values = row.getValues();
        gc.setObjectCode(code);
        gc.setLocation(values.get(4));
        gc.setWearCondition(values.get(6));
        gc.setGeneralState(values.get(7));
        gc.setDateOfPurchase(values.get(8));
        gc.setPrice(values.get(9));
        gc.setPublisherName(values.get(10));
        gcv.add(gc);
    }

    private void extractPublisherData(Row row, List<PublisherValues> pv, Integer code) {
        PublisherValues p = new PublisherValues();
        p.setName(row.getValues().get(10));
        pv.add(p);
    }

    private Integer parseObjectCode(String code) {
        Integer parsedCode = null;
        try {
            parsedCode = Integer.valueOf(code);
            log.trace("parsedCode:" + parsedCode);
        } catch (NumberFormatException nfe) {
            log.warn("Invalid ObjectCode Format : " + code);
            log.warn(nfe.getMessage());
        }
        return parsedCode;
    }
}
