package org.motoc.gamelibrary.repository.implementation;

import org.motoc.gamelibrary.model.Game;
import org.motoc.gamelibrary.model.ProductLine;
import org.motoc.gamelibrary.repository.ProductLineRepositoryCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

/**
 * It's the product line custom repository implementation, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 */
@Repository
public class ProductLineRepositoryCustomImpl implements ProductLineRepositoryCustom {

    private static final Logger logger = LoggerFactory.getLogger(ProductLineRepositoryCustomImpl.class);

    private final EntityManager entityManager;

    @Autowired
    public ProductLineRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void remove(Long id) {
        ProductLine productLine = entityManager.find(ProductLine.class, id);
        if (productLine != null) {
            for (Game game : productLine.getGames()) {
                productLine.removeGame(game);
            }
            entityManager.remove(productLine);
            logger.info("Successfully deleted product line of id={}", id);
        } else
            logger.info("Tried to delete, but product line of id={} doesn't exist", id);
    }
}

