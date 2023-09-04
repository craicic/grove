package org.motoc.gamelibrary.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.motoc.gamelibrary.domain.dto.GameCopyDto;
import org.motoc.gamelibrary.service.GameCopyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import java.util.List;

/**
 * Defines game copy endpoints
 */
@CrossOrigin
@RestController
@RequestMapping("api")
@SecurityRequirement(name="jwtAuth")
public class GameCopyController {

    private static final Logger logger = LoggerFactory.getLogger(GameCopyController.class);


    private final GameCopyService service;


    @Autowired
    public GameCopyController(GameCopyService service) {
        this.service = service;
    }

    /**
     * Get the total number of copies.
     * @return Return the number of copies in collection.
     */
    @GetMapping("/admin/game-copies/count")
    Long count() {
        logger.trace("count called");
        return service.count();
    }

    /**
     * Get copy of a game by ID
     * @param id The game copy ID that need to be fetched
     * @return The copy matching the ID
     */
    @GetMapping("/admin/game-copies/{id}")
    GameCopyDto findById(@PathVariable Long id) {
        logger.trace("findById(id) called");
        return service.findById(id);
    }

    /**
     * Get a game copy by object code.
     * @param objectCode The objectCode that is link to a copy, object code is a unique identifier.
     * @return The copy matching the objectCode
     */
    @GetMapping("/admin/game-copies/object-code/{objectCode}")
    GameCopyDto findByObjectCode(@PathVariable @Pattern(regexp = "^[0-9]{1,5}$") String objectCode) {
        logger.trace("findByObjectCode(objectCode) called");
        return service.findByObjectCode(objectCode);
    }

    /**
     * Save a new game copy
     * @param copyDto The copy to save
     * @return The saved copy
     */
    @PostMapping("/admin/game-copies")
    GameCopyDto save(@RequestBody @Valid GameCopyDto copyDto) {
        logger.trace("save(gameCopy) called");
        return service.save(copyDto);
    }

    /**
     * Update an existing game copy.
     * @param copyDto The edited copy
     * @param id The ID of the copy to edit
     * @return The edited copy
     */
    @PutMapping("/admin/game-copies/{id}")
    GameCopyDto edit(@RequestBody @Valid GameCopyDto copyDto,
                     @PathVariable Long id) {
        logger.trace("edit(gameCopy) called");
        return service.edit(copyDto, id);
    }

    /**
     * Find a list of all copies, can be filtered by ready-for-loan boolean
     * @param loanReady An optional filter, when true, only fetch copies that can be loan
     * @return A list of game copies
     */
    @GetMapping("/admin/game-copies")
    List<GameCopyDto> findAll(@RequestParam(value = "loan-ready", required = false, defaultValue = "false") boolean loanReady) {
        logger.trace("findAll() called");
        if (!loanReady)
            return service.findAll();
        else
            return service.findLoanReady();
    }

    /**
     * Find a page of copies, can be filtered by ready-for-loan boolean
     * @param loanReady An optional filter, when true, only fetch copies that can be loan
     * @param pageable An object to configure the page (item per page, current page, etc...)
     * @return A page of game copies
     */
    @GetMapping("/admin/game-copies/page")
    Page<GameCopyDto> findAll(@RequestParam(value = "loan-ready", required = false, defaultValue = "false") boolean loanReady,
                              Pageable pageable) {
        logger.trace("findAll() called");
        if (!loanReady)
            return service.findPage(pageable);
        else
            return service.findLoanReadyPage(pageable);
    }

    /**
     * Attach a publisher to a copy. Base on their IDs
     * @param copyId The ID of the copy you want to link the given publisher
     * @param publisherId The ID of the publisher you want to attach to the copy
     * @return The updated copy of the given ID.
     */
    @PostMapping("/admin/game-copies/{copyId}/add-publisher/{publisherId}")
    GameCopyDto addPublisher(@PathVariable Long copyId,
                             @PathVariable Long publisherId) {
        logger.trace("addPublisher() called");
        return service.addPublisher(copyId, publisherId);
    }

    /**
     * Detach a publisher from a copy. Base on their IDs
     * @param copyId The ID of the copy you want to unlink the given publisher
     * @param publisherId The ID of the publisher you want to detach from the copy
     * @return The updated copy of the given ID.
     */
    @DeleteMapping("/admin/game-copies/{copyId}/unlink-publisher/{publisherId}")
    GameCopyDto unlinkPublisher(@PathVariable Long copyId,
                                @PathVariable Long publisherId) {
        logger.trace("unlinkPublisher() called");
        return service.removePublisher(copyId, publisherId);
    }


}
