//package org.motoc.gamelibrary;
//
//
//import org.motoc.gamelibrary.model.Image;
//import org.motoc.gamelibrary.repository.ImageRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//public class CommandLineStartupRunner implements CommandLineRunner {
//
//    private final ImageRepository imageRepository;
//
//    public CommandLineStartupRunner(@Autowired ImageRepository imageRepository) {
//        this.imageRepository = imageRepository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        Image demoImageA = new Image();
//        Image demoImageB = new Image();
//        demoImageA.setFilePath("\\user\\imageA.jpg");
//        demoImageB.setFilePath("\\user\\imageB.jpg");
//
//        imageRepository.save(demoImageA);
//        imageRepository.save(demoImageB);
//        imageRepository.flush();
//
//    }
//}
