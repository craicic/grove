package org.motoc.gamelibrary.controller;

import org.motoc.gamelibrary.business.PublisherService;
import org.motoc.gamelibrary.mapper.PublisherMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class PublisherController {

    private static final Logger logger = LoggerFactory.getLogger(PublisherController.class);

    private final PublisherService service;

    private final PublisherMapper mapper;

    public PublisherController(PublisherService service) {
        this.service = service;
        this.mapper = PublisherMapper.INSTANCE;
    }
}
