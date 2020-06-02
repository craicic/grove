package org.motoc.gamelibrary.repository;

/**
 * Category custom repository, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 */
public interface CategoryRepositoryCustom {
    void remove(Long id);
}
