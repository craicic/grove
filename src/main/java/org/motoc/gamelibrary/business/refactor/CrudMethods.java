package org.motoc.gamelibrary.business.refactor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Part of a strategy pattern, the goal is to factorize basics business methods.
 *
 * @author RouzicJ
 */
public interface CrudMethods<T> {

    /**
     * Persist T
     */
    T persist(T t);

    /**
     * Count the number of T in persistence, the argument is useful for logging
     */
    long count(Class<T> tClass);

    /**
     * Find T. The second argument is useful for logging
     */
    T findById(long id, Class<T> tClass);

    /**
     * Find page
     */
    Page<T> findPage(Pageable pageable);

    /**
     * Delete T
     */
    void deleteOne(T t);
}
