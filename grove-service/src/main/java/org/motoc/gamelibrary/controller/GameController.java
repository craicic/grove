package org.motoc.gamelibrary.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.motoc.gamelibrary.domain.dto.GameDto;
import org.motoc.gamelibrary.domain.dto.GameOverviewDto;
import org.motoc.gamelibrary.domain.dto.ImageDto;
import org.motoc.gamelibrary.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Defines game endpoints
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("admin/games")
public class GameController {

    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    private final GameService service;

    @Autowired
    public GameController(GameService service) {
        this.service = service;
    }

    /**
     * Get the total game count.
     * @return Return the number of games in collection.
     */
    @GetMapping("/count")
     Long count() {
        logger.trace("count called");
        return service.count();
    }

    /**
     * Get game by ID
     * @param id The game ID that need to be fetched
     * @return The game matching the ID
     */
    @GetMapping("/{id}")
    GameDto findById(@PathVariable Long id) {
        logger.trace("findById(id) called");
        return service.findGameById(id);
    }

    /**
     * Fetch a paginated list of games, based on the pageable parameter.
     * @param pageable The pageable item to fetch a page of games.
     * @return The paginated list of games.
     */
    @GetMapping("/page")
    Page<GameDto> findPage(Pageable pageable) {
        logger.trace("findPage(pageable) called");
        return service.findPage(pageable);
    }

    /**
     * Update an existing game.
     * @apiNote TEST
     * @param gameDto The edited game
     * @param id The ID of the game to edit
     * @return The edited game
     */
    @PutMapping("/{id}")
    GameDto edit(@RequestBody @Valid GameDto gameDto,
                 @PathVariable Long id) {
        logger.trace("edit(game) called");
        return service.edit(gameDto, id);
    }

    /**
     * Search for a set of Game, paginated. Based on an optional keyword
     * @param pageable The pageable item to fetch a page of games
     * @param keyword The optional keyword to filter games
     * @return The paginated and filtered list of games.
     */
    @GetMapping("/page/overview")
    Page<GameOverviewDto> findPagedOverview(Pageable pageable,
                                            @RequestParam(name = "search", required = false, defaultValue = "")
                                            String keyword) {
        logger.trace("findPagedOverview(pageable) called");
        return service.findPagedOverview(pageable, keyword);

    }

    /**
     * Save a new game
     * @param gameDto The game to save
     * @return The saved game
     */
    @PostMapping("/")
    GameDto save(@RequestBody @Valid GameDto gameDto) {
        logger.trace("save(game) called");
        return service.save(gameDto);
    }

    /**
     * Get the list of all games' title
     * @return A list of title
     */
    @GetMapping("/titles")
    List<String> findTitles() {
        logger.trace("findTitles() called");
        return service.findTitles();
    }

    /**
     * Get all PNG or JPEG images linked to the game that match the given ID
     * @param id The ID of the game the images are needed
     * @return A list of images in PNG or JPEG format
     */
    @GetMapping("/{id}/images")
    List<ImageDto> findImagesById(@PathVariable Long id) {
        logger.trace("findImagesById() called");
        return service.findImagesById(id);
    }

    /**
     * Attach a category to a game. Base on IDs
     * @param gameId The ID of the game you want to link the given category
     * @param categoryId The ID of the category you want to attach to the game
     * @return The updated game of the given ID.
     */
    @PutMapping("/{gameId}/add-category/{categoryId}")
    GameDto addCategory(@PathVariable Long gameId,
                        @PathVariable Long categoryId) {
        logger.trace("addCategory() called");
        return service.addCategory(gameId, categoryId);
    }

    /**
     * Detach a category from a game. Base on IDs
     * @param gameId The ID of the game you want to unlink the given category
     * @param categoryId The ID of the category you want to detach from the game
     * @return The updated game of the given ID.
     */
    @PutMapping("/{gameId}/unlink-category/{categoryId}")
    GameDto unlinkCategory(@PathVariable Long gameId,
                           @PathVariable Long categoryId) {
        logger.trace("unlinkCategory() called");
        return service.removeCategory(gameId, categoryId);

    }

