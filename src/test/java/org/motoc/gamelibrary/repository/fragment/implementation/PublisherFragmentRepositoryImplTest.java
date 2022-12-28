package org.motoc.gamelibrary.repository.fragment.implementation;

import org.junit.After;
import org.junit.jupiter.api.*;
import org.motoc.gamelibrary.domain.dto.PublisherNameDto;
import org.motoc.gamelibrary.domain.model.Contact;
import org.motoc.gamelibrary.domain.model.Publisher;
import org.motoc.gamelibrary.repository.AbstractContainerBaseTest;
import org.motoc.gamelibrary.repository.jpa.PublisherRepository;
import org.motoc.gamelibrary.technical.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PublisherFragmentRepositoryImplTest extends AbstractContainerBaseTest {

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

    private static final Logger logger = LoggerFactory.getLogger(PublisherFragmentRepositoryImplTest.class);

    private static final Long pId = 1L;

    private static final Long wrongId = 1152L;
    @Autowired
    private PublisherRepository repository;

    @Test
    @Order(4)
    void whenDeletePublisher_ThenPublisherCountDecreaseBy1() {
        final long preDeleteCount = repository.count();
        repository.remove(pId);
        final long postDeleteCount = repository.count();

        assertThat(postDeleteCount).isEqualTo(preDeleteCount - 1L);
    }


    @Test
    @Order(3)
    void whenRemoveContact_ThenContactIsNull() {
        EntityManager em = emf.createEntityManager();
        Contact cBefore = em.find(Publisher.class, pId).getContact();
        logger.info("Before removeContact Contact={}", cBefore);

        assertThat(cBefore).isNotNull();

        Contact cAfter = repository.removeContact(pId).getContact();
        logger.info("After removeContact Contact={}", cAfter);

        assertThat(cAfter).isNull();
    }

    @Test
    @Order(5)
    void whenRemoveContact_WithWrongId_ThenThrowNotFoundException() {
        Exception exception = assertThrows(NotFoundException.class, () -> repository.removeContact(wrongId));

        assertThat(exception).hasMessageContaining("No publisher of id=" + wrongId + " found");

    }

    @Test
    @Order(1)
    void whenSavePublisher_ThenReturnExpectedPublisher() {
        final String pName = "Matagot";
        Publisher p = new Publisher();
        p.setName(pName);
        p = repository.savePublisher(p);

        assertThat(p.getName()).isEqualTo(pName);
        assertThat(p.getLowerCaseName()).isEqualTo(pName.toLowerCase());
    }

    @Test
    @Order(2)
    void whenSaveAlreadyExistingPublisher_ThenThrowADataIntegrityViolationException() {
        EntityManager em = emf.createEntityManager();
        Publisher p = new Publisher();
        p.setName(em.find(Publisher.class, 1L).getName());
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> repository.savePublisher(p));

        assertThat(exception.getClass()).isEqualTo(DataIntegrityViolationException.class);
    }

    @Test
    @Sql({"/sql/truncate.sql", "/sql/data.sql"})
    @Order(6)
    void whenFindNames_ThenReturnAList() {
        List<PublisherNameDto> names = repository.findNames();

        assertThat(names.size()).isEqualTo(3);
    }
}