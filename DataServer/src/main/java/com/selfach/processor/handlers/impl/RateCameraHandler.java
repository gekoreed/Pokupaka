package com.selfach.processor.handlers.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.selfach.dao.CameraRaitingDao;
import com.selfach.dao.jooq.tables.records.CameraraitingRecord;
import com.selfach.processor.handlers.GeneralHandler;
import com.selfach.processor.handlers.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by eshevchenko on 12.10.15 at 16:51.
 */
@Component("rateCamera")
public class RateCameraHandler implements GeneralHandler<RateCameraHandler.Resp> {

    @Autowired
    CameraRaitingDao cameraRaitingDao;

    @Override
    public Resp handle(ObjectNode node) throws Exception {

        int userId = node.get("userId").asInt();
        int cameraId = node.get("cameraId").asInt();
        int rating = node.get("rating").asInt();

        cameraRaitingDao.addCameraRaiting(cameraId, rating, userId);

        Resp resp = new Resp();
        resp.newRating = cameraRaitingDao.getCameraRaiting(cameraId).stream()
                .mapToDouble(CameraraitingRecord::getRaiting)
                .average()
                .getAsDouble();
        return resp;
    }

    class Resp extends Response{
         public double newRating;
    }
}
