package com.pokupaka.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * By gekoreed on 9/22/15.
 */
@Service
public class SnapShotter {
    public static void main(String[] args) throws IOException, InterruptedException {
        boolean b = new SnapShotter().makeImage("image-" + System.currentTimeMillis(), "http://stream.kpi.ua:8101/stream.flv");

        System.out.println(b);
    }

    public boolean makeImage(String imageName, String cameraURL) {

        try {
            Process pr = Runtime.getRuntime().exec(new String[]{"./capture.sh", cameraURL, imageName});
            pr.waitFor(10, TimeUnit.SECONDS);
        } catch (IOException | InterruptedException e) {
            return false;
        }

        return new File("pictures/"+imageName+".jpg").exists();

    }
}
