package org.motoc.gamelibrary.repository.criteria.implementation;

import org.motoc.gamelibrary.dto.GameNameDto;
import org.motoc.gamelibrary.model.Game;
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
            if (!game.getExpansions().contains(expansion))
                game.addExpansion(expansion);
            else
                logger.warn("Expansion {} of id={} is already linked to {} of id={}",
                        expansion.getName(), expansion.getId(),
                        game.getName(), game.getId());
        }
        entityManager.persist(game);
        logger.info("Successfully persisted game of id={}", game.getId());
        return game;
    }

    @Override
    public Game addCoreGame(Game game, Game coreGame) {
        Game coreGameFromDB = entityManager.find(Game.class, coreGame.getId());
        if (coreGameFromDB != null) {
            game.addCoreGame(coreGameFromDB);
            logger.info("Successfully persisted category of id={}", game.getId());
        } else {
            logger.warn("No core game of id {}", coreGame.getId());
        }
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
        Game gameFromDB = entityManager.find(Game.class, game.getId());
        Game expansionFromDB = entityManager.find(Game.class, expansion.getId());

        if (gameFromDB.getExpansions().isEmpty() || !gameFromDB.getExpansions().contains(expansionFromDB))
            logger.warn("Category of id=" + game.getId() + " does not contains this children of id=" + expansion.getId());
        else {
            gameFromDB.removeExpansion(expansionFromDB);
            entityManager.persist(gameFromDB);
        }
    }
}
