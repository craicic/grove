package org.motoc.gamelibrary.service.refactor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.util.List;

/**
 * Part of a strategy pattern, the goal is to factorize basics service methods.
 */
public interface SimpleCrudMethods<T> {

    /**
     * Persist T
     */
    T save(@Valid T t);

    /**
     * Count the number of T in persistence
     */
    long count();

    /**
     * Find T
     */
    T findById(long id);

    /**
     * Find All
     */
    List<T> findAll();

    /**
     * Find page
     */
    Page<T> findPage(Pageable pageable);

    /**
     * Delete T
     */
    void deleteOne(T t);

    /**
     * Delete by id
     */
    void deleteById(long id);
}
