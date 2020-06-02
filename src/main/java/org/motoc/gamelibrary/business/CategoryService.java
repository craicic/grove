package org.motoc.gamelibrary.business;

import org.motoc.gamelibrary.business.refactor.SimpleCrudMethodsImpl;
import org.motoc.gamelibrary.model.Category;
import org.motoc.gamelibrary.repository.CategoryRepository;
import org.motoc.gamelibrary.repository.CategoryRepositoryCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * Perform business logic on the web entity Category
 *
 * @author RouzicJ
 */
@Service
@Transactional
public class CategoryService extends SimpleCrudMethodsImpl<Category, JpaRepository<Category, Long>> {

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    final private CategoryRepository categoryRepository;

    final private CategoryRepositoryCustom categoryRepositoryCustom;

    public CategoryService(JpaRepository<Category, Long> genericRepository, CategoryRepository categoryRepository,
                           CategoryRepositoryCustom categoryRepositoryCustom) {
        super(genericRepository, Category.class);
        this.categoryRepository = categoryRepository;
        this.categoryRepositoryCustom = categoryRepositoryCustom;
    }

    /**
     * Edits a category line by id
     */
    public Category edit(@Valid Category category, Long id) {
        return categoryRepository.findById(id)
                .map(categoryFromPersistence -> {
                    categoryFromPersistence.setName(category.getName());
                    categoryFromPersistence.setParent(category.getParent());
                    logger.debug("Found category of id={} : {}", id, categoryFromPersistence);
                    return categoryRepository.save(categoryFromPersistence);
                })
                .orElseGet(() -> {
                    category.setId(id);
                    logger.debug("No product line of id={} found. Set category : {}", id, category);
                    return categoryRepository.save(category);
                });
    }

    /**
     * Calls the DAO to delete a category by id
     */
    public void remove(Long id) {
        logger.debug("deleting (if exist) category of id=" + id);
        categoryRepositoryCustom.remove(id);
    }
}
