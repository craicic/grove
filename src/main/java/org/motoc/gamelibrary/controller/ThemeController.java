package org.motoc.gamelibrary.controller;

import org.motoc.gamelibrary.business.ThemeService;
import org.motoc.gamelibrary.dto.ThemeDto;
import org.motoc.gamelibrary.model.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ThemeController {

    private final ThemeService service;

    @Autowired
    public ThemeController(ThemeService service) {
        this.service = service;
    }

    @GetMapping("/themes/count")
    public Long count() {
        return service.count(Theme.class);
    }

    @GetMapping("/themes")
    public ThemeDto findById(@RequestParam(value="id") long id) {
        return null;
    }

    @GetMapping("/themes/page")
    public Page<Theme> findPage(Pageable pageable) {
        return null;
    }
}
