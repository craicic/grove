package org.motoc.gamelibrary;


import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.motoc.gamelibrary.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

/**
 * It's methods run is executed on startup. It fills the database with demo data.sql.
 */
@Component
public class CommandLineStartupRunner
        implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineStartupRunner.class);

    private final ImageService imageService;
    @Autowired
    public CommandLineStartupRunner(ImageService imageService) {
        logger.info("Starting CommandLineRunner");
        this.imageService = imageService;

    }

    /**
     * return int code
     */
    private int parseArguments(Options o, String... args) throws Exception {
        Path imageSourcePath = Path.of("grove-service/src/main/resources/static");
        Path csvSourcePath = imageSourcePath;
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(o, args);
        int code = 0;

        if (!cmd.hasOption('i')) {
            logger.debug("No parameter passed in argument, now exiting parseArguments method.");
            return code;
        }
        if (cmd.hasOption('i')) {
            logger.debug("Pictures insert enabled.");
            String iArg = cmd.getOptionValue("i");
            if (iArg == null) {
                logger.debug("Using default source 'src/main/resources/static' folder.");
            } else {
                logger.debug("Found argument of 'i' : {}, using it a source folder.", iArg);
                imageSourcePath = Path.of(iArg);
            }
            imageService.processImages(imageSourcePath);
            code += 1;
        }
        return code;
    }

    private Options createOption() {
        Options options = new Options();
        options.addOption("i", true, "Insert image into the database, by default images in source folder 'src/main/resources/static' are inserted, but path can be edited by passing a String as argument of 'i'");

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
            logger.debug("Run method exit with code:" + code);
        }
    }
}
