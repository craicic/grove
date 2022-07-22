package org.motoc.gamelibrary.business;

import org.motoc.gamelibrary.repository.jpa.ImageRepository;
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

/**
 * Perform business logic on the entity Image, it is in charge to make file operations on Image
 */
@Service
@Transactional
public class ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);



    private final ImageRepository repository;


    @Autowired
    public ImageService(ImageRepository repository) {
        this.repository = repository;
    }

    /**
     * Save the image and attach it to a game
     */
    public Long saveThenAttachToGame(InputStream imageStream, Long gameId) throws IOException {
        byte[] bytes = imageToByte(imageStream);
        return repository.persistLob(bytes, gameId);
    }

    /**
     * Given an image Id, return the image bytes
     */
    public InputStream retrieveBytes(Long id) {
        return repository.findBlob(id);
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
