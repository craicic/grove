package org.motoc.gamelibrary.service;

import org.motoc.gamelibrary.domain.dto.CreatorNameDto;
import org.motoc.gamelibrary.domain.model.Creator;
import org.motoc.gamelibrary.repository.jpa.CreatorRepository;
import org.motoc.gamelibrary.service.refactor.SimpleCrudMethodsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

/**
 * Perform service logic on the entity Creator
 */
@Service
@Transactional
public class CreatorService extends SimpleCrudMethodsImpl<Creator, JpaRepository<Creator, Long>> {

    private static final Logger logger = LoggerFactory.getLogger(CreatorService.class);

    private final CreatorRepository creatorRepository;


    @Autowired
    public CreatorService(JpaRepository<Creator, Long> genericRepository,
                          CreatorRepository creatorRepository) {
        super(genericRepository, Creator.class);
        this.creatorRepository = creatorRepository;
    }


    /**
     * Edits a creator by id
     */
    public Creator edit(@Valid Creator creator, Long id) {
        return creatorRepository.findById(id)
                .map(creatorFromPersistence -> {
                    creatorFromPersistence.setFirstName(creator.getFirstName());
                    creatorFromPersistence.setLastName(creator.getLastName());
                    creatorFromPersistence.setRole(creator.getRole());
                    creatorFromPersistence.setContact(creator.getContact());
                    logger.debug("Found creator of id={} : {}", id, creatorFromPersistence);
                    return creatorRepository.save(creatorFromPersistence);
                })
                .orElseGet(() -> {
                    creator.setId(id);
                    logger.debug("No creator of id={} found. Set creator : {}", id, creator);
                    return creatorRepository.save(creator);
                });
    }


    /**
     * Calls the DAO to delete a creator by id
     */
    public void remove(Long id) {
        logger.debug("Deleting (if exist) creator of id=" + id);
        creatorRepository.remove(id);
    }

    public void removeContact(Long cId) {
        logger.debug("Deleting contact from creator of id=" + cId);
        Creator c = creatorRepository.removeContact(cId);
        logger.debug("Successfully removed contact for Creator of id={} : {}", cId, c);
    }

    public Page<Creator> quickSearch(String keyword, Pageable pageable) {
        logger.debug("Find paged creators that contains : " + keyword);
        return creatorRepository.findByLowerCaseFirstNameContainingOrLowerCaseLastNameContaining(keyword, keyword, pageable);
    }

    /**
     * Find all creator's name.
     */
    public List<CreatorNameDto> findNames() {
        return creatorRepository.findNames();
    }

    public Creator findByFullName(String name) {
        return creatorRepository.findByFullName(name);
    }
}
