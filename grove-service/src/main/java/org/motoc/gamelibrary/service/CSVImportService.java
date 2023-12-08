package org.motoc.gamelibrary.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class CSVImportService {

    private final EntityManagerFactory emf;

    public CSVImportService(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public void importCSV(Path cArg) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        em.createNativeQuery("");
    }
}
