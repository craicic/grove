package org.motoc.gamelibrary.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

@Service
public class CSVImportService {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getCanonicalName());

    private final EntityManagerFactory emf;

    public CSVImportService(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public void importCSV(Path csvFilePath) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        int batchSize = 20;

        try {

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath.toFile(), StandardCharsets.UTF_8));
            String lineText = null;

            int count = 0;

            lineReader.readLine(); // skip header line
            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(";");
                String column1 = data[0].trim();
                String column2 = data[1];

                if (count % batchSize == 0) {
                    log.debug("-----------");
                    log.debug("Code objet : " + column1);
                    log.debug("Titre du jeu : " + column2);
                    StringBuilder sb = new StringBuilder();
                    sb.append("[ ");
                    for (int i = 2; i < data.length; i++) {
                        log.debug(data[i]);
                        sb.append(data[i]);
                        if (i != data.length - 1) {
                            sb.append(" | ");
                        }
                    }
                    sb.append(" ]");
                    log.debug(sb.toString());
                }
                count++;
            }

            lineReader.close();
        } catch (Exception e) {
            for (StackTraceElement ste : e.getStackTrace()) {
                log.warn(ste.toString());
            }
        }
        em.getTransaction().commit();
        em.close();
    }
}
