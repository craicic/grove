package org.motoc.gamelibrary;


import org.apache.commons.cli.*;
import org.motoc.gamelibrary.domain.enumeration.ImageFormat;
import org.motoc.gamelibrary.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

/**
 * It's methods run is executed on startup. It fill the database with demo data.sql.
 */
@Component
public class CommandLineStartupRunner
        implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineStartupRunner.class);

    private final ImageService imageService;

    private Path sourcePath = Path.of("game-library/src/main/resources/static");
    private ImageFormat outputFormat = ImageFormat.PNG;

    @Autowired
    public CommandLineStartupRunner(ImageService imageService) {
        logger.info("Starting CommandLineRunner");
        this.imageService = imageService;

    }

    /**
     * return int code
     * -1 : no action
     * 0 : insert with no conversion
     * 1 : insert with conversion
     */
    private int parseArguments(Options o, String... args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(o, args);

        if (!cmd.hasOption("i")) {
            logger.debug("No parameter 'i' passed in argument, now exiting parseArguments method.");
            return -1;
        } else {
            logger.debug("Insert enabled.");
            String iArg = cmd.getOptionValue("i");
            if (iArg == null) {
                logger.debug("Using default source 'src/main/resources/static' folder.");
            } else {
                logger.debug("Found argument of 'i' : {}, using it a source folder.", iArg);
                sourcePath = Path.of(iArg);
            }
            if (!cmd.hasOption("c")) {
                logger.debug("Conversion will be skipped.");
                return 0;
            } else {
                logger.debug("Conversion enabled.");
                String cArg = cmd.getOptionValue("c");
                if (cArg == null) {
                    logger.debug("Using default PNG format.");
                } else if (cArg.equalsIgnoreCase("jpg")) {
                    logger.debug("Using default JPG format.");
                    outputFormat = ImageFormat.JPG;
                } else {
                    logger.debug("Specified format is invalid, must be 'jpg' or 'png'. now exiting parseArguments method.");
                    return -1;
                }
            }
            return 1;
        }

    }

    private Options createOption() {
        Options options = new Options();
        options.addOption("i", false, "Insert image into the database, by default images in source folder 'src/main/resources/static' are inserted, but path can be edited by passing a String as argument of 'i'");
        options.addOption("c", false, "Convert images before the insert into database, default output format is PNG but passing 'png' or 'jpg' will change the format");
        return options;
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length == 0) {
            logger.info("Found no argument, now exiting run method.");
        } else {
            logger.debug("Application arguments Definition stage");
            Options o = createOption();
            logger.debug("Parsing the command line arguments");
            int code = parseArguments(o, args);
            if (code >= 0) {
                imageService.processImages(sourcePath, code > 0, outputFormat);
            }
        }
    }
}
