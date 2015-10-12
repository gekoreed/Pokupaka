package com.selfach.processor.handlers.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.selfach.dao.CameraRaitingDao;
import com.selfach.processor.handlers.GeneralHandler;
import com.selfach.processor.handlers.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by eshevchenko on 12.10.15 at 16:51.
 */
@Component("rateCamera")
public class RateCameraHandler implements GeneralHandler<Response> {

    @Autowired
    CameraRaitingDao cameraRaitingDao;

    @Override
    public Response handle(ObjectNode node) throws Exception {

        int cameraId = node.get("cameraId").asInt();
        int rating = node.get("rating").asInt();

        cameraRaitingDao.addCameraRaiting(cameraId, rating);

        return new Response();
    }
}
