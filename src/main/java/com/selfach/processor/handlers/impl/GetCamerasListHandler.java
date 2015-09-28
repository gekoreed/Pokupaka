package com.selfach.processor.handlers.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.selfach.dao.CamerasDao;
import com.selfach.dao.jooq.tables.records.CameraRecord;
import com.selfach.processor.handlers.GeneralHandler;
import com.selfach.processor.handlers.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * By gekoreed on 9/26/15.
 */
@Component("getCamerasList")
public class GetCamerasListHandler implements GeneralHandler<GetCamerasListHandler.CamerasResponse>{

    @Autowired
    CamerasDao camerasDao;

    @Override
    public CamerasResponse handle(ObjectNode node) throws Exception {
        CamerasResponse response = new CamerasResponse();

        List<CameraRecord> cameras = camerasDao.getAvailableCameras();

        response.cameras = cameras.stream().map(cam -> {
            CameraPair cameraPair = new CameraPair();
            cameraPair.id = cam.getId();
            cameraPair.latitude = cam.getLatitude();
            cameraPair.longitude = cam.getLongitude();
            cameraPair.name = cam.getName();
            cameraPair.angle = cam.getAngle();
            return cameraPair;
        }).collect(toList());

        return response;
    }

    public class CamerasResponse extends Response{
        public List<CameraPair> cameras = new ArrayList<>();
    }

    class CameraPair{
        public String name;
        public String longitude;
        public String latitude;
        public int id;
        public int angle;
    }
}
