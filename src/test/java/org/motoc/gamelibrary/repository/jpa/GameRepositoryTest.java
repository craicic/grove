package org.motoc.gamelibrary.repository.jpa;

import org.junit.jupiter.api.Test;
import org.motoc.gamelibrary.model.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@Commit
class GameRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(GameRepositoryTest.class);

    @Autowired
    private GameRepository repository;

    @Container
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:14")
            .withDatabaseName("game-library-test-db")
            .withPassword("postgres")
            .withUsername("postgres");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

    @Test
    void test_findOverviewByKeyword() {
        String keyword = "colons";
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Order.asc("name")));

        Page<Game> pagedGames = repository.findOverview(keyword, pageable);
        List<Game> games = pagedGames.getContent();

        for (Game game : games) {
            logger.info(game.toString());
        }
        assertThat(games).isNotNull();
    }

    @Test
    void test_findAllByLowerCaseNameContaining() {
        String keyword = "colons";
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Order.asc("name")));

        Page<Game> pagedGames = repository.findAllByLowerCaseNameContaining(keyword, pageable);

        logger.warn(pagedGames.toString());
        logger.warn("number of elements=" + pagedGames.getTotalElements());
        List<Game> games = pagedGames.getContent();
        logger.warn("number of games=" + games.size());

        for (Game game : games) {
            logger.warn(game.toString());
        }

        assertThat(pagedGames).isNotNull();
    }
}