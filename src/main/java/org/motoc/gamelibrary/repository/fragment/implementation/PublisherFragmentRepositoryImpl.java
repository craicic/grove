package org.motoc.gamelibrary.repository.fragment.implementation;

import org.motoc.gamelibrary.dto.PublisherNameDto;
import org.motoc.gamelibrary.model.Contact;
import org.motoc.gamelibrary.model.GameCopy;
import org.motoc.gamelibrary.model.Publisher;
import org.motoc.gamelibrary.repository.fragment.PublisherFragmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * It's the category custom repository implementation, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 */
@Repository
public class PublisherFragmentRepositoryImpl implements PublisherFragmentRepository {

    private static final Logger logger = LoggerFactory.getLogger(PublisherFragmentRepositoryImpl.class);

    private final EntityManager entityManager;

    @Autowired
    public PublisherFragmentRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void remove(Long id) {
        Publisher publisher = entityManager.find(Publisher.class, id);

        Contact contact = publisher.getContact();

        if (contact != null) {
            publisher.removeContact(contact);
            Contact contactFromDb = entityManager.find(Contact.class, contact.getId());
            if (contactFromDb.getCreator() == null && contactFromDb.getAccount() == null && contactFromDb.getSeller() == null)
                entityManager.remove(contactFromDb);
        }
        for (GameCopy copy : publisher.getCopies()) {
            copy.removePublisher(publisher);
        }
        entityManager.remove(publisher);
    }

    @Override
    public void removeContact(Long publisherId, Long contactId) {
        Publisher publisher = entityManager.find(Publisher.class, publisherId);
        Contact contact = entityManager.find(Contact.class, contactId);
        if (contact != null
                && publisher != null
                && publisher.getContact() == contact) {
            logger.debug("passage ici!");
            publisher.removeContact(contact);
            entityManager.remove(contact);
        }
    }

    @Override
    public List<PublisherNameDto> findNames() {

        TypedQuery<PublisherNameDto> q = entityManager.createQuery(
                "SELECT new org.motoc.gamelibrary.dto.PublisherNameDto(p.name) FROM Publisher as p",
                PublisherNameDto.class);
        return q.getResultList();
    }
}
