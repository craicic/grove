package org.motoc.gamelibrary.service;

import jakarta.transaction.Transactional;
import org.motoc.gamelibrary.domain.enumeration.ImageFormat;
import org.motoc.gamelibrary.repository.jpa.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Perform service logic on the entity Image, it is in charge to make file operations on Image
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
    public Long saveThenAttachToGame(InputStream imageStream, String contentType, Long gameId) throws IOException {
        byte[] bytes = imageToByte(imageStream);
        return repository.persistImageAndAttachToGame(bytes, gameId);
    }

    /**
     * Given an image id, return the image bytes
     */
    public byte[] retrieveBytes(Long id) {
        return repository.findBytes(id);
    }


    private byte[] imageToByte(InputStream imageStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(imageStream);
            ImageIO.write(bufferedImage, "jpg", outputStream);
        } catch (
                IOException ex) {
            logger.warn("An error occurred with message : " + ex.getMessage());
            throw new IOException(ex.getMessage());
        }

        return outputStream.toByteArray();
    }

    public void processImages(Path sourcePath, boolean convert, ImageFormat outputFormat) throws IOException {
        Map<Path, ImageFormat> imageDescMap = new HashMap<>();
        List<byte[]> bytesList = new ArrayList<>();

        Files.newDirectoryStream(sourcePath).forEach(f -> {
            String filename = f.getFileName().toString();
            String extension = cropExtension(filename);
            if (isValidExtension(extension)) {
                imageDescMap.put(f, ImageFormat.valueOf(extension.toUpperCase()));
            } else {
                logger.debug("File:{} has a wrong extension, it will be skipped", filename);
            }

        });

        InputStream is;
        byte[] bytes;

        for (Map.Entry<Path, ImageFormat> image : imageDescMap.entrySet()) {
            is = Files.newInputStream(image.getKey());
            bytes = imageToByte(is);
            bytesList.add(bytes);
            is.close();
        }
        repository.persistAll(bytesList);
    }

    private String cropExtension(String filename) {
        Optional<String> opt = Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
        return opt.orElse("");

    }

    private boolean isValidExtension(String e) {
        return (e.equalsIgnoreCase("png")
                || e.equalsIgnoreCase("jpg")
                || e.equalsIgnoreCase("jpeg"));
    }
}
