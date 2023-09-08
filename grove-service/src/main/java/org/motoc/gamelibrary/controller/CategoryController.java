package org.motoc.gamelibrary.controller;

import org.motoc.gamelibrary.domain.dto.CategoryDto;
import org.motoc.gamelibrary.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Defines category endpoints
 */
@CrossOrigin
@RestController
@RequestMapping("api/admin/categories")
public class CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService service;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    /**
     * Get the total game count.
     * @return Return the number of games in collection.
     */
    @GetMapping("/count")
    Long count() {
        logger.trace("count called");
        return service.count();
    }

    /**
     * Get categorizes titles.
     * @return A list of titles.
     */
    @GetMapping("/titles")
    List<String> findTitles() {
        logger.trace("findTitles called");
        return service.findTitles();
    }

    /**
     * Get all categories.
     * @return A list of categories.
     */
    @GetMapping("/")
    List<CategoryDto> findAll() {
        logger.trace("findAll() called");
        return service.findAll();
    }

    /**
     * Get categories by ID.
     * @param id The categories ID that need to be fetched.
     * @return The categories matching the ID.
     */
    @GetMapping("/{id}")
    CategoryDto findById(@PathVariable Long id) {
        logger.trace("findById(id) called");
        return service.findById(id);
    }

    /**
     * Fetch a paginated list of categories, based on pageable parameter.
     * @param pageable The pageable item to fetch a page of categories.
     * @return The paginated list of categories.
     */
    @GetMapping("/page")
    Page<CategoryDto> findPage(Pageable pageable) {
        logger.trace("findPage(pageable) called");
        return service.findPage(pageable);
    }

    /**
     * Save a new category.
     * @param category The category to save.
     * @return The saved category.
     */

    @PostMapping("/")
    CategoryDto save(@RequestBody @Valid CategoryDto category) {
        logger.trace("save(category) called");
        return service.save(category);
    }

    /**
     * Update an existing category
     * @param category The edited category.
     * @param id The ID of the category to edit.
     * @return The edited category.
     */

    @PutMapping("/{id}")
    CategoryDto edit(@RequestBody @Valid CategoryDto category,
                     @PathVariable Long id) {
        logger.trace("edit(category, id) called");
        return service.edit(category, id);
    }

    /**
     * Delete a category based on the given ID.
     * @param id The ID of the category to delete.
     */
    @DeleteMapping("/{id}")
    void deleteById(@PathVariable Long id) {
        logger.trace("deleteById(id) called");
        service.remove(id);
    }
}
