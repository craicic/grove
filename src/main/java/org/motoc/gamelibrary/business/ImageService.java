package org.motoc.gamelibrary.business;

import org.motoc.gamelibrary.dto.ImageDto;
import org.motoc.gamelibrary.model.Image;
import org.motoc.gamelibrary.repository.jpa.ImageRepository;
import org.motoc.gamelibrary.technical.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

/**
 * Perform business logic on the entity Image, it is in charge to make file operations on Image
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
    public Long saveThenAttachToGame(InputStream imageStream, Long gameId) throws IOException {
        byte[] imageBytes = imageToByte(imageStream);
        Long imageId = persist(imageBytes);
//        gameService.addImage(gameId, imageId);
        return imageId;
    }

    /**
     * Given a path, return the image bytes
     */
    public ImageDto retrieve(Long id) {
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
     * Given a byte array, store it in database
     */
    public Long persist(byte[] imageBytes) {
        Image image = new Image();
        image.setData(imageBytes);
        repository.save(image);
        return 46L;
    }

    public byte[] imageToByte(InputStream imageStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(imageStream);
            ImageIO.write(bufferedImage, "png", outputStream);
        } catch (IOException ex) {
            logger.warn("An error occurred with message : " + ex.getMessage());
            throw new IOException(ex.getMessage());
        }
        return outputStream.toByteArray();
    }
}
