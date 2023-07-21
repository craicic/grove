package org.motoc.gamelibrary.repository.fragment.implementation;

import org.junit.jupiter.api.*;
import org.motoc.gamelibrary.AbstractContainerBaseIT;
import org.motoc.gamelibrary.domain.model.Mechanism;
import org.motoc.gamelibrary.repository.jpa.MechanismRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MechanismRepositoryRearmedIT extends AbstractContainerBaseIT {

    @BeforeAll
    static void startAbstractContainer() {
        postgreSQLContainer.start();
    }

    @BeforeEach()
    void reloadSQL() {
        JdbcDatabaseDelegate containerDelegate = new JdbcDatabaseDelegate(postgreSQLContainer, "");
        ScriptUtils.runInitScript(containerDelegate, "sql/schema.sql");
        ScriptUtils.runInitScript(containerDelegate, "sql/data.sql");
    }

    @Autowired
    private EntityManagerFactory emf;

    private static final Logger logger = LoggerFactory.getLogger(MechanismRepositoryRearmedIT.class);

    @Autowired
    private MechanismRepository repository;

    private static final Long tId = 1L;


    @Test
    @Order(1)
    void whenSaveMechanism_ThenReturnExpectedMechanism() {
        final String mTitle = "Cthulhu";
        Mechanism t = new Mechanism();
        t.setTitle(mTitle);
        t = repository.saveMechanism(t);

        assertThat(t.getTitle()).isEqualTo(mTitle);
        assertThat(t.getLowerCaseTitle()).isEqualTo(mTitle.toLowerCase());
    }

    @Test
    @Order(2)
    void whenDeleteMechanism_ThenMechanismCountDecreaseBy1() {
        final long preDeleteCount = repository.count();
        repository.remove(tId);
        final long postDeleteCount = repository.count();

        assertThat(postDeleteCount).isEqualTo(preDeleteCount - 1L);
    }

    @Test
    @Disabled
    @Order(3)
    void persistLotsOfMechanisms() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        for (int i = 0; i < 51; i++) {
            Mechanism m = new Mechanism();
            m.setTitle("mechanism" + i);
            em.persist(m);
        }

        logger.info("Commit transaction");
        em.getTransaction().commit();

        em.getTransaction().begin();

        TypedQuery<Mechanism> tq = em.createQuery("SELECT m FROM Mechanism AS m", Mechanism.class);
        em.getTransaction().commit();
        List<Mechanism> mechanisms = tq.getResultList();


        for (Mechanism m : mechanisms) {
            logger.debug("mechanism:" + m.getTitle() + " of id=" + m.getId());
        }
    }

    @Test
    @Order(4)
    void whenSaveAlreadyExistingMechanism_ThenThrowADataIntegrityViolationException() {
        EntityManager em = emf.createEntityManager();
        Mechanism m = new Mechanism();
        em.getTransaction().begin();
        m.setTitle(em.find(Mechanism.class, 1L).getTitle());
        em.getTransaction().commit();
        em.close();
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> repository.saveMechanism(m));

        assertThat(exception.getClass()).isEqualTo(DataIntegrityViolationException.class);
    }
}