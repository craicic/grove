package org.motoc.gamelibrary.repository.criteria.implementation;

import org.motoc.gamelibrary.dto.CreatorNameDto;
import org.motoc.gamelibrary.model.Contact;
import org.motoc.gamelibrary.model.Creator;
import org.motoc.gamelibrary.model.Game;
import org.motoc.gamelibrary.repository.criteria.CreatorRepositoryCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Creator custom repository implementation, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 */
@Repository
public class CreatorRepositoryCustomImpl implements CreatorRepositoryCustom {

    private static final Logger logger = LoggerFactory.getLogger(CreatorRepositoryCustomImpl.class);

    private final EntityManager entityManager;

    @Autowired
    public CreatorRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public void remove(Long id) {
        Creator creator = entityManager.find(Creator.class, id);
        Contact contact = creator.getContact();

        if (contact != null) {
            creator.removeContact(contact);
            Contact contactFromDb = entityManager.find(Contact.class, contact.getId());
            if (contactFromDb.getPublisher() == null && contactFromDb.getAccount() == null && contactFromDb.getSeller() == null)
                entityManager.remove(contactFromDb);
        }

        for (Game game : creator.getGames()) {
            game.removeCreator(creator);
        }

        entityManager.remove(creator);
    }

    @Override
    public void removeContact(Long creatorId, Long contactId) {
        Creator creator = entityManager.find(Creator.class, creatorId);
        Contact contact = entityManager.find(Contact.class, contactId);

        if (contact != null
                && creator != null
                && creator.getContact() == contact) {
            creator.removeContact(contact);
            entityManager.remove(contact);
        }
    }

    @Override
    public List<CreatorNameDto> findNames() {
        TypedQuery<CreatorNameDto> q = entityManager.createQuery(
                "SELECT new org.motoc.gamelibrary.dto.CreatorNameDto(c.firstName, c.lastName) FROM Creator as c",
                CreatorNameDto.class);
        return q.getResultList();
    }
}
