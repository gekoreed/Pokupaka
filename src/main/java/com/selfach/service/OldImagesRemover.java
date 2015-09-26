package com.selfach.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * By gekoreed on 9/26/15.
 */
@Component
public class OldImagesRemover {

    @Scheduled(fixedDelay = 60*60*1000)
    public void removaOldImages(){
        String[] list = new File("pictures").list();

        long currentTimeMillis = System.currentTimeMillis();

        for (String s : list) {
            String[] splitted = s.split("-");
            String nanoTime = splitted[splitted.length - 2];
            if (currentTimeMillis - Long.valueOf(nanoTime) > 3600000 * 24){
                boolean delete = new File("pictures/" + s).delete();
            }
        }
    }
}
