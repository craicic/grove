package org.motoc.gamelibrary.repository.fragment.implementation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.motoc.gamelibrary.domain.model.Game;
import org.motoc.gamelibrary.repository.AbstractContainerBaseTest;
import org.motoc.gamelibrary.repository.jpa.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class GameRepositoryRearmedTest extends AbstractContainerBaseTest {

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

    private static final Logger logger = LoggerFactory.getLogger(GameRepositoryRearmedTest.class);

    @Autowired
    private GameRepository repository;

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