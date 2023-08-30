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
public class CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService service;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }


    @GetMapping("/admin/categories/count")
    Long count() {
        logger.trace("count called");
        return service.count();
    }

    @GetMapping("/admin/categories/titles")
    List<String> findTitles() {
        logger.trace("findTitles called");
        return service.findTitles();
    }

    @GetMapping("/admin/categories")
    List<CategoryDto> findAll() {
        logger.trace("findAll() called");
        return service.findAll();
    }

    @GetMapping("/admin/categories/{id}")
    CategoryDto findById(@PathVariable Long id) {
        logger.trace("findById(id) called");
        return service.findById(id);
    }

    @GetMapping("/admin/categories/page")
    Page<CategoryDto> findPage(Pageable pageable) {
        logger.trace("findPage(pageable) called");
        return service.findPage(pageable);
    }

    @PostMapping("/admin/categories")
    CategoryDto save(@RequestBody @Valid CategoryDto category) {
        logger.trace("save(category) called");
        return service.save(category);
    }

    @PutMapping("/admin/categories/{id}")
    CategoryDto edit(@RequestBody @Valid CategoryDto category,
                     @PathVariable Long id) {
        logger.trace("edit(category, id) called");
        return service.edit(category, id);
    }

    @DeleteMapping("/admin/categories/{id}")
    void deleteById(@PathVariable Long id) {
        logger.trace("deleteById(id) called");
        service.remove(id);
    }
}
