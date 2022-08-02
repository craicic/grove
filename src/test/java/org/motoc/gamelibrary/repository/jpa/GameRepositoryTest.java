package org.motoc.gamelibrary.repository.jpa;

import org.junit.jupiter.api.Test;
import org.motoc.gamelibrary.AbstractContainerBaseTest;
import org.motoc.gamelibrary.domain.model.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class GameRepositoryTest extends AbstractContainerBaseTest {

    private static final Logger logger = LoggerFactory.getLogger(GameRepositoryTest.class);

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