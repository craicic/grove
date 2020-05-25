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
public abstract class SimpleCrudMethodsImpl<T, T_Repo extends JpaRepository<T, Long>> implements SimpleCrudMethods<T> {

    private final T_Repo genericRepository;

    private static final Logger logger = LoggerFactory.getLogger(SimpleCrudMethodsImpl.class);

    final Class<T> type;

    public SimpleCrudMethodsImpl(T_Repo genericRepository, Class<T> type) {
        this.genericRepository = genericRepository;
        this.type = type;
    }

    @Override
    public T save(T t) {
        T result = genericRepository.saveAndFlush(t);
        logger.debug("Saved a {} : {}", result.getClass().getSimpleName().toLowerCase(), result.toString());
        return result;
    }

    @Override
    public long count() {
        long result = genericRepository.count();
        logger.debug("Count {}={}", type.getSimpleName().toLowerCase(), result);
        return result;
    }

    @Override
    public T findById(long id) {
        Optional<T> optional = genericRepository.findById(id);
        if (optional.isPresent()) {
            T result = optional.get();
            logger.debug("Found {} : {}", type.getSimpleName().toLowerCase(), result);
            return result;
        } else {
            logger.debug("No {} found for id={}", type.getSimpleName().toLowerCase(), id);
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

    @Override
    public void deleteById(long id) {
        genericRepository.deleteById(id);
        logger.debug("Deleted {} of id {}", type.getSimpleName().toLowerCase(), id);
    }
}
