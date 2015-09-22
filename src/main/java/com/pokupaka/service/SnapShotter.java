package com.pokupaka.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * By gekoreed on 9/22/15.
 */
@Service
public class SnapShotter {
    public static void main(String[] args) throws IOException, InterruptedException {
        new SnapShotter().makeImage("image-" + System.currentTimeMillis(), "http://stream.kpi.ua:8101/stream.flv");

    }

    public void makeImage(String imageName, String cameraURL) throws IOException, InterruptedException {
        List<String> strings = System.getProperty("os.name").toLowerCase().contains("mac")
                || System.getProperty("os.name").toLowerCase().contains("lin")
                ? Files.readAllLines(Paths.get("capture.sh"))
                : Collections.<String>emptyList();
        if (strings.isEmpty()) {
            return;
        }
        StringBuilder builder = new StringBuilder();


        for (String string : strings) {
            if (string.contains("ff")) {
                builder.append(String.format(string, cameraURL, imageName)).append("\n");
            }
        }
        Files.write(Paths.get("capture2.sh"), builder.toString().getBytes());

        Process pr = Runtime.getRuntime().exec("./capture2.sh");

        pr.waitFor(10, TimeUnit.SECONDS);
    }
}
