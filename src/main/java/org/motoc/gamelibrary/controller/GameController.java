package org.motoc.gamelibrary.controller;

import org.motoc.gamelibrary.business.GameService;
import org.motoc.gamelibrary.dto.GameDto;
import org.motoc.gamelibrary.mapper.GameMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Defines game endpoints
 *
 * @author RouzicJ
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class GameController {

    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    private final GameService service;

    private final GameMapper mapper;

    @Autowired
    public GameController(GameService service) {
        this.service = service;
        this.mapper = GameMapper.INSTANCE;
    }

    @GetMapping("/admin/games/count")
    Long count() {
        logger.trace("count called");
        return service.count();
    }

    @GetMapping("/admin/games/{id}")
    GameDto findById(@PathVariable Long id) {
        logger.trace("findById(id) called");
        return mapper.gameToDto(service.findById(id));
    }

    @GetMapping("/admin/games/page")
    Page<GameDto> findPage(Pageable pageable) {
        logger.trace("findPage(pageable) called");
        return mapper.pageToPageDto(service.findPage(pageable));
    }

    @PostMapping("/admin/games")
    GameDto save(@RequestBody @Valid GameDto gameDto) {
        logger.trace("save(games) called");
        return mapper.gameToDto(service.save(mapper.dtoToGame(gameDto)));
    }
}
