package org.motoc.gamelibrary.business;

import org.motoc.gamelibrary.business.refactor.SimpleCrudMethodsImpl;
import org.motoc.gamelibrary.dto.PublisherNameDto;
import org.motoc.gamelibrary.model.Publisher;
import org.motoc.gamelibrary.repository.criteria.PublisherRepositoryCustom;
import org.motoc.gamelibrary.repository.jpa.ContactRepository;
import org.motoc.gamelibrary.repository.jpa.PublisherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Perform business logic on the entity Publisher
 */
@Service
@Transactional
public class PublisherService extends SimpleCrudMethodsImpl<Publisher, JpaRepository<Publisher, Long>> {

    private static final Logger logger = LoggerFactory.getLogger(PublisherService.class);

    final private PublisherRepository publisherRepository;

    final private PublisherRepositoryCustom publisherRepositoryCustom;

    final private ContactRepository contactRepository;

    @Autowired
    public PublisherService(JpaRepository<Publisher, Long> genericRepository,
                            PublisherRepository publisherRepository,
                            PublisherRepositoryCustom publisherRepositoryCustom,
                            ContactRepository contactRepository) {
        super(genericRepository, Publisher.class);
        this.publisherRepository = publisherRepository;
        this.publisherRepositoryCustom = publisherRepositoryCustom;
        this.contactRepository = contactRepository;
    }


    /**
     * Persist a new publisher by id (if a contact is associated, this one must be new)
     */
    public Publisher save(Publisher publisher, boolean hasContact) {
        if (hasContact) {
            long contactId = contactRepository.save(publisher.getContact()).getId();
            publisher.getContact().setId(contactId);
        }
        return publisherRepository.save(publisher);
    }

    /**
     * Edits a publisher by id
     */
    public Publisher edit(Publisher publisher, Long id) {
        return publisherRepository.findById(id)
                .map(publisherFromPersistence -> {
                    publisherFromPersistence.setName(publisher.getName());
                    publisherFromPersistence.setContact(publisher.getContact());
                    logger.debug("Found publisher of id={} : {}", id, publisherFromPersistence);
                    return publisherRepository.save(publisherFromPersistence);
                })
                .orElseGet(() -> {
                    publisher.setId(id);
                    logger.debug("No publisher of id={} found. Set theme : {}", id, publisher);
                    return publisherRepository.save(publisher);
                });
    }

    /**
     * Calls the DAO to delete a publisher by id
     */
    public void remove(Long id) {
        logger.debug("deleting (if exist) publisher of id=" + id);
        publisherRepositoryCustom.remove(id);
    }

    public Page<Publisher> quickSearch(String keyword, Pageable pageable) {
        logger.debug("Find paged publishers that contains : " + keyword);
        return publisherRepository.findByLowerCaseNameContaining(keyword, pageable);
    }

    public void removeContact(Long publisherId, Long contactId) {
        logger.debug("deleting (if exist) contact of id=" + contactId + " from publisher of id=" + publisherId);
        publisherRepositoryCustom.removeContact(publisherId, contactId);
    }

    public List<PublisherNameDto> findNames() {
        logger.debug("Find all publishers' name");
        return publisherRepositoryCustom.findNames();
    }
}

