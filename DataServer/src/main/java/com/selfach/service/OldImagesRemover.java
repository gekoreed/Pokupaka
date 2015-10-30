package com.selfach.service;

import com.selfach.dao.PhotoDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * By gekoreed on 9/26/15.
 */
@Component
public class OldImagesRemover {

    @Autowired
    PhotoDao photoDao;

    Logger logger = Logger.getLogger(OldImagesRemover.class);

    @Scheduled(fixedDelay = 60 * 60 * 1000)
    public void removaOldImages() throws Exception {
        Date d = new Date();

        Arrays.asList(new File("pictures").list()).stream()
                .filter(s -> !s.equals("c"))
                .filter(file -> {
                    String[] splitted = file.split("-");
                    String nanoTime = splitted[splitted.length - 2];
                    Long str = Long.valueOf(nanoTime.substring(0, 10));
                    Long date = Long.valueOf(new SimpleDateFormat("yyyyMMddHH").format(d));
                    return date - str > 100;
                })
                .map(photoName -> {
                    boolean delete = new File("pictures/" + photoName).delete();
                    boolean delete2 = new File("pictures/c/" + photoName).delete();
                    photoDao.deletePhoto(photoName.split("-")[1]);
                    return !delete || !delete2;
                }).findAny().ifPresent((c) -> logger.info("not all images were removed"));

    }

}
