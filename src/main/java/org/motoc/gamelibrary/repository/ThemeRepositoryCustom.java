package org.motoc.gamelibrary.repository;

/**
 * It's the theme custom repository, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 */
public interface ThemeRepositoryCustom {

    /**
     * @param id
     */
    void remove(Long id);
}
