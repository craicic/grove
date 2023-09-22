package org.motoc.gamelibrary.service;

import org.motoc.gamelibrary.domain.dto.MechanismDto;
import org.motoc.gamelibrary.domain.dto.MechanismNameDto;
import org.motoc.gamelibrary.mapper.MechanismMapper;
import org.motoc.gamelibrary.repository.jpa.MechanismRepository;
import org.motoc.gamelibrary.technical.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;

/**
 * Perform service logic on the web entity Mechanism
 */
@Service
@Transactional
public class MechanismService {

    private static final Logger logger = LoggerFactory.getLogger(MechanismService.class);

    private final MechanismRepository repository;

    final private MechanismMapper mapper;

    @Autowired
    public MechanismService(MechanismRepository repository) {
        this.repository = repository;
        this.mapper = MechanismMapper.INSTANCE;
    }

    public MechanismDto save(@Valid MechanismNameDto t) {
        return mapper.mechanismToDto(repository.save(mapper.mechanismNameDtoToMechanism(t)));
    }


    public long count() {
        return repository.count();
    }


    public MechanismDto findById(long id) {
        return mapper.mechanismToDto(repository.findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException("No mechanism of id=" + id + " found.");
                }));
    }

    public Page<MechanismDto> findPage(Pageable pageable) {
        return mapper.pageToPageDto(repository.findAll(pageable));
    }


    /**
     * Calls the DAO to edit a new mechanism (id should be null)
     */
    public MechanismDto save(@Valid MechanismDto mechanism) {
        if (mechanism.getId() == null) {
            logger.debug("Trying to save new mechanism={}", mechanism.getTitle());
        } else {
            logger.debug("Trying to save new mechanism={} of id={}", mechanism.getTitle(), mechanism.getId());
        }
        return mapper.mechanismToDto(repository.saveMechanism(mapper.dtoToMechanism(mechanism)));
    }

    /**
     * Calls the DAO to edit a mechanism by id
     */
    public MechanismDto edit(@Valid MechanismDto mechanism, Long id) {
        return mapper.mechanismToDto(repository.findById(id)
                .map(mechanismFromPersistence -> {
                    mechanismFromPersistence.setTitle(mapper.dtoToMechanism(mechanism).getTitle());
                    logger.debug("Found mechanism of id={} : {}", id, mechanismFromPersistence);
                    return repository.saveMechanism(mechanismFromPersistence);
                })
                .orElseGet(() -> {
                    logger.debug("No mechanism of id=" + id +" found.");
                   throw new NotFoundException("No mechanism of id=" + id + "found.");
                }));
    }

    /**
     * Calls the DAO to delete a mechanism by id
     */
    public void remove(Long id) {
        logger.debug("Deleting (if exist) mechanism of id=" + id);
        repository.remove(id);
    }

    /**
     * Calls the DAO to performs a paged search on mechanism
     */
    public Page<MechanismDto> quickSearch(String keyword, Pageable pageable) {
        logger.debug("Find all mechanism that contains : " + keyword);
        return mapper.pageToPageDto(repository.findByLowerCaseTitleContaining(keyword, pageable));
    }

    /**
     * Calls the DAO to search all mechanisms
     */
    public List<MechanismDto> findAll() {
        logger.debug("Find all mechanisms");
        return mapper.mechanismsToDto(repository.findAll(Sort.by(Sort.Direction.ASC, "title")));
    }
}
