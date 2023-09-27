package org.motoc.gamelibrary.repository.fragment.implementation;

import jakarta.persistence.EntityManager;
import org.motoc.gamelibrary.domain.model.GameCopy;
import org.motoc.gamelibrary.domain.model.Publisher;
import org.motoc.gamelibrary.repository.fragment.GameCopyFragmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GameCopyFragmentRepositoryImpl implements GameCopyFragmentRepository {

    private static final Logger logger = LoggerFactory.getLogger(GameCopyFragmentRepository.class);

    private final EntityManager entityManager;

    @Autowired
    public GameCopyFragmentRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public GameCopy addPublisher(GameCopy copy, Publisher publisher) {
        copy.addPublisher(publisher);
        entityManager.persist(copy);
        return copy;
    }

    @Override
    public GameCopy removePublisher(GameCopy copy, Publisher publisher) {
        copy.removePublisher(publisher);
        entityManager.persist(copy);
        return copy;
    }
}
