package com.selfach.service;

import com.selfach.dao.PhotoDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
        Timestamp dayBefore = Timestamp.valueOf(LocalDateTime.now().minusDays(1));

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

        String[] names = new File("pictures").list();

        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            if (name.equals("c"))
                continue;
            String[] splitted = name.split("-");
            String nanoTime = splitted[splitted.length - 2];
            try {
                Timestamp made = new Timestamp(format.parse(nanoTime).getTime());
                if (dayBefore.after(made)) {
                    logger.info("Deleting:        " + name);
                    boolean delete = new File("pictures/" + name).delete();
                    boolean delete2 = new File("pictures/c/" + name).delete();
                    if (!delete || !delete2)
                        logger.info("Shit happens");
                    photoDao.deletePhoto(nanoTime);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
