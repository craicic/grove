package org.motoc.gamelibrary.repository.jpa;

import org.junit.jupiter.api.Test;
import org.motoc.gamelibrary.model.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql(scripts = {
        "/sql/game_library_dev_db_public_contact.sql",
        "/sql/game_library_dev_db_public_category.sql",
        "/sql/game_library_dev_db_public_theme.sql",
        "/sql/game_library_dev_db_public_product_line.sql",
        "/sql/game_library_dev_db_public_account.sql",
        "/sql/game_library_dev_db_public_publisher.sql",
        "/sql/game_library_dev_db_public_game.sql",
        "/sql/game_library_dev_db_public_seller.sql",
        "/sql/game_library_dev_db_public_creator.sql",
        "/sql/game_library_dev_db_public_game_copy.sql",
        "/sql/game_library_dev_db_public_game_category.sql",
        "/sql/game_library_dev_db_public_game_creator.sql",
        "/sql/game_library_dev_db_public_game_theme.sql",
        "/sql/game_library_dev_db_public_loan.sql"}
)
class GameRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(GameRepositoryTest.class);

    @Autowired
    private GameRepository repository;

    @Test
    void test_findOverviewByKeyword() {
        String keyword = "colons";
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Order.asc("name")));

        Page<Game> pagedGames = repository.findOverview(keyword, pageable);
        List<Game> games = pagedGames.getContent();

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