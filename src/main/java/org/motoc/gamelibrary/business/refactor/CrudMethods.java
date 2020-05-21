package org.motoc.gamelibrary.business.refactor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Part of a strategy pattern, the goal is to factorize basics business methods.
 *
 * @author RouzicJ
 */
public interface CrudMethods<T> {

    T persist(T t);

    long count(Class<T> tClass);

    T findById(long id, Class<T> tClass);

    Page<T> findPage(Pageable pageable);

    void deleteOne(T t);
}
