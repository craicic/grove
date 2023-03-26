package org.motoc.gamelibrary.controller;

import org.motoc.gamelibrary.domain.dto.GameDto;
import org.motoc.gamelibrary.domain.dto.GameOverviewDto;
import org.motoc.gamelibrary.mapper.GameMapper;
import org.motoc.gamelibrary.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Defines game endpoints
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

    @PutMapping("/admin/games/{id}")
    GameDto edit(@RequestBody @Valid GameDto gameDto,
                 @PathVariable Long id) {
        logger.trace("edit(game) called\rAttached game to edit is =" + gameDto.toString());
        return mapper.gameToDto(service.edit(mapper.dtoToGame(gameDto), id));
    }


    @GetMapping("/admin/games/page/overview")
    Page<GameOverviewDto> findPagedOverview(Pageable pageable,
                                            @RequestParam(name = "search", required = false, defaultValue = "")
                                            String keyword) {
        logger.trace("findPagedOverview(pageable) called");
        return mapper.pageToOverviewDto(service.findPagedOverview(pageable, keyword));

    }

    @PostMapping("/admin/games")
    GameDto save(@RequestBody @Valid GameDto gameDto) {
        logger.trace("save(game) called");
        return mapper.gameToDto(service.save(mapper.dtoToGame(gameDto)));
    }

    @GetMapping("/admin/games/names")
    List<String> findNames() {
        logger.trace("findNames() called");
        return service.findNames();
    }
    @PostMapping("/admin/games/{gameId}/add-category/{categoryId}")
    GameDto addCategory(@PathVariable Long gameId,
                        @PathVariable Long categoryId) {
        logger.trace("addCategory() called");
        return mapper.gameToDto(service.addCategory(gameId, categoryId));
    }

    @DeleteMapping("/admin/games/{gameId}/unlink-category/{categoryId}")
    GameDto unlinkCategory(@PathVariable Long gameId,
                           @PathVariable Long categoryId) {
        logger.trace("unlinkCategory() called");
        return mapper.gameToDto(service.removeCategory(gameId, categoryId));

    }

    @PostMapping("/admin/games/{gameId}/add-mechanism/{mechanismId}")
    GameDto addMechanism(@PathVariable Long gameId,
                         @PathVariable Long mechanismId) {
        logger.trace("addMechanism() called");
        return mapper.gameToDto(service.addMechanism(gameId, mechanismId));
    }

    @DeleteMapping("/admin/games/{gameId}/unlink-mechanism/{mechanismId}")
    GameDto unlinkMechanism(@PathVariable Long gameId,
                            @PathVariable Long mechanismId) {
        logger.trace("unlinkMechanism() called");
        return mapper.gameToDto(service.removeMechanism(gameId, mechanismId));
    }

    @PostMapping("/admin/games/{gameId}/add-game-copy/{gameCopyId}")
    GameDto addGameCopy(@PathVariable Long gameId,
                        @PathVariable Long gameCopyId) {
        logger.trace("addGameCopy() called");
        return mapper.gameToDto(service.addGameCopy(gameId, gameCopyId));
    }

    @DeleteMapping("/admin/games/{gameId}/unlink-game-copy/{gameCopyId}")
    void unlinkGameCopy(@PathVariable Long gameId,
                        @PathVariable Long gameCopyId) {
        logger.trace("unlinkGameCopy() called");
        service.removeGameCopy(gameId, gameCopyId);
    }

    @PostMapping("/admin/games/{gameId}/add-creator/{creatorId}")
    GameDto addCreator(@PathVariable Long gameId,
                       @PathVariable Long creatorId) {
        logger.trace("addCreator() called");
        return mapper.gameToDto(service.addCreator(gameId, creatorId));
    }

    @DeleteMapping("/admin/games/{gameId}/unlink-creator/{creatorId}")
    GameDto unlinkCreator(@PathVariable Long gameId,
                          @PathVariable Long creatorId) {
        logger.trace("unlinkCreator() called");
        return mapper.gameToDto(service.removeCreator(gameId, creatorId));
    }
}
