package org.motoc.gamelibrary.repository;

/**
 * It's the product line custom repository, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 */
public interface ProductLineRepositoryCustom {

    /**
     * Removes a theme, removing it from game before
     */
    void remove(Long id);
}
