package org.motoc.gamelibrary.repository.fragment.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.motoc.gamelibrary.domain.dto.CreatorNameDto;
import org.motoc.gamelibrary.domain.model.Creator;
import org.motoc.gamelibrary.domain.model.Game;
import org.motoc.gamelibrary.repository.fragment.CreatorFragmentRepository;
import org.motoc.gamelibrary.technical.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Creator custom repository implementation, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 */
@Repository
public class CreatorFragmentRepositoryImpl implements CreatorFragmentRepository {

    private static final Logger logger = LoggerFactory.getLogger(CreatorFragmentRepositoryImpl.class);

    private final EntityManager em;

    @Autowired
    public CreatorFragmentRepositoryImpl(EntityManager em) {
        this.em = em;
    }


    @Override
    @Transactional
    public void remove(Long id) {
        Creator creator = em.find(Creator.class, id);

        for (Game game : creator.getGames()) {
            game.removeCreator(creator);
        }
        em.remove(creator);
    }

    @Override
    @Transactional
    public Creator removeContact(Long pId) {
        Creator c = em.find(Creator.class, pId);
        if (c != null) {
            c.setContact(null);
            return c;
        } else {
            throw new NotFoundException("No creator of id=" + pId + " found");
        }
    }

    @Override
    public List<CreatorNameDto> findNames() {
        TypedQuery<CreatorNameDto> q = em.createQuery(
                "SELECT new org.motoc.gamelibrary.domain.dto.CreatorNameDto(c.firstName, c.lastName) FROM Creator as c",
                CreatorNameDto.class);
        return q.getResultList();
    }
}
