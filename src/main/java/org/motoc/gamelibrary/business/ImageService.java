package org.motoc.gamelibrary.business;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.motoc.gamelibrary.model.Image;
import org.motoc.gamelibrary.repository.jpa.ImageRepository;
import org.motoc.gamelibrary.technical.properties.PathProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Perform business logic on the entity Image, it is in charge to make file operations on Image
 *
 * @author RouzicJ
 */
@Service
@Transactional
public class ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    private final PathProperties pathProperties;

    private final ImageRepository repository;

    @Autowired
    public ImageService(PathProperties pathProperties, ImageRepository repository) {
        this.pathProperties = pathProperties;
        this.repository = repository;
    }


    /**
     * Given a MultipartFile, call methods to store it on file system, and in database
     */
    public Long save(MultipartFile image) {
        String path = "";
        path = storeImage(image);
        return persistPath(path);
    }

    /**
     * Given a MultipartFile, store it on file system, then return a full path in order to store it in database
     */
    private String storeImage(MultipartFile image) {

        logger.debug("Starting operations to store an image into file system");
        byte[] imageBytes;

        try {
            imageBytes = image.getBytes();
        } catch (IOException e) {
            return String.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        }

        String imageExtension = "";
        if (image.getOriginalFilename() != null
                && image.getOriginalFilename().endsWith(".png")) {
            imageExtension = "png";
        } else if (image.getOriginalFilename() != null
                && image.getOriginalFilename().endsWith(".jpeg")
                || image.getOriginalFilename().endsWith(".jpg")) {
            imageExtension = "jpg";
        } else {
            return String.valueOf(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
        }


        /* We choose a randomly generated name, using RandomStringUtils */
        String name = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(8), imageExtension);
        /* We now define the path */
        String strPath = pathProperties.getPathRoot() + name;
        logger.debug("Full path for this image is : " + strPath);
        /* Set up our file */
        File imageToStore = new File(strPath);
        /* Write image on filesystem */

        try {
            FileOutputStream outputStream = new FileOutputStream(imageToStore);
            outputStream.write(imageBytes);
            logger.debug("Image successfully stored");
            outputStream.close();
        } catch (FileNotFoundException fe) {
            logger.error("An error occurred : the file exists but is a directory rather than a regular file, does not" +
                    " exist but cannot be created, or cannot be opened for any other reason");
            logger.error(fe.getMessage());
            return String.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        } catch (IOException ioe) {
            logger.error("An I/O error occurred");
            logger.error(ioe.getMessage());
            return String.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        }

        return strPath;
    }

    Long persistPath(String path) {
        Image image = new Image();
        image.setFilePath(path);
        return this.repository.save(image).getId();
    }
}
