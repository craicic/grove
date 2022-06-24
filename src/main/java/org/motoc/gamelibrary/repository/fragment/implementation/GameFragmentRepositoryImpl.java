package org.motoc.gamelibrary.repository.fragment.implementation;

import org.motoc.gamelibrary.model.*;
import org.motoc.gamelibrary.repository.fragment.GameFragmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Game custom repository implementation, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 */
@Repository
public class GameFragmentRepositoryImpl implements GameFragmentRepository {

    private static final Logger logger = LoggerFactory.getLogger(GameFragmentRepositoryImpl.class);

    private final EntityManager entityManager;

    @Autowired
    public GameFragmentRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Page<Game> findOverview(String keyword, Pageable pageable) {

        String searchQuery = "SELECT g FROM Game as g " +
                " WHERE g.id IN (:ids) ORDER BY g.name ";

        String idQuery = "SELECT g.id FROM Game as g" +
                " WHERE (g.lowerCaseName LIKE CONCAT('%', LOWER(:keyword), '%'))";

        TypedQuery<Long> idQ = entityManager.createQuery(idQuery, Long.class);

        List<Long> ids = idQ.setParameter("keyword", keyword)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        EntityGraph<Game> graph = entityManager.createEntityGraph(Game.class);
        graph.addSubgraph(Game_.categories);
        graph.addSubgraph(Game_.images);
        graph.addSubgraph(Game_.gameCopies);
        graph.addSubgraph(Game_.creators);

        List<Game> games = entityManager.createQuery(searchQuery, Game.class)
                .setParameter("ids", ids)
                .setHint("javax.persistence.fetchgraph", graph)
                .getResultList();

        return new PageImpl<>(games, pageable, games.size());
    }

    @Override
    public List<String> findNames() {
        TypedQuery<String> q = entityManager.createQuery(
                "SELECT TRIM(LOWER(g.name)) FROM Game as g",
                String.class
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
    public Game addProductLine(Game game, ProductLine productLine) {
        game.addProductLine(productLine);
        entityManager.persist(game);
        logger.info("Successfully persisted game of id={}", game.getId());
        return game;
    }

    @Override
    public Game removeProductLine(Game game, ProductLine productLine) {
        game.removeProductLine(productLine);
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
    public Game removeCategory(Game game, Category category) {
        game.removeCategory(category);
        entityManager.persist(game);
        return game;
    }

    @Override
    public Game addTheme(Game game, Theme theme) {
        game.addTheme(theme);
        entityManager.persist(game);
        return game;
    }

    @Override
    public Game removeTheme(Game game, Theme theme) {
        game.removeTheme(theme);
        entityManager.persist(game);
        return game;
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
    public Game removeCreator(Game game, Creator creator) {
        game.removeCreator(creator);
        entityManager.persist(game);
        return game;
    }


    @Override
    public void attachImage(Game game, Image image) {
        game.addImage(image);
        entityManager.persist(game);

    }

}
