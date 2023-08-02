package org.motoc.gamelibrary.repository.fragment.implementation;

import org.motoc.gamelibrary.domain.model.*;
import org.motoc.gamelibrary.repository.fragment.GameFragmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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
    public Game findGameById(Long id) {
        Game g = entityManager.find(Game.class, id);
        g.setImages(g.getImages());
        return g;
    }

    @Override
    public Page<Game> findGamesByKeyword(String keyword, Pageable pageable) {

        String searchQuery = "SELECT g FROM Game as g " +
                             " WHERE g.id IN (:ids) ORDER BY g.title ";

        String idQuery = "SELECT g.id FROM Game as g" +
                         " WHERE (g.lowerCaseTitle LIKE CONCAT('%', LOWER(:keyword), '%'))";

        TypedQuery<Long> idQ = entityManager.createQuery(idQuery, Long.class);

        List<Long> ids = idQ.setParameter("keyword", keyword)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        EntityGraph<Game> graph = entityManager.createEntityGraph(Game.class);
        graph.addSubgraph(Game_.categories);
        graph.addSubgraph(Game_.gameCopies);
        graph.addSubgraph(Game_.creators);

        List<Game> games = entityManager.createQuery(searchQuery, Game.class)
                .setParameter("ids", ids)
                .setHint("jakarta.persistence.fetchgraph", graph)
                .getResultList();

        return new PageImpl<>(games, pageable, games.size());
    }

    @Override
    public List<String> findTitles() {
        TypedQuery<String> q = entityManager.createQuery(
                "SELECT TRIM(LOWER(g.title)) FROM Game as g",
                String.class
        );
        return q.getResultList();
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
    public Game addMechanism(Game game, Mechanism mechanism) {
        game.addMechanism(mechanism);
        entityManager.persist(game);
        return game;
    }

    @Override
    public Game removeMechanism(Game game, Mechanism mechanism) {
        game.removeMechanism(mechanism);
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
