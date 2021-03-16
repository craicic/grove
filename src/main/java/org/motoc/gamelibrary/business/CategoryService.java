package org.motoc.gamelibrary.business;

import org.motoc.gamelibrary.business.refactor.SimpleCrudMethodsImpl;
import org.motoc.gamelibrary.dto.CategoryNameDto;
import org.motoc.gamelibrary.model.Category;
import org.motoc.gamelibrary.repository.criteria.CategoryRepositoryCustom;
import org.motoc.gamelibrary.repository.jpa.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

/**
 * Perform business logic on the entity Category
 *
 * @author RouzicJ
 */
@Service
@Transactional
public class CategoryService extends SimpleCrudMethodsImpl<Category, JpaRepository<Category, Long>> {

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    final private CategoryRepository categoryRepository;

    final private CategoryRepositoryCustom categoryRepositoryCustom;

    @Autowired
    public CategoryService(JpaRepository<Category, Long> genericRepository, CategoryRepository categoryRepository,
                           CategoryRepositoryCustom categoryRepositoryCustom) {
        super(genericRepository, Category.class);
        this.categoryRepository = categoryRepository;
        this.categoryRepositoryCustom = categoryRepositoryCustom;
    }

    /**
     * Edits a category by id
     */
    public Category edit(@Valid Category category, Long id) {
        return categoryRepository.findById(id)
                .map(categoryFromPersistence -> {
                    categoryFromPersistence.setName(category.getName());
                    logger.debug("Found category of id={} : {}", id, categoryFromPersistence);
                    return categoryRepository.save(categoryFromPersistence);
                })
                .orElseGet(() -> {
                    category.setId(id);
                    logger.debug("No category of id={} found. Set category : {}", id, category);
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


    /**
     * Find all categories' names
     */
    public List<CategoryNameDto> findNames() {
        return categoryRepositoryCustom.findNames();
    }

    /**
     * Find all categories
     */
    public List<Category> findAll() {
        return categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }
}