    /**
     * Attach a mechanism to a game. Base on IDs
     * @param gameId The ID of the game you want to link the given mechanism
     * @param mechanismId The ID of the mechanism you want to attach to the game
     * @return The updated game of the given ID.
     */
    @PutMapping("/{gameId}/add-mechanism/{mechanismId}")
    GameDto addMechanism(@PathVariable Long gameId,
                         @PathVariable Long mechanismId) {
        logger.trace("addMechanism() called");
        return service.addMechanism(gameId, mechanismId);
    }

    /**
     * Detach a mechanism from a game. Base on IDs
     * @param gameId The ID of the game you want to unlink the given mechanism
     * @param mechanismId The ID of the mechanism you want to detach from the game
     * @return The updated game of the given ID.
     */
    @PutMapping("/{gameId}/unlink-mechanism/{mechanismId}")
    GameDto unlinkMechanism(@PathVariable Long gameId,
                            @PathVariable Long mechanismId) {
        logger.trace("unlinkMechanism() called");
        return service.removeMechanism(gameId, mechanismId);
    }

    /**
     * Delete a game based on the given ID
     * @param gameId The ID of the game to delete
     */
    @DeleteMapping("/{gameId}")
    void deleteById(@PathVariable Long gameId) {
        logger.trace("deleteById() called");
        service.deleteById(gameId);
    }

    /**
     * Attach a game copy to a game. Base on IDs
     * @param gameId The ID of the game you want to link the given game copy
     * @param gameCopyId The ID of the game copy you want to attach to the game
     * @return The updated game of the given ID.
     */
    @PutMapping("/{gameId}/add-game-copy/{gameCopyId}")
    GameDto addGameCopy(@PathVariable Long gameId,
                        @PathVariable Long gameCopyId) {
        logger.trace("addGameCopy() called");
        return service.addGameCopy(gameId, gameCopyId);
    }

    /**
     * Detach a game copy from a game. Base on IDs
     * @param gameId The ID of the game you want to unlink the given game copy
     * @param gameCopyId The ID of the game copy you want to detach from the game
     */
    @PutMapping("/{gameId}/unlink-game-copy/{gameCopyId}")
    void unlinkGameCopy(@PathVariable Long gameId,
                        @PathVariable Long gameCopyId) {
        logger.trace("unlinkGameCopy() called");
        service.removeGameCopy(gameId, gameCopyId);
    }

    /**
     * Attach a creator to a game. Base on IDs
     * @param gameId The ID of the game you want to link the given creator
     * @param creatorId The ID of the creator you want to attach to the game
     * @return The updated game of the given ID.
     */
    @PutMapping("/{gameId}/add-creator/{creatorId}")
    GameDto addCreator(@PathVariable Long gameId,
                       @PathVariable Long creatorId) {
        logger.trace("addCreator() called");
        return service.addCreator(gameId, creatorId);
    }

    /**
     * Detach a creator from a game. Base on IDs
     * @param gameId The ID of the game you want to unlink the given creator
     * @param creatorId The ID of the creator you want to detach from the game
     * @return The updated game of the given ID.
     */
    @PutMapping("/{gameId}/unlink-creator/{creatorId}")
    GameDto unlinkCreator(@PathVariable Long gameId,
                          @PathVariable Long creatorId) {
        logger.trace("unlinkCreator() called");
        return service.removeCreator(gameId, creatorId);
    }

    /**
     * Attach an image to a game. Base on IDs
     * @param gameId The ID of the game you want to link the given image
     * @param imageId The ID of the image you want to attach to the game
     */
    @PutMapping("/{gameId}/add-image/{imageId}")
    void addImage(@PathVariable Long gameId,
                  @PathVariable Long imageId) {
        logger.trace("addImage() called");
        service.addImage(gameId, imageId);
    }
}
