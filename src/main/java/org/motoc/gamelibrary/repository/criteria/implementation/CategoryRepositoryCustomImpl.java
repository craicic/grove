package org.motoc.gamelibrary.repository.criteria.implementation;

import org.motoc.gamelibrary.dto.CategoryNameDto;
import org.motoc.gamelibrary.model.Category;
import org.motoc.gamelibrary.model.Game;
import org.motoc.gamelibrary.repository.criteria.CategoryRepositoryCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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


    @Override
    public void remove(Long id) {
        Category category = entityManager.find(Category.class, id);
        if (category != null) {
            for (Game game : category.getGames()) {
                game.removeCategory(category);
            }
            entityManager.remove(category);
            logger.info("Successfully deleted category of id={}", id);
        } else
            logger.info("Tried to delete, but category of id={} doesn't exist", id);
    }

    @Override
    public List<CategoryNameDto> findNames() {
        TypedQuery<CategoryNameDto> q = entityManager.createQuery(
                "SELECT new org.motoc.gamelibrary.dto.CategoryNameDto(c.name) FROM Category as c",
                CategoryNameDto.class);
        return q.getResultList();
    }
}
