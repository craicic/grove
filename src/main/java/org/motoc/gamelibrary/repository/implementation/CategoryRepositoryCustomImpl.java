package org.motoc.gamelibrary.repository.implementation;

import org.motoc.gamelibrary.model.Category;
import org.motoc.gamelibrary.repository.CategoryRepositoryCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

/**
 * It's the category custom repository implementation, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 */
@Repository
public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom {

    private static final Logger logger = LoggerFactory.getLogger(CategoryRepositoryCustomImpl.class);

    private final EntityManager entityManager;

    @Autowired
    public CategoryRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Removes carefully the category of id
     */
    @Override
    public void remove(Long id) {
        Category category = entityManager.find(Category.class, id);
        if (category != null) {
            for (Category children : category.getChildren()) {
                category.removeChild(children);
            }
            category.removeParent(category.getParent());
            entityManager.remove(category);
            logger.info("Successfully deleted category of id={}", id);
        } else
            logger.info("Tried to delete, but category of id={} doesn't exist", id);
    }
}
