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

        for (String name : new File("pictures").list()) {
            if (name.equals("c"))
                continue;
            String[] splitted = name.split("-");
            String nanoTime = splitted[splitted.length - 2];
            Timestamp made = new Timestamp(format.parse(nanoTime).getTime());
            if (dayBefore.after(made)) {
                logger.info("Deleting:        " + name);
                if (!new File("pictures/" + name).delete()
                        || !new File("pictures/c/" + name).delete())
                    logger.info("Shit happens");
                photoDao.deletePhoto(nanoTime);
            }
        }
    }
}
