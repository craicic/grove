package org.motoc.gamelibrary.controller;

import org.motoc.gamelibrary.business.GameService;
import org.motoc.gamelibrary.mapper.GameMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

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
}
