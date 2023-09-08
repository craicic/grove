package org.motoc.gamelibrary.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.motoc.gamelibrary.domain.dto.PublisherDto;
import org.motoc.gamelibrary.domain.dto.PublisherNameDto;
import org.motoc.gamelibrary.domain.dto.PublisherNoIdDto;
import org.motoc.gamelibrary.service.PublisherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Defines publisher endpoints
 */
@CrossOrigin
@RestController
@RequestMapping("api/admin/publishers")
@SecurityRequirement(name="jwtAuth")
public class PublisherController {

    private static final Logger logger = LoggerFactory.getLogger(PublisherController.class);

    private final PublisherService service;

    public PublisherController(PublisherService service) {
        this.service = service;
    }

    /**
     * Get the total publisher count.
     * @return The number of publisher in collection.
     */
    @GetMapping("/count")
    Long count() {
        logger.trace("count() called");
        return service.count();
    }

    /**
     * Get all names of publishers.
     * @return A list of publishers.
     */
    @GetMapping("/names")
    List<PublisherNameDto> findNames() {
        logger.trace("findNames called");
        return service.findNames();
    }

    /**
     * Get publisher based on the given ID.
     * @param id The ID of the publisher to find.
     * @return The publisher matching the ID.
     */
    @GetMapping("/{id}")
    PublisherDto findById(@PathVariable Long id) {
        logger.trace("findById(id) called");
        return service.findById(id);
    }

    /**
     * Fetch the paginated list of publishers, based on the pageable parameters.
     * @param pageable The pageable item to fetch a page of publishers.
     * @param keyword The keyword to fetch a page of publishers.
     * @return The paginated list of publishers.
     */
    @GetMapping("/page")
    Page<PublisherDto> findPage(Pageable pageable,
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
     * Save a new publisher.
     * @param dto The publisher to save.
     * @return The saved publisher.
     */
    @PostMapping("/")
    PublisherDto save(@RequestBody @Valid PublisherNoIdDto dto) {
        logger.trace("save(publisher) called");
        return service.save(dto);
    }

    /**
     * Update an existing publisher.
     * @param publisherDto The edited publisher.
     * @param id The ID of the publisher to edit.
     * @return The edited publisher.
     */
    @PutMapping("/{id}")
    PublisherDto edit(@RequestBody @Valid PublisherDto publisherDto,
                      @PathVariable Long id) {
        logger.trace("edit(publisher, id) called");
        return service.edit(publisherDto, id);
    }

    /**
     * Delete a publisher based on the given ID.
     * @param id The ID of the publisher to delete.
     */
    @DeleteMapping("/{id}")
    void deleteById(@PathVariable Long id) {
        logger.trace("deleteById(id) called");
        service.remove(id);
    }

    /**
     * Remove publisher contact.
     * @param publisherId The publisherID of the publisher to delete.
     */
    @DeleteMapping("/{publisherId}/contact/")
    void deleteContact(@PathVariable Long publisherId) {
        logger.trace("deleteContact(publisherId, contactId) called");
        service.removeContact(publisherId);
    }
}
