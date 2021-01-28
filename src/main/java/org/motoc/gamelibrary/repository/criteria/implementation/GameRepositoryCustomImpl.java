package org.motoc.gamelibrary.repository.criteria.implementation;

import org.motoc.gamelibrary.dto.GameNameDto;
import org.motoc.gamelibrary.model.*;
import org.motoc.gamelibrary.repository.criteria.GameRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Game custom repository implementation, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 *
 * @author RouzicJ
 */
@Repository
public class GameRepositoryCustomImpl implements GameRepositoryCustom {

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
    public Page<Game> getFilteredGameOverview(Pageable pageable, String keyword) {

        // Create query
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createQuery(Tuple.class);

        // Define FROM clause
        Root<Game> root = cq.from(Game.class);
        Join<Game, Category> categories = root.join(Game_.categories);
        Join<Game, Creator> creators = root.join(Game_.creators);
        Join<Game, Image> images = root.join(Game_.images);
        Join<Game, Game> expansions = root.join(Game_.expansions);
        Join<Game, Game> coreGame = root.join(Game_.coreGame);

        // Define Tuple projection
        cq.multiselect(
                root.get(Game_.name).alias("name"),
                root.get(Game_.description).alias("description"),
                root.get(Game_.playTime).alias("playtime"),
                root.get(Game_.minNumberOfPlayer).alias("minNumberOfPlayer"),
                root.get(Game_.maxNumberOfPlayer).alias("maxNumberOfPlayer"),
                root.get(Game_.minAge).alias("minAge"),
                root.get(Game_.maxAge).alias("maxAge"),
                root.get(Game_.minMonth).alias("minMonth"),
                categories.get(Category_.id).alias("CategoryId"),
                categories.get(Category_.name).alias("CategoryName"),
                creators.get(Creator_.id).alias("creatorId"),
                creators.get(Creator_.firstName).alias("creatorFirstName"),
                creators.get(Creator_.lastName).alias("creatorLastName"),
                creators.get(Creator_.role).alias("creatorRole"),
                images.get(Image_.id).alias("imageId"),
                images.get(Image_.filePath).alias("imagePath"),
                expansions.get(Game_.id).alias("expansionId"),
                expansions.get(Game_.name).alias("expansionName"),
                coreGame.get(Game_.id).alias("coreGameId"),
                coreGame.get(Game_.name).alias("coreGameName")
        );

        // Define WHERE clause

        // Execute query

        // Something ...
//        TypedQuery<Game> q = entityManager.createQuery(
//                "SELECT new org.motoc.gamelibrary.dto.GameOverviewWithImageIdsDto(" +
//                        "g.id, g.gameCopies, g.coreGame, g.expansions,  g.name, g.description, g.playTime, g.minNumberOfPlayer, g.maxNumberOfPlayer," +
//                        "g.minAge, g.maxAge, g.minMonth, g.categories ,g.creators, g.images) FROM Game as g",
//                GameOverviewWithImageIdsDto.class
//        );

        return null;
    }
}
