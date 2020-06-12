package org.motoc.gamelibrary.repository.criteria.implementation;

import org.motoc.gamelibrary.model.Creator;
import org.motoc.gamelibrary.model.Game;
import org.motoc.gamelibrary.repository.criteria.CreatorRepositoryCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

/**
 * Creator custom repository implementation, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 *
 * @author RouzicJ
 */
@Repository
public class CreatorRepositoryCustomImpl implements CreatorRepositoryCustom {

    private static final Logger logger = LoggerFactory.getLogger(CreatorRepositoryCustomImpl.class);

    private final EntityManager entityManager;

    @Autowired
    public CreatorRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Removes a creator
     */
    @Override
    public void remove(Long id) {
        Creator creator = entityManager.find(Creator.class, id);
        creator.removeContact(creator.getContact());

        for (Game game : creator.getGames()) {
            game.removeCreator(creator);
        }

        entityManager.remove(creator);
    }
}
