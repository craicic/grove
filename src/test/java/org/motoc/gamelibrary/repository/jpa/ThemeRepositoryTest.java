package org.motoc.gamelibrary.repository.jpa;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.motoc.gamelibrary.AbstractContainerBaseTest;
import org.motoc.gamelibrary.domain.model.Theme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

class ThemeRepositoryTest extends AbstractContainerBaseTest {

    @BeforeAll
    static void startAbstractContainer() {
        postgreSQLContainer.start();
        JdbcDatabaseDelegate containerDelegate = new JdbcDatabaseDelegate(postgreSQLContainer, "");
        ScriptUtils.runInitScript(containerDelegate, "sql/schema.sql");
        ScriptUtils.runInitScript(containerDelegate, "sql/data.sql");
    }

    private static final Logger logger = LoggerFactory.getLogger(GameRepositoryTest.class);

    @Autowired
    private ThemeRepository repository;
    @Autowired
    private EntityManagerFactory emf;

    @Test
    void insertHundredTheme() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        for (int i = 0; i < 51; i++) {
            Theme t = new Theme();
            t.setName("theme" + i);
            em.persist(t);
        }

        logger.info("Commit transaction");
        em.getTransaction().commit();

        em.getTransaction().begin();

        TypedQuery<Theme> tq = em.createQuery("SELECT t FROM Theme AS t", Theme.class);
        em.getTransaction().commit();
        List<Theme> themes = tq.getResultList();


        for (Theme t : themes) {
            logger.debug("theme:" + t.getName() + " of id=" + t.getId());
        }

        em.close();
    }


}