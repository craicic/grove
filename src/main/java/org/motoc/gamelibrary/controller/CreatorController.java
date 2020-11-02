package org.motoc.gamelibrary.controller;

import org.motoc.gamelibrary.business.CreatorService;
import org.motoc.gamelibrary.dto.CreatorDto;
import org.motoc.gamelibrary.mapper.CreatorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Defines creator's endpoints
 *
 * @author RouzicJ
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CreatorController {

    private static final Logger logger = LoggerFactory.getLogger(CreatorController.class);

    private final CreatorService service;

    private final CreatorMapper mapper;

    @Autowired
    public CreatorController(CreatorService service) {
        this.service = service;
        this.mapper = CreatorMapper.INSTANCE;
    }

    @GetMapping("/admin/creators/count")
    Long count() {
        logger.trace("count called");
        return service.count();
    }

    @GetMapping("/admin/creators")
    CreatorDto findById(@RequestParam(value = "id") Long id) {
        logger.trace("findById(id) called");
        return mapper.creatorToDto(service.findById(id));
    }

    @GetMapping("/admin/creators/page")
    Page<CreatorDto> findPage(Pageable pageable) {
        logger.trace("findPage(pageable) called");
        return mapper.pageToPageDto(service.findPage(pageable));
    }

    @PostMapping("/admin/creators")
    CreatorDto save(@RequestBody @Valid CreatorDto creator) {
        logger.trace("save(creator) called");
        return mapper.creatorToDto(service.save(mapper.dtoToCreator(creator)));
    }

    @PutMapping("/admin/creators/{id}")
    CreatorDto edit(@RequestBody @Valid CreatorDto creator,
                    @PathVariable Long id) {
        logger.trace("edit(creator, id) called");
        return mapper.creatorToDto(service.edit(mapper.dtoToCreator(creator), id));
    }

    @DeleteMapping("/admin/creators/{id}")
    void deleteById(@PathVariable Long id) {
        logger.trace("deleteById(id) called");
        service.remove(id);
    }

    @DeleteMapping("admin/creators/{creatorId}/contact/{contactId}")
    void deleteContact(@PathVariable Long creatorId,
                       @PathVariable Long contactId) {
        logger.trace("deleteContact(creatorId, contactId) called");
        service.removeContact(creatorId, contactId);
    }
}
