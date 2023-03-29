package org.motoc.gamelibrary.repository.fragment.implementation;

import org.motoc.gamelibrary.domain.model.Game;
import org.motoc.gamelibrary.domain.model.Mechanism;
import org.motoc.gamelibrary.repository.fragment.MechanismFragmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

/**
 * It's the mechanism custom repository implementation, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 */
@Repository
public class MechanismFragmentRepositoryImpl implements MechanismFragmentRepository {

    private static final Logger logger = LoggerFactory.getLogger(MechanismFragmentRepositoryImpl.class);

    private final EntityManager em;


    @Autowired
    public MechanismFragmentRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public void remove(Long id) {

        Mechanism mechanism = em.find(Mechanism.class, id);
        if (mechanism != null) {
            for (Game game : mechanism.getGames()) {
                game.removeMechanism(mechanism);
            }
            em.remove(mechanism);
            logger.debug("Entity Manager will now handle deletion for the mechanism of id={}", id);
        } else
            logger.info("Tried to delete, but mechanism of id={} doesn't exist", id);
    }

    @Override
    @Transactional
    public Mechanism saveMechanism(Mechanism mechanism) {
        em.persist(mechanism);
        if (mechanism != null && mechanism.getId() != null) {
            logger.debug("Entity Manager will now handle persistence for the mechanism of name={} and id={}", mechanism.getTitle(), mechanism.getId());
        } else {
            logger.info("Tried to persist a mechanism but an error occurred");
        }
        return mechanism;
    }

}
