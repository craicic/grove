package org.motoc.gamelibrary.repository.criteria;

import org.motoc.gamelibrary.dto.ProductLineNameDto;

import java.util.List;

/**
 * It's the product line custom repository, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 */
public interface ProductLineRepositoryCustom {

    /**
     * Removes a theme, removing it from game before
     */
    void remove(Long id);

    /**
     * Get all Product line's name in a custom DTO
     */
    List<ProductLineNameDto> findNames();
}
