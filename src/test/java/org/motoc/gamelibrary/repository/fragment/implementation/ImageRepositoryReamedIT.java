package org.motoc.gamelibrary.repository.fragment.implementation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.motoc.gamelibrary.AbstractContainerBaseIT;
import org.motoc.gamelibrary.repository.jpa.ImageRepository;
import org.motoc.gamelibrary.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

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
    private ImageService service;

    @Autowired
    private ImageRepository repository;

    @Autowired
    private EntityManagerFactory emf;

    private byte[] imageByte;
    private static final Logger logger = LoggerFactory.getLogger(ImageRepositoryReamedIT.class);

    @Test
    void whenPersistImageAttachToGame() {
    }

    @Test
    void whenFindLob() throws IOException {
        byte[] expectedBytes = "testing a blob".getBytes();
        Long id = repository.persistImage(expectedBytes);

        byte[] actualBytes = repository.findLob(id).readAllBytes();

        assertThat(actualBytes).isEqualTo(expectedBytes);
    }

    @Test
    void whenPersistImage() {
    }

    @Test
    void whenPersistAll() {
    }
}