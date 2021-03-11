package org.motoc.gamelibrary.repository.criteria.implementation;

import org.motoc.gamelibrary.dto.GameNameDto;
import org.motoc.gamelibrary.model.*;
import org.motoc.gamelibrary.repository.criteria.GameRepositoryCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Game custom repository implementation, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 *
 * @author RouzicJ
 */
@Repository
public class GameRepositoryCustomImpl implements GameRepositoryCustom {

    private static final Logger logger = LoggerFactory.getLogger(GameRepositoryCustomImpl.class);

    private final EntityManager entityManager;

    @Autowired
    public GameRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<GameNameDto> findNames() {
        TypedQuery<GameNameDto> q = entityManager.createQuery(
                "SELECT new org.motoc.gamelibrary.dto.GameNameDto(g.name) FROM Game as g",
                GameNameDto.class
        );
        return q.getResultList();
    }

    @Override
    public Game addExpansions(Game game, List<Game> expansions) {
        for (Game expansion : expansions) {
            game.addExpansion(expansion);
        }
        entityManager.persist(game);
        logger.info("Successfully persisted game of id={}", game.getId());
        return game;
    }

    @Override
    public Game addExpansion(Game game, Game expansion) {
        game.addExpansion(expansion);
        entityManager.persist(game);
        logger.info("Successfully persisted game of id={}", game.getId());
        return game;
    }

    @Override
    public Game addCoreGame(Game game, Game coreGame) {
        game.addCoreGame(coreGame);
        entityManager.persist(game);
        logger.info("Successfully persisted game of id={}", game.getId());
        return game;
    }

    @Override
    public void removeCoreGame(Game game) {
        game.removeCoreGame();
        entityManager.persist(game);
        logger.info("Successfully removed core game of game of id={}", game.getId());
    }

    @Override
    public void removeExpansion(Game game, Game expansion) {
        game.removeExpansion(expansion);
        entityManager.persist(game);

    }

    @Override
    public Game addCategory(Game game, Category category) {
        game.addCategory(category);
        entityManager.persist(game);
        return game;
    }

    @Override
    public void removeCategory(Game game, Category category) {
        game.removeCategory(category);
        entityManager.persist(game);
    }

    @Override
    public Game addTheme(Game game, Theme theme) {
        game.addTheme(theme);
        entityManager.persist(game);
        return game;
    }

    @Override
    public void removeTheme(Game game, Theme theme) {
        game.removeTheme(theme);
        entityManager.persist(game);
    }

    @Override
    public Game addGameCopy(Game game, GameCopy gameCopy) {
        game.addGameCopy(gameCopy);
        entityManager.persist(game);
        return game;
    }

    @Override
    public void removeGameCopy(Game game, GameCopy gameCopy) {
        game.removeGameCopy(gameCopy);
        entityManager.persist(game);
    }

    @Override
    public Game addCreator(Game game, Creator creator) {
        game.addCreator(creator);
        entityManager.persist(game);
        return game;
    }

    @Override
    public void removeCreator(Game game, Creator creator) {
        game.removeCreator(creator);
        entityManager.persist(game);
    }

    @Override
    public Game addPublisher(Game game, Publisher publisher) {
        game.addPublisher(publisher);
        entityManager.persist(game);
        return game;
    }

    @Override
    public void removePublisher(Game game, Publisher publisher) {
        game.removePublisher(publisher);
        entityManager.persist(game);
    }

    @Override
    public void attachImage(Game game, Image image) {
        game.addImage(image);
        entityManager.persist(game);

    }

}
