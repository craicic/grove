package org.motoc.gamelibrary.repository.criteria;

import org.motoc.gamelibrary.dto.CategoryNameDto;

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
     * Get all Categories' name in a custom DTO
     */
    List<CategoryNameDto> findNames();
}
