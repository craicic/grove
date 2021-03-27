package org.motoc.gamelibrary.business;

import org.motoc.gamelibrary.dto.ImageDto;
import org.motoc.gamelibrary.model.Image;
import org.motoc.gamelibrary.repository.jpa.ImageRepository;
import org.motoc.gamelibrary.technical.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * Perform business logic on the entity Image, it is in charge to make file operations on Image
 *
 * @author RouzicJ
 */
@Service
@Transactional
public class ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);



    private final ImageRepository repository;

    private final GameService gameService;

    @Autowired
    public ImageService(ImageRepository repository, GameService gameService) {
        this.repository = repository;
        this.gameService = gameService;
    }

    /**
     * Save the image and attach it to a game
     */
    public Long saveThenAttachToGame(MultipartFile image, Long gameId) throws IOException {
        Long imageId = persistImageInDatabase(image);
        gameService.addImage(gameId, imageId);
        return imageId;
    }

    /**
     * Given a path, return the image bytes
     */
    public ImageDto retrieveDataUri(Long id) {
        Image image = repository.findById(id).orElseThrow(() -> {
            logger.debug("No image path of id={} found.", id);
            throw new NotFoundException("No image path of id=" + id + " found.");
        });

        ImageDto dto = new ImageDto();
        String encodedString = Base64.getEncoder().encodeToString(image.getData());
        dto.setData(encodedString);
        return dto;
    }

    /**
     * Given a MultipartFile, store it in database
     */
    private Long persistImageInDatabase(MultipartFile multipartImage) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Image image = new Image();
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(multipartImage.getInputStream());
            ImageIO.write(bufferedImage, "png", outputStream);
        } catch (IOException ex) {
            logger.warn("An error occurred with message : " + ex.getMessage());
            throw new IOException(ex.getMessage());
        }

        image.setData(outputStream.toByteArray());
        return repository.save(image).getId();
    }
}
