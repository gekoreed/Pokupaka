package com.pokupaka.processor.handlers.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pokupaka.dao.CamerasDao;
import com.pokupaka.dao.jooq.tables.records.CameraRecord;
import com.pokupaka.exceptions.AndroidServerException;
import com.pokupaka.processor.handlers.Base64Util;
import com.pokupaka.processor.handlers.GeneralHandler;
import com.pokupaka.processor.handlers.Response;
import com.pokupaka.service.SnapShotter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * By gekoreed on 9/26/15.
 */
@Component("makePicture")
public class MakePictureHandler implements GeneralHandler<MakePictureHandler.MakerResponse>{

    @Autowired
    CamerasDao camerasDao;

    @Autowired
    SnapShotter snapShotter;

    @Override
    public MakerResponse handle(ObjectNode node) throws Exception {

        String userId = node.get("userId").asText();
        int cameraId = node.get("cameraId").asInt();

        CameraRecord cameraById = camerasDao.getCameraById(cameraId);

        if (cameraById == null)
            throw new AndroidServerException("CameraNotFound");

        String imageName = userId + System.currentTimeMillis();
        boolean pictureDone = snapShotter.makeImage(imageName, cameraById.getUrl());
        if (!pictureDone){
            throw new AndroidServerException("Something wrong with Server");
        }
        MakerResponse response = new MakerResponse();

        response.base = Base64Util.encode("pictures/" + imageName + ".jpg");



        return response;
    }


    public class MakerResponse extends Response {
        public String base;
    }
}
