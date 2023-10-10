package org.motoc.gamelibrary.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.motoc.gamelibrary.domain.dto.PublisherDto;
import org.motoc.gamelibrary.domain.dto.PublisherNameDto;
import org.motoc.gamelibrary.domain.dto.PublisherNoIdDto;
import org.motoc.gamelibrary.domain.model.Publisher;
import org.motoc.gamelibrary.mapper.PublisherMapper;
import org.motoc.gamelibrary.repository.jpa.PublisherRepository;
import org.motoc.gamelibrary.technical.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Perform service logic on the entity Publisher
 */
@Service
@Transactional
public class PublisherService {

    private static final Logger logger = LoggerFactory.getLogger(PublisherService.class);

    final private PublisherRepository repository;
    final private PublisherMapper mapper;

    @Autowired
    public PublisherService(PublisherRepository repository) {
        this.mapper = PublisherMapper.INSTANCE;
        this.repository = repository;
    }


    public PublisherDto save(@Valid PublisherNoIdDto t) {
        return mapper.publisherToDto(repository.save(mapper.noIdDtoToPublisher(t)));
    }


    public long count() {
        return repository.count();
    }


    public PublisherDto findById(long id) {
        return mapper.publisherToDto(repository.findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException("No publisher of id=" + id + " found.");
                }));
    }

    public Page<PublisherDto> findPage(Pageable pageable) {
        return mapper.pageToPageDto(repository.findAll(pageable));
    }

    /**
     * Edits a publisher by id
     */
    public PublisherDto edit(PublisherDto publisherDto, Long id) {
        Publisher publisher = mapper.dtoToPublisher(publisherDto);
        return mapper.publisherToDto(repository.findById(id)
                .map(publisherFromPersistence -> {
                    publisherFromPersistence.setName(publisher.getName());
                    publisherFromPersistence.setContact(publisher.getContact());
                    logger.debug("Found publisher of id={} : {}", id, publisherFromPersistence);
                    return repository.save(publisherFromPersistence);
                })
                .orElseGet(() -> {
                    logger.debug("No publisher of id={} found. Set mechanism : {}", id, publisher);
                    throw new NotFoundException("No publisher of id = " + id + "found.");
                }));
    }

    /**
     * Calls the DAO to delete a publisher by id
     */
    public void remove(Long id) {
        logger.debug("Deleting (if exist) publisher of id=" + id);
        repository.remove(id);
    }

    public Page<PublisherDto> quickSearch(String keyword, Pageable pageable) {
        logger.debug("Find paged publishers that contains : " + keyword);
        return mapper.pageToPageDto(repository.findByLowerCaseNameContaining(keyword, pageable));
    }

    public void removeContact(Long pId) {
        logger.debug("Deleting contact from publisher of id=" + pId);
        Publisher p = repository.removeContact(pId);
        logger.debug("Successfully removed contact for Publisher of id={} : {}", pId, p);
    }

    public List<PublisherNameDto> findNames() {
        logger.debug("Find all publishers' name");
        return repository.findNames();
    }

    public PublisherDto save(PublisherDto p) {
        logger.debug(" publishers' name");
        if (p.getId() == null) {
            logger.debug("Trying to save new p={}", p.getName());
        } else {
            logger.debug("Trying to save new p={} of id={}", p.getName(), p.getId());
        }
        return mapper.publisherToDto(repository.savePublisher(mapper.dtoToPublisher(p)));
    }
}

