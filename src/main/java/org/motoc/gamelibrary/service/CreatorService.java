package org.motoc.gamelibrary.service;

import org.motoc.gamelibrary.domain.dto.CreatorDto;
import org.motoc.gamelibrary.domain.dto.CreatorNameDto;
import org.motoc.gamelibrary.domain.model.Creator;
import org.motoc.gamelibrary.mapper.CreatorMapper;
import org.motoc.gamelibrary.repository.jpa.CreatorRepository;
import org.motoc.gamelibrary.technical.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

/**
 * Perform service logic on the entity Creator
 */
@Service
@Transactional
public class CreatorService {

    private static final Logger logger = LoggerFactory.getLogger(CreatorService.class);

    private final CreatorMapper mapper;
    private final CreatorRepository repository;


    @Autowired
    public CreatorService(CreatorRepository repository) {
        this.mapper = CreatorMapper.INSTANCE;
        this.repository = repository;
    }

    public CreatorDto save(@Valid CreatorDto creatorDto) {
        return mapper.creatorToDto(repository.save(mapper.dtoToCreator(creatorDto)));
    }


    public long count() {
        return repository.count();
    }


    public CreatorDto findById(long id) {
        return mapper.creatorToDto(repository.findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException("No category of id=" + id + " found.");
                }));
    }


    public Page<CreatorDto> findPage(Pageable pageable) {
        return mapper.pageToPageDto(repository.findAll(pageable));
    }


    /**
     * Edits a creator by id
     */
    public Creator edit(@Valid Creator creator, Long id) {
        return repository.findById(id)
                .map(creatorFromPersistence -> {
                    creatorFromPersistence.setFirstName(creator.getFirstName());
                    creatorFromPersistence.setLastName(creator.getLastName());
                    creatorFromPersistence.setRole(creator.getRole());
                    creatorFromPersistence.setContact(creator.getContact());
                    logger.debug("Found creator of id={} : {}", id, creatorFromPersistence);
                    return repository.save(creatorFromPersistence);
                })
                .orElseGet(() -> {
                    creator.setId(id);
                    logger.debug("No creator of id={} found. Set creator : {}", id, creator);
                    return repository.save(creator);
                });
    }


    /**
     * Calls the DAO to delete a creator by id
     */
    public void remove(Long id) {
        logger.debug("Deleting (if exist) creator of id=" + id);
        repository.remove(id);
    }

    public void removeContact(Long cId) {
        logger.debug("Deleting contact from creator of id=" + cId);
        Creator c = repository.removeContact(cId);
        logger.debug("Successfully removed contact for Creator of id={} : {}", cId, c);
    }

    public Page<CreatorDto> quickSearch(String keyword, Pageable pageable) {
        logger.debug("Find paged creators that contains : " + keyword);
        return mapper.pageToPageDto(
                repository.findByLowerCaseFirstNameContainingOrLowerCaseLastNameContaining(keyword, keyword, pageable));
    }

    /**
     * Find all creator's name.
     */
    public List<CreatorNameDto> findNames() {
        return repository.findNames();
    }

    public Creator findByFullName(String name) {
        return repository.findByFullName(name);
    }
}
