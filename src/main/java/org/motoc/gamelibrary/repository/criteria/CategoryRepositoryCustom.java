package org.motoc.gamelibrary.repository.criteria;

import org.motoc.gamelibrary.model.Category;

import java.util.List;

/**
 * Category custom repository, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 */
public interface CategoryRepositoryCustom {
    void remove(Long id);

    Category saveWithChildren(List<Category> children, Category category);

    Category saveWithParent(Category parent, Category category);

    void removeParent(Category category);

    void removeChild(Long catId, Long childId);
}
