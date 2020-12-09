package org.motoc.gamelibrary.business;

import org.motoc.gamelibrary.business.refactor.SimpleCrudMethodsImpl;
import org.motoc.gamelibrary.model.Creator;
import org.motoc.gamelibrary.repository.criteria.CreatorRepositoryCustom;
import org.motoc.gamelibrary.repository.jpa.ContactRepository;
import org.motoc.gamelibrary.repository.jpa.CreatorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * Perform business logic on the entity Creator
 *
 * @author RouzicJ
 */
@Service
@Transactional
public class CreatorService extends SimpleCrudMethodsImpl<Creator, JpaRepository<Creator, Long>> {

    private static final Logger logger = LoggerFactory.getLogger(CreatorService.class);

    private final CreatorRepository creatorRepository;

    private final ContactRepository contactRepository;

    private final CreatorRepositoryCustom creatorRepositoryCustom;

    @Autowired
    public CreatorService(JpaRepository<Creator, Long> genericRepository,
                          CreatorRepository creatorRepository,
                          CreatorRepositoryCustom creatorRepositoryCustom,
                          ContactRepository contactRepository) {
        super(genericRepository, Creator.class);
        this.creatorRepository = creatorRepository;
        this.creatorRepositoryCustom = creatorRepositoryCustom;
        this.contactRepository = contactRepository;
    }


    /**
     * Persist a new creator by id (if a contact is associated, this one must be new)
     */
    public Creator save(@Valid Creator creator, boolean hasContact) {
        if (hasContact) {
            long contactId = contactRepository.save(creator.getContact()).getId();
            creator.getContact().setId(contactId);
        }
        return creatorRepository.save(creator);
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
     * Removes a creator
     */
    public void remove(Long id) {
        logger.debug("deleting (if exist) creator of id=" + id);
        creatorRepositoryCustom.remove(id);
    }

    public void removeContact(Long creatorId, Long contactId) {
        logger.debug("deleting (if exist) contact of id=" + contactId + " from creator of id=" + creatorId);
        creatorRepositoryCustom.removeContact(creatorId, contactId);
    }

    public Page<Creator> quickSearch(String keyword, Pageable pageable) {
        logger.debug("Find paged creators that contains : " + keyword);
        return creatorRepository.findByLowerCaseFirstNameContainingOrLowerCaseLastNameContaining(keyword, keyword, pageable);
    }
}
