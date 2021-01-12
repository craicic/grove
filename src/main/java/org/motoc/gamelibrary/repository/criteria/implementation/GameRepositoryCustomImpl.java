package org.motoc.gamelibrary.repository.criteria.implementation;

import org.motoc.gamelibrary.dto.GameNameDto;
import org.motoc.gamelibrary.repository.criteria.GameRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Game custom repository implementation, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 *
 * @author RouzicJ
 */
@Repository
public class GameRepositoryCustomImpl implements GameRepositoryCustom {

    private final EntityManager entityManager;

    @Autowired
    public GameRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<GameNameDto> findNames() {
        TypedQuery<GameNameDto> q = entityManager.createQuery(
                "SELECT new org.motoc.gamelibrary.dto.GameNameDto(g.name) FROM Game as g",
                GameNameDto.class
        );
        return q.getResultList();
    }
}
