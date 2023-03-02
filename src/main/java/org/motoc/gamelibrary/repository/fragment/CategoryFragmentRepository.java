package org.motoc.gamelibrary.repository.fragment;

import java.util.List;

/**
 * Category custom repository, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 */
public interface CategoryFragmentRepository {

    /**
     * Removes carefully the category of id
     */
    void remove(Long id);


    /**
     * Get all Categories' name in a custom DTO
     */
    List<String> findNames();
}
