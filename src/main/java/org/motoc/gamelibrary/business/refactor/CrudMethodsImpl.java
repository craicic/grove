package org.motoc.gamelibrary.business.refactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Part of a strategy pattern, the goal is to factorize basics business methods.
 *
 * @author RouzicJ
 */
public abstract class CrudMethodsImpl<T, T_Repo extends JpaRepository<T, Long>> implements CrudMethods<T> {

    private final T_Repo genericRepository;

    private static final Logger logger = LoggerFactory.getLogger(CrudMethodsImpl.class);

    public CrudMethodsImpl(T_Repo genericRepository) {
        this.genericRepository = genericRepository;
    }

    @Override
    public T persist(T t) {
        T result = genericRepository.saveAndFlush(t);
        logger.debug("Persisted a {} : {}", result.getClass().getSimpleName().toLowerCase(), result.toString());
        return result;
    }

    @Override
    public long count(Class<T> tClass) {
        long result = genericRepository.count();
        logger.debug("Count {}={}", tClass.getSimpleName().toLowerCase(), result);
        return result;
    }

    @Override
    public T findById(long id, Class<T> tClass) {
        Optional<T> optional = genericRepository.findById(id);
        if (optional.isPresent()) {
            T result = optional.get();
            logger.debug("Found {} : {}", tClass.getSimpleName().toLowerCase(), result);
            return result;
        } else {
            logger.debug("No {} found for id={}", tClass.getSimpleName().toLowerCase(), id);
            return null;
        }
    }

    @Override
    public Page<T> findPage(Pageable pageable) {
        Page<T> result = genericRepository.findAll(pageable);
        logger.debug("Found {} element(s) through {} page(s)", result.getTotalElements(), result.getTotalPages());
        return result;
    }

    @Override
    public void deleteOne(T t) {
        genericRepository.delete(t);
        logger.debug("Deleted {} : {}", t.getClass().getSimpleName().toLowerCase(), t);
    }
}
