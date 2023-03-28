package org.motoc.gamelibrary.repository.fragment.implementation;

import org.motoc.gamelibrary.domain.model.Category;
import org.motoc.gamelibrary.domain.model.Game;
import org.motoc.gamelibrary.repository.fragment.CategoryFragmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

/**
 * It's the category custom repository implementation, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 */
@Repository
public class CategoryFragmentRepositoryImpl implements CategoryFragmentRepository {

    private static final Logger logger = LoggerFactory.getLogger(CategoryFragmentRepositoryImpl.class);

    private final EntityManager entityManager;

    @Autowired
    public CategoryFragmentRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    @Transactional
    public void remove(Long id) {
        Category category = entityManager.find(Category.class, id);
        if (category != null) {
            for (Game game : category.getGames()) {
                game.removeCategory(category);
            }
            entityManager.remove(category);
            logger.debug("Entity Manager will now handle deletion for the category of id={}", id);
        } else
            logger.info("Tried to delete, but category of id={} doesn't exist", id);
    }

    @Override
    public List<String> findTitles() {
        TypedQuery<String> q = entityManager.createQuery(
                "SELECT c.title FROM Category as c",
                String.class);
        return q.getResultList();
    }
}
