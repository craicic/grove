package org.motoc.gamelibrary.service;

import org.motoc.gamelibrary.domain.model.Mechanism;
import org.motoc.gamelibrary.repository.jpa.MechanismRepository;
import org.motoc.gamelibrary.service.refactor.SimpleCrudMethodsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

/**
 * Perform service logic on the web entity Mechanism
 */
@Service
@Transactional
public class MechanismService extends SimpleCrudMethodsImpl<Mechanism, JpaRepository<Mechanism, Long>> {

    private static final Logger logger = LoggerFactory.getLogger(MechanismService.class);

    private final MechanismRepository mechanismRepository;


    @Autowired
    public MechanismService(MechanismRepository mechanismRepository,
                            JpaRepository<Mechanism, Long> mechanismGenericRepository) {
        super(mechanismGenericRepository, Mechanism.class);
        this.mechanismRepository = mechanismRepository;
    }

    // Methods

    /**
     * Calls the DAO to edit a new mechanism (id should be null)
     */
    public Mechanism save(@Valid Mechanism mechanism) {
        if (mechanism.getId() == null) {
            logger.debug("Trying to save new mechanism={}", mechanism.getTitle());
        } else {
            logger.debug("Trying to save new mechanism={} of id={}", mechanism.getTitle(), mechanism.getId());
        }
        return mechanismRepository.saveMechanism(mechanism);
    }

    /**
     * Calls the DAO to edit a mechanism by id
     */
    public Mechanism edit(@Valid Mechanism mechanism, Long id) {
        return mechanismRepository.findById(id)
                .map(mechanismFromPersistence -> {
                    mechanismFromPersistence.setTitle(mechanism.getTitle());
                    logger.debug("Found mechanism of id={} : {}", id, mechanismFromPersistence);
                    return mechanismRepository.saveMechanism(mechanismFromPersistence);
                })
                .orElseGet(() -> {
                    mechanism.setId(id);
                    logger.debug("No mechanism of id={} found. Set mechanism : {}", id, mechanism);
                    return mechanismRepository.saveMechanism(mechanism);
                });
    }

    /**
     * Calls the DAO to delete a mechanism by id
     */
    public void remove(Long id) {
        logger.debug("Deleting (if exist) mechanism of id=" + id);
        mechanismRepository.remove(id);
    }

    /**
     * Calls the DAO to performs a paged search on mechanism
     */
    public Page<Mechanism> quickSearch(String keyword, Pageable pageable) {
        logger.debug("Find all mechanism that contains : " + keyword);
        return mechanismRepository.findByLowerCaseTitleContaining(keyword, pageable);
    }

    /**
     * Calls the DAO to search all mechanisms
     */
    public List<Mechanism> findAll() {
        logger.debug("Find all mechanisms");
        return mechanismRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }
}
