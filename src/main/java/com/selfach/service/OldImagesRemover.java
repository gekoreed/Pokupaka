package com.selfach.service;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;

/**
 * By gekoreed on 9/26/15.
 */
@Component
public class OldImagesRemover {

    Logger logger = Logger.getLogger(OldImagesRemover.class);

    @Scheduled(fixedDelay = 60*60*1000)
    public void removaOldImages() throws Exception {

        long currentTimeMillis = System.currentTimeMillis();
        Arrays.asList(new File("pictures").list()).stream()
                .filter(s -> !s.equals("c"))
                .filter(s -> {
                    String[] splitted = s.split("-");
                    String nanoTime = splitted[splitted.length - 2];
                    return currentTimeMillis - Long.valueOf(nanoTime) > 3600000 * 24;
                })
                .map(s -> {
                    boolean delete = new File("pictures/" + s).delete();
                    boolean delete2 = new File("pictures/c/" + s).delete();
                    return !delete || !delete2;
                }).findAny().ifPresent((c) -> logger.info("not all images were removed"));

    }
}
