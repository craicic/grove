package org.motoc.gamelibrary.controller;

import org.motoc.gamelibrary.business.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Images endpoints
 *
 * @author RouzicJ
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/admin/images")
    Long save(@RequestParam(name = "file") MultipartFile file) throws IOException {
        logger.trace("save(image) called");
        return imageService.save(file);
    }

    // TODO remove
    @GetMapping("/admin/images/io")
    Long throwIO() throws IOException {
        logger.trace("save(image) called");
        throw new IOException("test");
    }
}
