package org.motoc.gamelibrary.controller;

import org.motoc.gamelibrary.domain.dto.CreatorDto;
import org.motoc.gamelibrary.domain.dto.CreatorNameDto;
import org.motoc.gamelibrary.domain.dto.CreatorWithoutContactDto;
import org.motoc.gamelibrary.service.CreatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Defines creator's endpoints
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CreatorController {

    private static final Logger logger = LoggerFactory.getLogger(CreatorController.class);

    private final CreatorService service;


    @Autowired
    public CreatorController(CreatorService service) {
        this.service = service;
    }

    @GetMapping("/admin/creators/count")
    Long count() {
        logger.trace("count called");
        return service.count();
    }

    @GetMapping("/admin/creators/names")
    List<CreatorNameDto> findNames() {
        logger.trace("findNames called");
        return service.findNames();
    }

    @GetMapping("/admin/creators")
    CreatorWithoutContactDto findByName(@RequestParam(name = "full-name") String name) {
        logger.trace("findByName(name) called");
        return service.findByFullName(name);
    }

    @GetMapping("/admin/creators/{id}")
    CreatorDto findById(@PathVariable Long id) {
        logger.trace("findById(id) called");
        return service.findById(id);
    }

    @GetMapping("/admin/creators/page")
    Page<CreatorDto> findPage(Pageable pageable,
                              @RequestParam(name = "search", required = false) String keyword) {
        logger.trace("findPage(pageable) called");
        if (keyword == null) {
            return service.findPage(pageable);
        } else {
            logger.trace("findPage(" + keyword + ", pageable) called");
            return service.quickSearch(keyword, pageable);
        }
    }

    /**
     * Edit an existing creator
     */
    @PutMapping("/admin/creators/{id}")
    CreatorDto edit(@RequestBody @Valid CreatorDto creator,
                    @PathVariable Long id) {
        logger.trace("edit(creator), id) called");
        return service.edit(creator, id);
    }

    @DeleteMapping("/admin/creators/{id}")
    void deleteById(@PathVariable Long id) {
        logger.trace("deleteById(id) called");
        service.remove(id);
    }


    @PutMapping("admin/creators/{creatorId}")
    void removeContact(@PathVariable Long creatorId) {
        logger.trace("deleteContact(creatorId, contactId) called");
        service.removeContact(creatorId);
    }
}
