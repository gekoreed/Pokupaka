package com.selfach.processor.handlers.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.selfach.dao.CameraRaitingDao;
import com.selfach.dao.CamerasDao;
import com.selfach.dao.jooq.tables.records.CameraRecord;
import com.selfach.dao.jooq.tables.records.CameraraitingRecord;
import com.selfach.processor.handlers.GeneralHandler;
import com.selfach.processor.handlers.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * By gekoreed on 9/26/15.
 */
@Component("getCamerasList")
public class GetCamerasListHandler implements GeneralHandler<GetCamerasListHandler.CamerasResponse> {

    @Autowired
    CamerasDao camerasDao;

    @Autowired
    CameraRaitingDao cameraRaitingDao;

    @Override
    public CamerasResponse handle(ObjectNode node) throws Exception {
        CamerasResponse response = new CamerasResponse();

        List<CameraRecord> cameras = camerasDao.getAvailableCameras();
        List<Integer> ids = cameras.stream().map(CameraRecord::getId).collect(toList());

        Map<Integer, List<CameraraitingRecord>> byid = cameraRaitingDao.getCameraRaiting(ids)
                .stream().collect(groupingBy(CameraraitingRecord::getCameraid));

        response.cameras = cameras.stream().map(cam -> {
            CameraPair cameraPair = new CameraPair();
            cameraPair.id = cam.getId();
            cameraPair.latitude = cam.getLatitude();
            cameraPair.longitude = cam.getLongitude();
            cameraPair.name = cam.getName();
            cameraPair.angle = cam.getAngle();
            String[] vector = cam.getVector().split(",");
            cameraPair.vectorLatitude = vector[0];
            cameraPair.vectorLongitude = vector[1];
            if (byid.keySet().contains(cam.getId())) {
                OptionalDouble average = byid.get(cam.getId()).stream().mapToDouble(CameraraitingRecord::getRaiting).average();
                cameraPair.raiting = average.orElse(0.0);
            }
            return cameraPair;
        }).collect(toList());

        return response;
    }

    public class CamerasResponse extends Response {
        public List<CameraPair> cameras = new ArrayList<>();
    }

    class CameraPair {
        public String name;
        public String longitude;
        public String latitude;
        public String vectorLongitude;
        public String vectorLatitude;
        public int id;
        public int angle;
        public Double raiting = -1.0;
    }
}
