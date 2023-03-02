package org.motoc.gamelibrary.repository.fragment;

import org.motoc.gamelibrary.domain.dto.ProductLineNameDto;
import org.motoc.gamelibrary.domain.model.ProductLine;

import javax.transaction.Transactional;
import java.util.List;

/**
 * It's the product line custom repository, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 */
public interface ProductLineFragmentRepository {

    /**
     * Removes a theme, removing it from game before
     */
    void remove(Long id);

    /**
     * Get all Product line's name in a custom DTO
     */
    List<ProductLineNameDto> findNames();

    @Transactional
    ProductLine saveProductLine(ProductLine p);
}
