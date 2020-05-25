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

    @GetMapping(path = {"/", "/unsecured"})
    String home() {
        return "Welcome !!!";
    }

    @GetMapping("/user")
    String user() {
        return "User page";
    }

    @GetMapping("/admin/themes/count")
    Long count() {
        return service.count();
    }

    @GetMapping("/admin/themes")
    ThemeDto findById(@RequestParam(value="id") long id) {
        return mapper.themeToThemeDto(service.findById(id));
    }

    @GetMapping("/admin/themes/page")
    Page<ThemeDto> findPage(Pageable pageable) {
        return mapper.themePageToThemePageDto(service.findPage(pageable));
    }

    @PostMapping("/admin/admin/themes")
    ThemeDto persist(@RequestBody ThemeDto theme) {
        logger.debug("Persist(theme) called");
        return mapper.themeToThemeDto(service.save(mapper.themeDtoToTheme(theme)));
    }

    @PutMapping("/admin/themes/{id}")
    ThemeDto edit(@RequestBody ThemeDto theme,
                  @PathVariable Long id) {
        return mapper.themeToThemeDto(service.edit(mapper.themeDtoToTheme(theme), id));
    }

    @DeleteMapping("/admin/themes/{id}")
    void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @DeleteMapping("/admin/themes")
    void deleteByEntity(@RequestBody Theme theme) {

    }

}
