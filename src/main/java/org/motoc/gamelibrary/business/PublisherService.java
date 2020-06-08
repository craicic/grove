package org.motoc.gamelibrary.business;

import org.motoc.gamelibrary.business.refactor.SimpleCrudMethodsImpl;
import org.motoc.gamelibrary.model.Publisher;
import org.motoc.gamelibrary.repository.criteria.PublisherRepositoryCustom;
import org.motoc.gamelibrary.repository.jpa.PublisherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Perform business logic on the entity Publisher
 *
 * @author RouzicJ
 */
@Service
@Transactional
public class PublisherService extends SimpleCrudMethodsImpl<Publisher, JpaRepository<Publisher, Long>> {

    private static final Logger logger = LoggerFactory.getLogger(PublisherService.class);

    final private PublisherRepository publisherRepository;

    final private PublisherRepositoryCustom publisherRepositoryCustom;

    @Autowired
    public PublisherService(JpaRepository<Publisher, Long> genericRepository, PublisherRepository publisherRepository,
                            PublisherRepositoryCustom publisherRepositoryCustom) {
        super(genericRepository, Publisher.class);
        this.publisherRepository = publisherRepository;
        this.publisherRepositoryCustom = publisherRepositoryCustom;
    }
}
