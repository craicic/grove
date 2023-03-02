package org.motoc.gamelibrary.repository.fragment.implementation;

import org.motoc.gamelibrary.domain.dto.ProductLineNameDto;
import org.motoc.gamelibrary.domain.model.Game;
import org.motoc.gamelibrary.domain.model.ProductLine;
import org.motoc.gamelibrary.repository.fragment.ProductLineFragmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

/**
 * It's the product line custom repository implementation, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 */
@Repository
public class ProductLineFragmentRepositoryImpl implements ProductLineFragmentRepository {

    private static final Logger logger = LoggerFactory.getLogger(ProductLineFragmentRepositoryImpl.class);

    private final EntityManager em;

    @Autowired
    public ProductLineFragmentRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public void remove(Long id) {
        ProductLine productLine = em.find(ProductLine.class, id);
        if (productLine != null) {
            for (Game game : productLine.getGames()) {
                productLine.removeGame(game);
            }
            em.remove(productLine);
            logger.debug("Entity Manager will now handle deletion for the product line of id={}", id);
        } else
            logger.info("Tried to delete, but product line of id={} doesn't exist", id);
    }


    @Override
    @Transactional
    public ProductLine saveProductLine(ProductLine p) {
        em.persist(p);
        if (p != null && p.getId() != null) {
            logger.debug("Entity Manager will now handle persistence for the product line of name={} and id={}", p.getName(), p.getId());
        } else {
            logger.info("Tried to persist a product line but an error occurred");
        }
        return p;
    }

    @Override
    public List<ProductLineNameDto> findNames() {
        TypedQuery<ProductLineNameDto> q = em.createQuery(
                "SELECT new org.motoc.gamelibrary.domain.dto.ProductLineNameDto(p.name) FROM ProductLine as p",
                ProductLineNameDto.class);
        return q.getResultList();
    }

}

