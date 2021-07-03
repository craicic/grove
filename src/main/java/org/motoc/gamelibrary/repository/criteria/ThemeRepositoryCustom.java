package org.motoc.gamelibrary.repository.criteria;

/**
 * It's the theme custom repository, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 */
public interface ThemeRepositoryCustom {

    /**
     * Removes a theme, removing it from game before
     */
    void remove(Long id);
}
