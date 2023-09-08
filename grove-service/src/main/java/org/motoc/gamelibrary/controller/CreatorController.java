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

import jakarta.validation.Valid;
import java.util.List;

/**
 * Defines creator's endpoints
 */
@CrossOrigin
@RestController
@RequestMapping("api/admin/creators")
public class CreatorController {

    private static final Logger logger = LoggerFactory.getLogger(CreatorController.class);

    private final CreatorService service;


    @Autowired
    public CreatorController(CreatorService service) {
        this.service = service;
    }

    /**
     * Get the total creators count.
     * @return The number of creators in collection.
     */
    @GetMapping("/count")
    Long count() {
        logger.trace("count called");
        return service.count();
    }

    /**
     * Get all names of creators
     * @return A list of creators.
     */
    @GetMapping("/names")
    List<CreatorNameDto> findNames() {
        logger.trace("findNames called");
        return service.findNames();
    }

    /**
     * Get creators based on the given fullName.
     * @param name The fullName of creators.
     * @return The creator find with fullName
     */
    @GetMapping("/")
    CreatorWithoutContactDto findByName(@RequestParam(name = "full-name") String name) {
        logger.trace("findByName(name) called");
        return service.findByFullName(name);
    }

    /**
     * Get a creator based on the given ID.
     * @param id The ID of the creator to find.
     * @return The creator matching the ID.
     */
    @GetMapping("/{id}")
    CreatorDto findById(@PathVariable Long id) {
        logger.trace("findById(id) called");
        return service.findById(id);
    }

    /**
     * Fetch the paginated list of creators, based on the pageable parameters.
     * @param pageable The pageable item to fetch a page of creators.
     * @param keyword The keyword to fetch a page of creators.
     * @return The paginated list of creators.
     */
    @GetMapping("/page")
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
     * Update an existing creator.
     * @param creator The edited creator.
     * @param id The ID of the creator to edit.
     * @return The edited creator.
     */
    @PutMapping("/{id}")
    CreatorDto edit(@RequestBody @Valid CreatorDto creator,
                    @PathVariable Long id) {
        logger.trace("edit(creator), id) called");
        return service.edit(creator, id);
    }

    /**
     * Delete a creator based on the given ID.
     * @param id The ID of the creator to delete.
     */
    @DeleteMapping("/{id}")
    void deleteById(@PathVariable Long id) {
        logger.trace("deleteById(id) called");
        service.remove(id);
    }

    /**
     * Remove creator's contact.
     * @param creatorId The creatorId of the creator to delete.
     */
    @PutMapping("/{creatorId}")
    void removeContact(@PathVariable Long creatorId) {
        logger.trace("deleteContact(creatorId, contactId) called");
        service.removeContact(creatorId);
    }
}
