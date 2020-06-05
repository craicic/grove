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
 *
 * @author RouzicJ
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

    @GetMapping("/admin/categories")
    CategoryDto findById(Long id) {
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

    @PutMapping("/admin/categories/{catId}/add-children")
    CategoryDto addChildren(@RequestBody List<Long> childrenIds,
                            @PathVariable Long catId) {
        logger.trace("addChildren(children, catId) called");
        return mapper.categoryToDto(
                service.addChildren(childrenIds, catId)
        );
    }

    @PutMapping("/admin/categories/{catId}/parent")
    CategoryDto addParent(@RequestBody @Valid CategoryDto parent,
                          @PathVariable Long catId) {
        logger.trace("addParent(parent, catId) called");
        return mapper.categoryToDto(service.addParent(
                mapper.dtoToCategory(parent), catId));
    }

    @DeleteMapping("/admin/categories/{catId}/parent")
    void removeParent(@PathVariable Long catId) {
        logger.trace("removeParent(catId) called");
        service.removeParent(catId);
    }

    @DeleteMapping("/admin/categories/{catId}/children/{childId}")
    void removeChild(@PathVariable Long catId, @PathVariable Long childId) {
        logger.trace("removeChildren(catId, childId) called");
        service.removeChild(catId, childId);
    }
}
