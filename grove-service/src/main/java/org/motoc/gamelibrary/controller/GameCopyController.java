package org.motoc.gamelibrary.controller;

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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class GameCopyController {

    private static final Logger logger = LoggerFactory.getLogger(GameCopyController.class);


    private final GameCopyService service;


    @Autowired
    public GameCopyController(GameCopyService service) {
        this.service = service;
    }

    @GetMapping("/admin/game-copies/count")
    Long count() {
        logger.trace("count called");
        return service.count();
    }

    @GetMapping("/admin/game-copies/{id}")
    GameCopyDto findById(@PathVariable Long id) {
        logger.trace("findById(id) called");
        return service.findById(id);
    }

    @GetMapping("/admin/game-copies/object-code/{objectCode}")
    GameCopyDto findByObjectCode(@PathVariable @Pattern(regexp = "^[0-9]{1,5}$") String objectCode) {
        logger.trace("findByObjectCode(objectCode) called");
        return service.findByObjectCode(objectCode);
    }

    @PostMapping("/admin/game-copies")
    GameCopyDto save(@RequestBody @Valid GameCopyDto copyDto) {
        logger.trace("save(gameCopy) called");
        return service.save(copyDto);
    }

    @PutMapping("/admin/game-copies/{id}")
    GameCopyDto edit(@RequestBody @Valid GameCopyDto copyDto,
                     @PathVariable Long id) {
        logger.trace("edit(gameCopy) called");
        return service.edit(copyDto, id);
    }

    /* Maybe replace this endpoint by a endpoint in GAME */
    @GetMapping("/admin/game-copies")
    List<GameCopyDto> findAll(@RequestParam(value = "loan-ready", required = false, defaultValue = "false") boolean loanReady) {
        logger.trace("findAll() called");
        if (!loanReady)
            return service.findAll();
        else
            return service.findLoanReady();
    }

    /* Maybe replace this endpoint by a endpoint in GAME */
    @GetMapping("/admin/game-copies/page")
    Page<GameCopyDto> findAll(@RequestParam(value = "loan-ready", required = false, defaultValue = "false") boolean loanReady,
                              Pageable pageable) {
        logger.trace("findAll() called");
        if (!loanReady)
            return service.findPage(pageable);
        else
            return service.findLoanReadyPage(pageable);
    }
    @PostMapping("/admin/game-copies/{copyId}/add-publisher/{publisherId}")
    GameCopyDto addPublisher(@PathVariable Long copyId,
                             @PathVariable Long publisherId) {
        logger.trace("addPublisher() called");
        return service.addPublisher(copyId, publisherId);
    }

    @DeleteMapping("/admin/game-copies/{copyId}/unlink-publisher/{publisherId}")
    GameCopyDto unlinkPublisher(@PathVariable Long copyId,
                                @PathVariable Long publisherId) {
        logger.trace("unlinkPublisher() called");
        return service.removePublisher(copyId, publisherId);
    }


}
