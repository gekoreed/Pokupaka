package com.selfach.service;

import com.selfach.dao.CamerasDao;
import com.selfach.dao.jooq.tables.records.CameraRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by gekoreed on 12/17/15.
 */
@Component
public class CamerasBackup {

    @Autowired
    CamerasDao camerasDao;

    @Scheduled(fixedDelay = 24 * 3600000)
    public void doBackup() throws IOException {
        StringBuilder str = new StringBuilder();

        List<CameraRecord> cameras = camerasDao.getAllCameras();
        for (CameraRecord camera : cameras) {
            str.append(camera.getName()).append(";")
                    .append(camera.getUrl()).append(";")
                    .append(camera.getLongitude()).append(";")
                    .append(camera.getLatitude()).append(";")
                    .append(camera.getVector()).append(";")
                    .append(camera.getAngle()).append(";")
                    .append(camera.getWorking()).append(";")
                    .append(camera.getDescription()).append(";")
                    .append(camera.getCameragroup()).append("\n");
        }

        Files.write(Paths.get("cameraBackup.txt"), str.toString().getBytes());
    }
}
