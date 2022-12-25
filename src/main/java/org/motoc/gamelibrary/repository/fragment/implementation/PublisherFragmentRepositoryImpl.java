package org.motoc.gamelibrary.repository.fragment.implementation;

import org.motoc.gamelibrary.domain.dto.PublisherNameDto;
import org.motoc.gamelibrary.domain.model.GameCopy;
import org.motoc.gamelibrary.domain.model.Publisher;
import org.motoc.gamelibrary.repository.fragment.PublisherFragmentRepository;
import org.motoc.gamelibrary.technical.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

/**
 * It's the category custom repository implementation, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 */
@Repository
public class PublisherFragmentRepositoryImpl implements PublisherFragmentRepository {

    private static final Logger logger = LoggerFactory.getLogger(PublisherFragmentRepositoryImpl.class);

    private final EntityManager em;

    @Autowired
    public PublisherFragmentRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public void remove(Long id) {
        Publisher publisher = em.find(Publisher.class, id);

        for (GameCopy copy : publisher.getCopies()) {
            copy.removePublisher(publisher);
        }
        em.remove(publisher);
    }

    @Override
    @Transactional
    public Publisher removeContact(Long pId) {
        Publisher p = em.find(Publisher.class, pId);
        if (p != null) {
            p.setContact(null);
            return p;
        } else {
            throw new NotFoundException("No publisher of id=" + pId + " found");
        }
    }

    @Override
    public List<PublisherNameDto> findNames() {

        TypedQuery<PublisherNameDto> q = em.createQuery(
                "SELECT new org.motoc.gamelibrary.domain.dto.PublisherNameDto(p.name) FROM Publisher as p",
                PublisherNameDto.class);
        return q.getResultList();
    }

    @Override
    @Transactional
    public Publisher savePublisher(Publisher p) {
        em.persist(p);
        if (p != null && p.getId() != null) {
            logger.info("Successfully persisted publisher of name={} and id={}", p.getName(), p.getId());
        } else {
            logger.info("Tried to persist a publisher but an error occurred");
        }
        return p;
    }
}
