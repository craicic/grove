package org.motoc.gamelibrary.controller;

import org.motoc.gamelibrary.mapper.ImageMapper;
import org.motoc.gamelibrary.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * Images endpoints
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    private final ImageService service;

    private final ImageMapper mapper;

    @Autowired
    public ImageController(ImageService service) {
        this.service = service;
        this.mapper = ImageMapper.INSTANCE;
    }

    @PostMapping("/admin/images/games/{gameId}")
    Long save(@RequestParam(name = "file") MultipartFile file,
              @PathVariable Long gameId) throws IOException {
        logger.trace("save(image) called");
        return service.saveThenAttachToGame(file.getInputStream(), gameId);
    }

    @GetMapping("/admin/images/{id}")
    @ResponseBody
    ResponseEntity<InputStreamResource> getContent(@PathVariable Long id) throws IOException {
        logger.trace("findDataById(id) called");
        InputStream is = service.retrieveBytes(id);
        InputStreamResource isr = new InputStreamResource(is);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.IMAGE_PNG);
        return ResponseEntity.ok().headers(header).body(isr);

    }
}
