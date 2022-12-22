package org.motoc.gamelibrary.service.refactor;

import org.motoc.gamelibrary.technical.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.Valid;
import java.util.List;

/**
 * Part of a strategy pattern, the goal is to factorize basics service methods.
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
    public T save(@Valid T t) {
        T result = genericRepository.save(t);
        logger.debug("Saved a {} : {}", type.getSimpleName().toLowerCase(), result.toString());
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
        return genericRepository.findById(id)
                .map(result -> {
                    logger.debug("Found {} for id={}", type.getSimpleName().toLowerCase(), id);
                    return result;
                })
                .orElseThrow(() -> {
                    logger.warn("No {} found for id={}", type.getSimpleName().toLowerCase(), id);
                    throw new NotFoundException("No " + type.getSimpleName().toLowerCase() + " of id=" + id + " found.");
                });
    }

    @Override
    public List<T> findAll() {
        List<T> result = genericRepository.findAll();
        logger.debug("Found {} element(s) of type", result.size());
        return result;
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
