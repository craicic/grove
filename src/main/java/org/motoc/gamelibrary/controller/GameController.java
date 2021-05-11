package org.motoc.gamelibrary.controller;

import org.motoc.gamelibrary.business.GameService;
import org.motoc.gamelibrary.dto.GameDto;
import org.motoc.gamelibrary.dto.GameOverviewDto;
import org.motoc.gamelibrary.mapper.GameMapper;
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
                                            @RequestParam(name = "search", required = false) String keyword) {
        logger.trace("findPagedOverview(pageable) called");
        if (keyword != null)
            return mapper.pageToOverviewDto(service.findPagedOverview(pageable, keyword));
        else
            return mapper.pageToOverviewDto(service.findPagedOverview(pageable));
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

    @PostMapping("/admin/games/{gameId}/add-expansions/{expansionId}")
    GameDto addExpansion(@PathVariable Long gameId,
                         @PathVariable Long expansionId) {
        logger.trace("addExpansion() called");
        return mapper.gameToDto(service.addExpansion(gameId, expansionId));
    }

    @PostMapping("/admin/games/{gameId}/add-expansions")
    GameDto addExpansions(@PathVariable Long gameId,
                          @RequestBody List<Long> expansionsIds) {
        logger.trace("addExpansions() called");
        return mapper.gameToDto(service.addExpansions(gameId, expansionsIds));
    }

    @PostMapping("/admin/games/{gameId}/add-core-game/{coreGameId}")
    GameDto addCoreGame(@PathVariable Long gameId,
                        @PathVariable Long coreGameId) {
        logger.trace("addCoreGame() called");
        return mapper.gameToDto(service.addCoreGame(gameId, coreGameId));
    }

    @DeleteMapping("/admin/games/{gameId}/unlink-core-link")
    void unlinkCoreGame(@PathVariable Long gameId) {
        logger.trace("unlinkCoreGame(gameId) called");
        service.removeCoreGame(gameId);
    }

    @DeleteMapping("/admin/games/{gameId}/unlink-expansion/{expansionId}")
    void unlinkExpansion(@PathVariable Long gameId,
                         @PathVariable Long expansionId) {
        logger.trace("unlinkExpansion(gameId, expansionId) called");
        service.removeExpansion(gameId, expansionId);
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

    @PostMapping("/admin/games/{gameId}/add-theme/{themeId}")
    GameDto addTheme(@PathVariable Long gameId,
                     @PathVariable Long themeId) {
        logger.trace("addTheme() called");
        return mapper.gameToDto(service.addTheme(gameId, themeId));
    }

    @DeleteMapping("/admin/games/{gameId}/unlink-theme/{themeId}")
    void unlinkTheme(@PathVariable Long gameId,
                     @PathVariable Long themeId) {
        logger.trace("unlinkTheme() called");
        service.removeTheme(gameId, themeId);
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
    void unlinkCreator(@PathVariable Long gameId,
                       @PathVariable Long creatorId) {
        logger.trace("unlinkCreator() called");
        service.removeCreator(gameId, creatorId);
    }

    @PostMapping("/admin/games/{gameId}/add-publisher/{publisherId}")
    GameDto addPublisher(@PathVariable Long gameId,
                         @PathVariable Long publisherId) {
        logger.trace("addPublisher() called");
        return mapper.gameToDto(service.addPublisher(gameId, publisherId));
    }

    @DeleteMapping("/admin/games/{gameId}/unlink-publisher/{publisherId}")
    void unlinkPublisher(@PathVariable Long gameId,
                         @PathVariable Long publisherId) {
        logger.trace("unlinkPublisher() called");
        service.removePublisher(gameId, publisherId);
    }
}
