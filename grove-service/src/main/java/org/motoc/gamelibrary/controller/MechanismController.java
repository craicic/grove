package org.motoc.gamelibrary.controller;

import org.motoc.gamelibrary.domain.dto.MechanismDto;
import org.motoc.gamelibrary.domain.dto.MechanismNameDto;
import org.motoc.gamelibrary.service.MechanismService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Defines mechanism endpoints
 */
@CrossOrigin
@RestController
@RequestMapping("api/admin/mechanisms")
public class MechanismController {

    private static final Logger logger = LoggerFactory.getLogger(MechanismController.class);

    private final MechanismService service;

    @Autowired
    public MechanismController(MechanismService service) {
        this.service = service;
    }

    /**
     * Get the total mechanism count.
     * @return The number of mechanisms in collection.
     */
    @GetMapping("/count")
    Long count() {
        logger.trace("count called");
        return service.count();
    }

    /**
     * Get all mechanisms.
     * @return A list of mechanisms.
     */
    @GetMapping("/")
    List<MechanismDto> findAll() {
        logger.trace("findAll called");
        return service.findAll();
    }

    /**
     * Get mechanism by ID.
     * @param id The mechanism ID that need to be fetched.
     * @return The mechanism matching the ID.
     */
    @GetMapping("/{id}")
    MechanismDto findById(@PathVariable Long id) {
        logger.trace("findById(id) called");
        return service.findById(id);
    }

    /**
     * Fetch a paginated list of mechanisms, based on the pageable parameter.
     * @param pageable The pageable item to fetch a page of mechanisms.
     * @param keyword The keyword to fetch a page of mechanism.
     * @return The paginated list of mechanisms.
     */
    @GetMapping("/page")
    Page<MechanismDto> findPage(Pageable pageable,
                                @RequestParam(required = false, name = "search") String keyword) {
        if (keyword == null) {
            logger.trace("findPage(pageable) called");
            return service.findPage(pageable);
        } else {
            logger.trace("findPage(" + keyword + ", pageable) called");
            return service.quickSearch(keyword, pageable);
        }
    }

    /**
     * Save a new mechanism.
     * @param mechanismNameDto The mechanism to save.
     * @return The save mechanism.
     */
    @PostMapping("/")
    MechanismDto save(@RequestBody @Valid MechanismNameDto mechanismNameDto) {
        logger.trace("save(mechanism) called");
        return service.save(mechanismNameDto);
    }

    /**
     * Update an existing mechanism.
     * @param mechanismDto The edited mechanism.
     * @param id The ID of the mechanism to edit.
     * @return The edited mechanism.
     */
    @PutMapping("/{id}")
    MechanismDto edit(@RequestBody @Valid MechanismDto mechanismDto,
                      @PathVariable Long id) {
        logger.trace("edit(mechanism, id) called");
        return service.edit(mechanismDto, id);
    }

    /**
     * Delete a mechanism based on the given ID.
     * @param id The ID of the mechanism to delete.
     */
    @DeleteMapping("/{id}")
    void deleteById(@PathVariable Long id) {
        logger.trace("deleteById(id) called");
        service.remove(id);
    }

}
