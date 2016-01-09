package com.selfach.service;

import com.selfach.enums.Resolution;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * By gekoreed on 9/22/15.
 */
@Service
public class SnapShotter {

    Logger logger = Logger.getLogger(SnapShotter.class);

    public boolean makeImage(String imageName, String cameraURL, Resolution resolution, int cameraId) {

        try {
            if (resolution == Resolution.COMPRESSED)
                imageName = "c/"+imageName;

            String param = "", protocol = "";

            if (cameraURL.startsWith("rtsp")) {
                protocol = "tcp";
                param = "-rtsp_transport";
            }


            Process pr = Runtime.getRuntime().exec(new String[]{"./capture.sh", cameraURL, imageName, resolution.toString(), param, protocol});
            pr.waitFor(10, TimeUnit.SECONDS);
            logger.info(System.currentTimeMillis());

            if (cameraId >= 0) {
                Files.copy(Paths.get("pictures/" + imageName + ".jpg"), Paths.get("last/" + cameraId + ".jpg"), REPLACE_EXISTING);
            }

        } catch (IOException | InterruptedException e) {
            return false;
        }

        return new File("pictures/"+imageName+".jpg").exists();

    }
}
