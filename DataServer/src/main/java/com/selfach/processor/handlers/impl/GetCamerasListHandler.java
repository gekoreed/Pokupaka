package com.selfach.processor.handlers.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.selfach.dao.CameraRatingDao;
import com.selfach.dao.CamerasDao;
import com.selfach.dao.UsersDao;
import com.selfach.dao.jooq.tables.records.CameraRecord;
import com.selfach.dao.jooq.tables.records.CameraratingRecord;
import com.selfach.processor.handlers.GeneralHandler;
import com.selfach.processor.handlers.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.groupingByConcurrent;
import static java.util.stream.Collectors.toList;

/**
 * By gekoreed on 9/26/15.
 */
@Component("getCamerasList")
public class GetCamerasListHandler implements GeneralHandler<GetCamerasListHandler.CamerasResponse> {

    @Autowired
    CamerasDao camerasDao;

    @Autowired
    CameraRatingDao cameraRatingDao;

    @Autowired
    UsersDao usersDao;

    @Override
    public CamerasResponse handle(ObjectNode node) throws Exception {
        CamerasResponse response = new CamerasResponse();
        int userId = node.has("userId") ? node.get("userId").asInt() : 1;
        String lang = usersDao.getLang(userId);
        int cameraGroup = node.has("group") ? node.get("group").asInt() : 0;
        List<CameraRecord> cameras;
        if (cameraGroup == 0)
            cameras = camerasDao.getAvailableCameras();
        else
            cameras = camerasDao.getCamerasByGroup(cameraGroup);
        List<Integer> ids = cameras.stream().map(CameraRecord::getId).collect(toList());

        Map<Integer, List<CameraratingRecord>> byid = cameraRatingDao.getCameraRating(ids)
                .stream().collect(groupingBy(CameraratingRecord::getCameraid));

        response.cameras = cameras.stream().map(cam -> {
            CameraPair cameraPair = new CameraPair();
            cameraPair.id = cam.getId();
            cameraPair.latitude = cam.getLatitude();
            cameraPair.longitude = cam.getLongitude();
            String[] split = cam.getName().split(";");
            cameraPair.name = lang.equals("ru") ? split[0] :
                    lang.equals("ua") ? split[1] : split[2];
            cameraPair.angle = cam.getAngle();
            cameraPair.description = cam.getDescription();
            String[] vector = cam.getVector().split(",");
            cameraPair.vectorLatitude = vector[0];
            cameraPair.vectorLongitude = vector[1];
            if (byid.keySet().contains(cam.getId())) {
                List<CameraratingRecord> ratings = byid.get(cam.getId());
                if (userId != 0)
                    cameraPair.userRated = ratings.stream().filter(r -> r.getUserid() == userId).count() > 0;
                OptionalDouble average = ratings.stream().mapToDouble(CameraratingRecord::getRaiting).average();
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
        public Double raiting = 0.0;
        public String description;
        public boolean userRated = false;
    }
}
