package org.motoc.gamelibrary.controller;

import org.motoc.gamelibrary.business.ThemeService;
import org.motoc.gamelibrary.dto.ThemeDto;
import org.motoc.gamelibrary.mapper.ThemeMapper;
import org.motoc.gamelibrary.model.Theme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/themes/count")
    public Long count() {
        return service.count(Theme.class);
    }

    @GetMapping("/themes")
    public ThemeDto findById(@RequestParam(value="id") long id) {
        return mapper.themeToThemeDto(service.findById(id, Theme.class));
    }

    @GetMapping("/themes/page")
    public Page<ThemeDto> findPage(Pageable pageable) {
        return mapper.themePageToThemePageDto(service.findPage(pageable));
    }

    @PostMapping("/themes")
    public ThemeDto persist(@RequestBody Theme theme) {
        logger.debug("Persist method called");
        return mapper.themeToThemeDto(service.persist(theme));
    }
}
