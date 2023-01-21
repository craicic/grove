package org.motoc.gamelibrary.repository.fragment.implementation;

import org.junit.jupiter.api.*;
import org.motoc.gamelibrary.AbstractContainerBaseIT;
import org.motoc.gamelibrary.repository.jpa.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;

import javax.persistence.EntityManagerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ImageRepositoryReamedIT extends AbstractContainerBaseIT {

    @BeforeAll
    static void startAbstractContainer() {
        postgreSQLContainer.start();
    }

    @BeforeEach
    void reloadSQL() {
        JdbcDatabaseDelegate containerDelegate = new JdbcDatabaseDelegate(postgreSQLContainer, "");
        ScriptUtils.runInitScript(containerDelegate, "sql/schema.sql");
        ScriptUtils.runInitScript(containerDelegate, "sql/data.sql");
    }

    @Autowired
    private ImageRepository repository;

    @Autowired
    private EntityManagerFactory emf;

    private static final Logger logger = LoggerFactory.getLogger(ImageRepositoryReamedIT.class);

    @Test
    void whenPersistImageAttachToGame() {
    }

    @Test
    @Order(2)
    void whenFindBytes_() {
        try {
            byte[] actualBytes = repository.findBytes(1L);
            assertThat(actualBytes).isNotEmpty();
        } catch (RuntimeException e) {
            fail("Autocloseable encountered a problem : " + e.getMessage());
        }
    }

    @Test
    @Order(1)
    void whenFindBytes() {
        byte[] expectedBytes = "testing a blob".getBytes();
        Long id = repository.persistImage("testing a blob".getBytes());
        try {
            byte[] actualBytes = repository.findBytes(id);
            assertThat(actualBytes).isEqualTo(expectedBytes);
        } catch (RuntimeException e) {
            for (StackTraceElement line :
                    e.getStackTrace()) {
                logger.debug(line.toString());
            }
            fail("Autocloseable encountered a problem : " + e.getMessage());
        }
    }

    @Test
    void whenPersistImage() {
    }

    @Test
    void whenPersistAll() {
    }
}