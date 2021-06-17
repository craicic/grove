package org.motoc.gamelibrary.controller;

import org.motoc.gamelibrary.business.CategoryService;
import org.motoc.gamelibrary.dto.CategoryDto;
import org.motoc.gamelibrary.mapper.CategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Defines category endpoints
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(ProductLineController.class);

    private final CategoryService service;

    private final CategoryMapper mapper;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
        this.mapper = CategoryMapper.INSTANCE;
    }


    @GetMapping("/admin/categories/count")
    Long count() {
        logger.trace("count called");
        return service.count();
    }

    @GetMapping("/admin/categories/names")
    List<String> findNames() {
        logger.trace("findNames called");
        return service.findNames();
    }

    @GetMapping("/admin/categories")
    List<CategoryDto> findAll() {
        logger.trace("findAll() called");
        return mapper.categoriesToDto(service.findAll());
    }

    @GetMapping("/admin/categories/{id}")
    CategoryDto findById(@PathVariable Long id) {
        logger.trace("findById(id) called");
        return mapper.categoryToDto(service.findById(id));
    }

    @GetMapping("/admin/categories/page")
    Page<CategoryDto> findPage(Pageable pageable) {
        logger.trace("findPage(pageable) called");
        return mapper.pageToPageDto(service.findPage(pageable));
    }

    @PostMapping("/admin/categories")
    CategoryDto save(@RequestBody @Valid CategoryDto category) {
        logger.trace("save(category) called");
        return mapper.categoryToDto(
                service.save(mapper.dtoToCategory(category))
        );
    }

    @PutMapping("/admin/categories/{id}")
    CategoryDto edit(@RequestBody @Valid CategoryDto category,
                     @PathVariable Long id) {
        logger.trace("edit(category, id) called");
        return mapper.categoryToDto(service.edit(
                mapper.dtoToCategory(category), id)
        );
    }

    @DeleteMapping("/admin/categories/{id}")
    void deleteById(@PathVariable Long id) {
        logger.trace("deleteById(id) called");
        service.remove(id);
    }
}
