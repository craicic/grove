package org.motoc.gamelibrary.repository.fragment.implementation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.motoc.gamelibrary.AbstractContainerBaseIT;
import org.motoc.gamelibrary.domain.model.Game;
import org.motoc.gamelibrary.domain.model.Image;
import org.motoc.gamelibrary.repository.jpa.GameRepository;
import org.motoc.gamelibrary.repository.jpa.ImageRepository;
import org.motoc.gamelibrary.technical.ResourceLoader;
import org.motoc.gamelibrary.technical.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.motoc.gamelibrary.repository.TestResources.*;

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
    private GameRepository gameRepository;

    @Autowired
    private EntityManagerFactory emf;

    private static final Logger logger = LoggerFactory.getLogger(ImageRepositoryReamedIT.class);

    @Test
    void whenPersistImageAttachToGame_WorksAsExpected() throws IOException {

        // Load the imageData as a byte array
        byte[] expectedImageData = ResourceLoader.getBytesFromResource(imageLocationB);
        Long expectedGameId = gameAId;

        // Call the method to test
        Long actualImageId = repository.persistImageAndAttachToGame(expectedImageData, expectedGameId);

        // Get the given game in database
        Optional<Game> optGame = gameRepository.findById(expectedGameId);

        Image expectedImage = new Image();
        if (optGame.isEmpty()) {
            fail("No game found with id:" + expectedGameId);
        } else {
            expectedImage.setGame(optGame.get());
        }

        Image actualImage = new Image();

        // Get the image that was persisted with the method we are testing
        Optional<Image> optImage = repository.findById(actualImageId);
        if (optImage.isEmpty()) {
            fail("No image found with id:" + actualImageId);
        } else {
            actualImage = optImage.get();
            expectedImage.setId(optImage.get().getId());
        }

        assertThat(actualImage).isEqualTo(expectedImage);

        // Test the imageData
        byte[] actualImageData = repository.findBytes(actualImageId);

        assertThat(actualImageData).isEqualTo(expectedImageData);
    }

    @Test
    void whenPersistImageAttachToGame_ThrowNotFoundException() throws IOException {
        byte[] imageData = ResourceLoader.getBytesFromResource(imageLocationB);

        NotFoundException e = assertThrows(NotFoundException.class, () -> repository.persistImageAndAttachToGame(imageData, wrongId));

        String expectedMessage = "No game of id=" + wrongId + " found.";
        String actualMessage = e.getMessage();

        assertThat(actualMessage).isEqualTo(expectedMessage);

    }

    @Test
    void whenFindBytes_FromScriptBasedEntry_ComparedToFile_ThenReturnExpectedBytes() throws IOException {

        byte[] expectedBytes = ResourceLoader.getBytesFromResource(imageLocationA);

        byte[] actualBytes = repository.findBytes(imageAId);

        assertThat(actualBytes).isEqualTo(expectedBytes);


    }

    @Test
    void whenFindBytes_StringBytes_FromHibernateBasedEntry_ThenReturnExpectedBytes() {

        byte[] expectedBytes = "testing a blob".getBytes();
        Long id = repository.persistImage(expectedBytes);

        byte[] actualBytes = repository.findBytes(id);
        assertThat(actualBytes).isEqualTo(expectedBytes).as("Persisted String bytes was found as expected.");

    }

    @Test
    void whenFindBytes_JpgFileBytes_FromHibernateBasedEntry_ThenReturnExpectedBytes() throws IOException {

        byte[] expectedBytes = ResourceLoader.getBytesFromResource(imageLocationB);
        Long id = repository.persistImage(expectedBytes);

        byte[] actualBytes = repository.findBytes(id);
        assertThat(actualBytes).isEqualTo(expectedBytes).as("Persisted JPG Blob was found as expected.");
    }

    @Test
    void whenPersistImage_ThenReturnExpectedId() throws IOException {
        byte[] expectedImageData = ResourceLoader.getBytesFromResource(imageLocationB);
        Long actualId = repository.persistImage(expectedImageData);

        assertThat(actualId).isNotNull();

        byte[] actualImageData = repository.findBytes(actualId);

        assertThat(actualImageData).isEqualTo(expectedImageData);
    }

    @Test
    void whenPersistAll_ThenReturnAListWithExpectedNumberOfEntries() throws IOException {
        List<byte[]> imageData = new ArrayList<>();
        imageData.add(ResourceLoader.getBytesFromResource(imageLocationB));
        imageData.add(ResourceLoader.getBytesFromResource(imageLocationC));
        imageData.add(ResourceLoader.getBytesFromResource(imageLocationD));

        List<Long> actualImageIds = repository.persistAll(imageData);

        assertThat(actualImageIds.size()).isEqualTo(imageData.size());

        byte[] actualBytes;
        byte[] expectedBytes;

        for (int i = 0; i < actualImageIds.size(); i++) {
            actualBytes = repository.findBytes(actualImageIds.get(i));
            expectedBytes = imageData.get(i);

            assertThat(actualBytes).isEqualTo(expectedBytes);
        }
    }
}