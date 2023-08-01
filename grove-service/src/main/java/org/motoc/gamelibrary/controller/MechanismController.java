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
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MechanismController {

    private static final Logger logger = LoggerFactory.getLogger(MechanismController.class);

    private final MechanismService service;

    @Autowired
    public MechanismController(MechanismService service) {
        this.service = service;
    }

    @GetMapping("/admin/mechanisms/count")
    Long count() {
        logger.trace("count called");
        return service.count();
    }

    @GetMapping("/admin/mechanisms")
    List<MechanismDto> findAll() {
        logger.trace("findAll called");
        return service.findAll();
    }

    @GetMapping("/admin/mechanisms/{id}")
    MechanismDto findById(@PathVariable Long id) {
        logger.trace("findById(id) called");
        return service.findById(id);
    }

    @GetMapping("/admin/mechanisms/page")
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


    @PostMapping("/admin/mechanisms")
    MechanismDto save(@RequestBody @Valid MechanismNameDto mechanismNameDto) {
        logger.trace("save(mechanism) called");
        return service.save(mechanismNameDto);
    }

    @PutMapping("/admin/mechanisms/{id}")
    MechanismDto edit(@RequestBody @Valid MechanismDto mechanismDto,
                      @PathVariable Long id) {
        logger.trace("edit(mechanism, id) called");
        return service.edit(mechanismDto, id);
    }

    @DeleteMapping("/admin/mechanisms/{id}")
    void deleteById(@PathVariable Long id) {
        logger.trace("deleteById(id) called");
        service.remove(id);
    }

}
