package org.motoc.gamelibrary.repository.criteria.implementation;

import org.motoc.gamelibrary.dto.GameNameDto;
import org.motoc.gamelibrary.model.*;
import org.motoc.gamelibrary.repository.criteria.GameRepositoryCustom;
import org.motoc.gamelibrary.technical.exception.ChildAndParentException;
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
            logger.info("Successfully persisted game of id={}", game.getId());
        } else {
            logger.warn("No core game of id {}", coreGame.getId());
            // TODO throw exception
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
            logger.warn("Game of id=" + game.getId() + " does not contains this expansion of id=" + expansion.getId());
        else {
            gameFromDB.removeExpansion(expansionFromDB);
            entityManager.persist(gameFromDB);
        }
    }

    @Override
    public Game addCategory(Game game, Category category) {
        if (game.getCategories().contains(category))
            logger.warn("Game of id=" + game.getId() +
                    " is already linked to category of id=" + category.getId());
        game.addCategory(category);
        entityManager.persist(game);
        return game;
    }

    @Override
    public void removeCategory(Game game, Category category) {
        if (!game.getCategories().contains(category))
            throw new ChildAndParentException("Game of id=" + game.getId() +
                    " is not linked to category of id=" + category.getId());
        game.removeCategory(category);
        entityManager.persist(game);
    }

    @Override
    public Game addTheme(Game game, Theme theme) {
        if (game.getThemes().contains(theme))
            logger.warn("Game of id=" + game.getId() +
                    " is already linked to theme of id=" + theme.getId());
        game.addTheme(theme);
        entityManager.persist(game);
        return game;
    }

    @Override
    public void removeTheme(Game game, Theme theme) {
        if (!game.getThemes().contains(theme))
            throw new ChildAndParentException("Game of id=" + game.getId() +
                    " is not linked to theme of id=" + theme.getId());
        game.removeTheme(theme);
        entityManager.persist(game);
    }

    @Override
    public Game addGameCopy(Game game, GameCopy gameCopy) {
        if (game.getGameCopies().contains(gameCopy))
            logger.warn("Game of id=" + game.getId() +
                    " is already linked to gameCopy of id=" + gameCopy.getId());
        game.addGameCopy(gameCopy);
        entityManager.persist(game);
        return game;
    }

    @Override
    public void removeGameCopy(Game game, GameCopy gameCopy) {
        if (!game.getGameCopies().contains(gameCopy))
            throw new ChildAndParentException("Game of id=" + game.getId() +
                    " is not linked to gameCopy of id=" + gameCopy.getId());
        game.removeGameCopy(gameCopy);
        entityManager.persist(game);
    }

    @Override
    public Game addCreator(Game game, Creator creator) {
        if (game.getCreators().contains(creator))
            logger.warn("Game of id=" + game.getId() +
                    " is already linked to creator of id=" + creator.getId());
        game.addCreator(creator);
        entityManager.persist(game);
        return game;
    }

    @Override
    public void removeCreator(Game game, Creator creator) {
        if (!game.getCreators().contains(creator))
            throw new ChildAndParentException("Game of id=" + game.getId() +
                    " is not linked to creator of id=" + creator.getId());
        game.removeCreator(creator);
        entityManager.persist(game);
    }

    @Override
    public Game addPublisher(Game game, Publisher publisher) {
        if (game.getPublisher() == publisher)
            logger.warn("Game of id=" + game.getId() +
                    " is already linked to publisher of id=" + publisher.getId());
        game.addPublisher(publisher);
        entityManager.persist(game);
        return game;
    }

    @Override
    public void removePublisher(Game game, Publisher publisher) {
        if (game.getPublisher() != publisher)
            throw new ChildAndParentException("Game of id=" + game.getId() +
                    " is not linked to publisher of id=" + publisher.getId());
        game.removePublisher(publisher);
        entityManager.persist(game);
    }

    //    public Game addCategoryOld(Game game, Long categoryId) {
//        Category category = entityManager.find(Category.class, categoryId);
//        if (category == null)
//            throw new NotFoundException(categoryId);
//        if (game.getId() == null)
//            throw new RuntimeException("Parameter game has no id");
//        if (game.getCategories().contains(category))
//            throw new ChildAndParentException("Game of id=" +game.getId() +
//                    " is already linked to category of id=" + categoryId);
//        game.addCategory(category);
//        entityManager.persist(game);
//        return game;
//    }
//
//
//    public void removeCategoryOld(Game game, Long categoryId) {
//        Category category = entityManager.find(Category.class, categoryId);
//        if (category == null)
//            throw new NotFoundException(categoryId);
//        if (game.getId() == null)
//            throw new RuntimeException("Parameter game has no id");
//        if (!game.getCategories().contains(category))
//            throw new ChildAndParentException("Game of id=" +game.getId() +
//                    " is not linked to category of id=" + categoryId);
//        game.removeCategory(category);
//        entityManager.persist(game);
//    }
}
