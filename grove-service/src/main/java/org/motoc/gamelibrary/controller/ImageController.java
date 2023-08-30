package org.motoc.gamelibrary.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.motoc.gamelibrary.domain.dto.ImageDto;
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

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Images endpoints
 */
@CrossOrigin
@RestController
@SecurityRequirement(name="basicAuth")
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    private final ImageService service;


    @Autowired
    public ImageController(ImageService service) {
        this.service = service;
    }

    /**
     * Store an image and attach it to the game of ID given in param
     * @param file The image to store
     * @param gameId The game to link
     * @return The ID of the stored image
     * @throws IOException If something went wrong during IO operation
     */
    @PostMapping("/admin/images/games/{gameId}")
    Long save(@RequestParam(name = "file") MultipartFile file,
              @PathVariable Long gameId) throws IOException {
        logger.trace("save(image) called");
        return service.saveThenAttachToGame(file.getInputStream(), gameId);
    }

    /**
     * Get an item containing an image and its ID given an ID
     * @param id The ID of the image wanted to fetch
     * @return An object containing the image and its ID
     */
    @GetMapping("/admin/images/{id}")
    @ResponseBody
    ImageDto getImage(@PathVariable Long id) {
        logger.trace("findDataById(id) called");
        ImageDto dto = new ImageDto();
        dto.setId(id);
        dto.setContent(service.retrieveBytes(id));
        return dto;
    }

    /**
     * Get an image content given its ID
     * @param id The ID of the image wanted to fetch
     * @return An image
     */
    @GetMapping(value = "/admin/images/{id}/content")
    @ResponseBody
    ResponseEntity<InputStreamResource> getContent(@PathVariable Long id) {
        logger.trace("findDataById(id) called");
        InputStreamResource isr = new InputStreamResource(new ByteArrayInputStream(service.retrieveBytes(id)));
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.IMAGE_PNG);
        return ResponseEntity.ok().headers(header).body(isr);
    }


}
