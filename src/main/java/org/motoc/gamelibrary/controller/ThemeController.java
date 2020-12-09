package org.motoc.gamelibrary.controller;

import org.motoc.gamelibrary.business.ThemeService;
import org.motoc.gamelibrary.dto.ThemeDto;
import org.motoc.gamelibrary.mapper.ThemeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Defines theme endpoints
 *
 * @author RouzicJ
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ThemeController {

    private static final Logger logger = LoggerFactory.getLogger(ThemeController.class);

    private final ThemeService service;

    private final ThemeMapper mapper;

    @Autowired
    public ThemeController(ThemeService service) {
        this.service = service;
        this.mapper = ThemeMapper.INSTANCE;
    }

    // TODO remove
    @GetMapping(path = {"/"})
    String tempHome() {
        return "You're at index";
    }

    // TODO remove
    @GetMapping(path = {"/user"})
    String tempUser() {
        return "You're on the user page";
    }

    // TODO remove
    @GetMapping(path = {"/admin"})
    String tempAdmin() {
        return "You're on the admin page";
    }

    @GetMapping("/admin/themes/count")
    Long count() {
        logger.trace("count called");
        return service.count();
    }

    @GetMapping("/admin/themes")
    List<ThemeDto> findAll() {
        logger.trace("findAll called");
        return mapper.themesToDto(service.findAll());
    }

    @GetMapping("/admin/themes/{id}")
    ThemeDto findById(@PathVariable Long id) {
        logger.trace("findById(id) called");
        return mapper.themeToDto(service.findById(id));
    }

    @GetMapping("/admin/themes/page")
    Page<ThemeDto> findPage(Pageable pageable,
                            @RequestParam(required = false, name = "search") String keyword) {
        if (keyword == null) {
            logger.trace("findPage(pageable) called");
            return mapper.pageToPageDto(service.findPage(pageable));
        } else {
            logger.trace("findPage(" + keyword + ", pageable) called");
            return mapper.pageToPageDto(service.quickSearch(keyword, pageable));
        }
    }


    @PostMapping("/admin/themes")
    ThemeDto save(@RequestBody @Valid ThemeDto theme) {
        logger.trace("save(theme) called");
        return mapper.themeToDto(service.save(mapper.dtoToTheme(theme)));
    }

    @PutMapping("/admin/themes/{id}")
    ThemeDto edit(@RequestBody @Valid ThemeDto theme,
                  @PathVariable Long id) {
        logger.trace("edit(theme, id) called");
        return mapper.themeToDto(service.edit(mapper.dtoToTheme(theme), id));
    }

    @DeleteMapping("/admin/themes/{id}")
    void deleteById(@PathVariable Long id) {
        logger.trace("deleteById(id) called");
        service.remove(id);
    }

}
