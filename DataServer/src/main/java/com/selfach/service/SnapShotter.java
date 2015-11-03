package com.selfach.service;

import com.selfach.enums.Resolution;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * By gekoreed on 9/22/15.
 */
@Service
public class SnapShotter {

    Logger logger = Logger.getLogger(SnapShotter.class);

    public boolean makeImage(String imageName, String cameraURL, Resolution resolution) {

        try {
            if (resolution == Resolution.COMPRESSED)
                imageName = "c/"+imageName;
            Process pr = Runtime.getRuntime().exec(new String[]{"./capture.sh", cameraURL, imageName, resolution.toString()});
            pr.waitFor(10, TimeUnit.SECONDS);
            logger.info(System.currentTimeMillis());
        } catch (IOException | InterruptedException e) {
            return false;
        }

        return new File("pictures/"+imageName+".jpg").exists();

    }
}
