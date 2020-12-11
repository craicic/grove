package org.motoc.gamelibrary.repository.criteria;

import org.motoc.gamelibrary.dto.CategoryNameDto;
import org.motoc.gamelibrary.model.Category;

import java.util.List;

/**
 * Category custom repository, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 *
 * @author RouzicJ
 */
public interface CategoryRepositoryCustom {

    /**
     * Removes carefully the category of id
     */
    void remove(Long id);

    /**
     * Adds a list of children to a category, this methods checks if the child is already contains in the children of
     * this category.
     */
    Category saveWithChildren(List<Category> children, Category category);

    /**
     * Adds a parent to a given category
     */
    Category saveWithParent(Category parent, Category category);

    /**
     * Removes a parent of a given category
     */
    void removeParent(Category category);

    /**
     * Removes a child of a given category
     */
    void removeChild(Long catId, Long childId);

    /**
     * Get all Categories' name in a custom DTO
     */
    List<CategoryNameDto> findNames();
}
