package org.motoc.gamelibrary.business;

import org.motoc.gamelibrary.business.refactor.SimpleCrudMethodsImpl;
import org.motoc.gamelibrary.dto.CategoryNameDto;
import org.motoc.gamelibrary.model.Category;
import org.motoc.gamelibrary.repository.criteria.CategoryRepositoryCustom;
import org.motoc.gamelibrary.repository.jpa.CategoryRepository;
import org.motoc.gamelibrary.technical.exception.ChildAndParentException;
import org.motoc.gamelibrary.technical.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Arrays;
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
                    for (Category child : category.getChildren()) {
                        if (child.equals(category.getParent()))
                            throw new ChildAndParentException("This children is also the parent of the given category");
                    }
                    categoryFromPersistence.setParent(category.getParent());
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
     * Adds a children list to the category of id=catId
     */
    public Category addChildren(List<Long> childrenIds, Long catId) {
        return categoryRepository.findById(catId)
                .map(category -> {
                    if (category.getParent() != null)
                        throw new ChildAndParentException("The parent category : " + category
                                + " has already a parent : " + category.getParent());

                    List<Category> children = categoryRepository.findAllById(childrenIds);

                    for (Category child : children) {
                        /* if a candidate child is already a child, throw exception */
                        if (child.getParent() != null)
                            throw new ChildAndParentException("The candidate child : " + child
                                    + " has already a parent  : " + child.getParent());
                        /* if a candidate child already have a child, throw exception */
                        if (!child.getChildren().isEmpty())
                            throw new ChildAndParentException("The candidate child : " + child
                                    + " has already at least one child : children.size()=" + child.getChildren().size());
                    }

                    return categoryRepositoryCustom.saveWithChildren(children, category);
                })
                .orElseThrow(
                        () -> {
                            logger.debug("No category of id={} found.", catId);
                            throw new NotFoundException(catId);
                        }
                );
    }

    /**
     * Adds a parent to the category of id=catId
     */
    public Category addParent(Long parentId, Long catId) {

        Category parent = categoryRepository.findById(parentId).orElseThrow(() -> {
            logger.debug("No parent category of id={} found.", catId);
            throw new NotFoundException(catId);
        });


        return categoryRepository.findById(catId)
                .map(category -> {
                    if (parent.getChildren().contains(category))
                        throw new ChildAndParentException("Category : " + parent.getName() + " is already the parent of " + category.getName());
                    if (category.getParent() != null)
                        throw new ChildAndParentException("The category of id=" + catId + " already has a parent");
                    if (!category.getChildren().isEmpty())
                        throw new ChildAndParentException("Category " + category.getName() + " has at least one child : it can't have a parent");
                    return categoryRepositoryCustom.saveWithParent(parent, category);
                })
                .orElseThrow(
                        () -> {
                            logger.debug("No category of id={} found.", catId);
                            throw new NotFoundException(catId);
                        }
                );
    }

    /**
     * Removes the parent of a category
     */
    public void removeParent(Long catId) {
        categoryRepository.findById(catId)
                .ifPresentOrElse(
                        category -> {
                            if (category.getParent() != null)
                                categoryRepositoryCustom.removeParent(category);
                            else
                                logger.warn("Category of id={} already has a parent", category.getId());
                        },
                        () -> {
                            logger.debug("No category of id={} found.", catId);
                            throw new NotFoundException(catId);
                        });
    }

    /**
     * Removes a particular child
     */
    public void removeChild(Long catId, Long childId) {
        List<Long> ids = Arrays.asList(catId, childId);
        categoryRepository.findAllById(ids);
        categoryRepositoryCustom.removeChild(catId, childId);
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
