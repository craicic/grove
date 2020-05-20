package org.motoc.gamelibrary.business.refactor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudMethods<T> {

    T persist(T t);
    long count(Class<T> tClass);
    T findOne(long id, Class<T> tClass);
    Page<T> findPage(Pageable pageable);
    void deleteOne(T t);
}
