package org.motoc.gamelibrary;


import org.motoc.gamelibrary.business.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * It's methods run is executed on startup. It fill the database with demo data.sql.
 */
@Component
public class CommandLineStartupRunner
        implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineStartupRunner.class);

    // Repository
    private final ImageService imageService;

    @Autowired
    public CommandLineStartupRunner(ImageService imageService) {
        logger.warn("Starting CommandLineRunner");
        this.imageService = imageService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.fillImages();

    }

    public void fillImages() throws IOException {
        File fileA = new File("src/main/resources/static/catane.jpg");
        BufferedImage imageA = ImageIO.read(fileA.getAbsoluteFile());
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(imageA, "png", os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());


        imageService.saveThenAttachToGame(is, 42L);
        os.close();
        is.close();

    }
}
