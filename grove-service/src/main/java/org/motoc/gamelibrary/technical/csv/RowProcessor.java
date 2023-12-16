package org.motoc.gamelibrary.technical.csv;

import org.motoc.gamelibrary.technical.csv.object.Row;
import org.motoc.gamelibrary.technical.csv.object.value.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RowProcessor {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getCanonicalName());

    private List<AuthorValues> av = new ArrayList<>();
    private List<IllustratorValues> iv = new ArrayList<>();
    private List<GameValues> gv = new ArrayList<>();
    private List<GameCopyValues> gcv = new ArrayList<>();
    private List<PublisherValues> pv = new ArrayList<>();

    public void processToValues(List<Row> rows) {
        for (Row row : rows) {
            extractAuthorData(row);
            extractIllustratorData(row);
            extractGameData(row);
            extractGameCopyData(row);
            extractPublisherData(row);
        }
    }

    private void extractAuthorData(Row row) {
        AuthorValues c = new AuthorValues();
        c.setName(row.getValues().get(15));
        c.setName(row.getValues().get(16));
        c.setName(row.getValues().get(12));
        av.add(c);
    }

    private void extractIllustratorData(Row row) {
        IllustratorValues ill = new IllustratorValues();
        ill.setName(row.getValues().get(17));
        iv.add(ill);
    }

    private void extractGameData(Row row) {
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

    private void extractGameCopyData(Row row) {
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

    private void extractPublisherData(Row row) {
        PublisherValues p = new PublisherValues();
        p.setName(row.getValues().get(10));
        pv.add(p);
    }
}
