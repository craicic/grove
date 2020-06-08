package org.motoc.gamelibrary.repository.criteria.implementation;

import org.motoc.gamelibrary.model.Category;
import org.motoc.gamelibrary.model.Game;
import org.motoc.gamelibrary.repository.criteria.CategoryRepositoryCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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
            category.removeParent();

            for (Game game : category.getGames()) {
                game.removeCategory(category);
            }
            entityManager.remove(category);
            logger.info("Successfully deleted category of id={}", id);
        } else
            logger.info("Tried to delete, but category of id={} doesn't exist", id);
    }

    /**
     * Adds a list of children to a category, this methods checks if the child is already contains in the children of
     * this category.
     */
    @Override
    public Category saveWithChildren(List<Category> children, Category category) {
        for (Category child : children) {
            if (!category.getChildren().contains(child))
                category.addChild(child);
            else
                logger.warn("Child category {} of id={} is already linked to {} of id={}",
                        child.getName(), child.getId(),
                        category.getName(), category.getId());
        }
        entityManager.persist(category);
        logger.info("Successfully persisted category of id={}", category.getId());
        return category;
    }

    /**
     * Adds a parent to a given category
     */
    @Override
    public Category saveWithParent(Category parent, Category category) {
        Category parentFromDb = entityManager.find(Category.class, parent.getId());
        category.addParent(parentFromDb);
        logger.info("Successfully persisted category of id={}", parentFromDb.getId());
        return category;
    }

    /**
     * Removes a parent of a given category
     */
    @Override
    public void removeParent(Category category) {
        category.removeParent();
        logger.info("Successfully remove parent of category of id={}", category.getId());
    }

    /**
     * Removes a child of a given category
     */
    @Override
    public void removeChild(Long catId, Long childId) {
        Category category = entityManager.find(Category.class, catId);
        Category child = entityManager.find(Category.class, childId);

        if (category.getChildren().isEmpty() || !category.getChildren().contains(child))
            logger.warn("Category of id=" + catId + " does not contains this children of id=" + childId);
        else {
            category.removeChild(child);
        }
    }
}
