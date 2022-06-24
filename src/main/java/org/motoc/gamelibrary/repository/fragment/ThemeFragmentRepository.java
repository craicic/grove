package org.motoc.gamelibrary.repository.fragment;

/**
 * It's the theme custom repository, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 */
public interface ThemeFragmentRepository {

    /**
     * Removes a theme, removing it from game before
     */
    void remove(Long id);
}
