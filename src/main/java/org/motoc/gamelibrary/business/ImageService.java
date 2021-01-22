package org.motoc.gamelibrary.business;

import org.apache.commons.lang3.RandomStringUtils;
import org.motoc.gamelibrary.technical.properties.PathProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    public ImageService(PathProperties pathProperties) {
        this.pathProperties = pathProperties;
    }

    /**
     * Given an image, store it on file system, then return a full path in order to store it in database
     */
    public String storeImage(byte[] imageBytes, String imageExtension) {

        logger.debug("Starting operations to store an image into file system");
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
        } catch (IOException ioe) {
            logger.error("An I/O error occurred");
            logger.error(ioe.getMessage());
        }

        return strPath;
    }
}
