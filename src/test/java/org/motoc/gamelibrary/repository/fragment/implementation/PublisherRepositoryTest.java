package org.motoc.gamelibrary.repository.fragment.implementation;

import org.junit.After;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.motoc.gamelibrary.domain.model.Contact;
import org.motoc.gamelibrary.domain.model.Publisher;
import org.motoc.gamelibrary.repository.AbstractContainerBaseTest;
import org.motoc.gamelibrary.repository.jpa.PublisherRepository;
import org.motoc.gamelibrary.technical.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PublisherRepositoryTest extends AbstractContainerBaseTest {

    @BeforeAll
    static void startAbstractContainer() {
        postgreSQLContainer.start();
        JdbcDatabaseDelegate containerDelegate = new JdbcDatabaseDelegate(postgreSQLContainer, "");
        ScriptUtils.runInitScript(containerDelegate, "sql/schema.sql");
        ScriptUtils.runInitScript(containerDelegate, "sql/data.sql");
    }

    @After
    public void closeEMF() {
        this.emf.close();
    }

    @Autowired
    private EntityManagerFactory emf;

    private static final Logger logger = LoggerFactory.getLogger(PublisherRepositoryTest.class);

    private static final Long pId = 1L;
    private static final Long wrongId = 1152L;
    @Autowired
    private PublisherRepository repository;

    @Test
    void WhenDeletePublisher_ThenPublisherCountDecreaseBy1() {
        final long preDeleteCount = repository.count();
        repository.remove(pId);
        final long postDeleteCount = repository.count();

        assertThat(postDeleteCount).isEqualTo(preDeleteCount - 1L);
    }


    @Test
    void WhenRemoveContact_ThenContactIsNull() {
        EntityManager em = emf.createEntityManager();
        Contact cBefore = em.find(Publisher.class, pId).getContact();
        logger.info("Before removeContact Contact={}", cBefore);

        assertThat(cBefore).isNotNull();

        Contact cAfter = repository.removeContact(pId).getContact();
        logger.info("After removeContact Contact={}", cAfter);

        assertThat(cAfter).isNull();
    }

    @Test
    void WhenRemoveContact_WithWrongId_ThenThrowNotFoundException() {
        Exception exception = assertThrows(NotFoundException.class, () -> {
            repository.removeContact(wrongId);
        });

        assertThat(exception).hasMessageContaining("No publisher of id=" + wrongId + " found");

    }

    @Test
    void findNames() {
    }
}