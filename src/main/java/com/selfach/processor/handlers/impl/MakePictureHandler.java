package com.selfach.processor.handlers.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.selfach.dao.CamerasDao;
import com.selfach.dao.jooq.tables.records.CameraRecord;
import com.selfach.enums.Resolution;
import com.selfach.exceptions.AndroidServerException;
import com.selfach.processor.handlers.GeneralHandler;
import com.selfach.processor.handlers.Response;
import com.selfach.service.PictureCompressor;
import com.selfach.service.SnapShotter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * By gekoreed on 9/26/15.
 */
@Component("makePicture")
public class MakePictureHandler implements GeneralHandler<MakePictureHandler.MakerResponse>{

    @Autowired
    CamerasDao camerasDao;

    @Autowired
    SnapShotter snapShotter;

    @Autowired
    PictureCompressor compressor;

    @Override
    public MakerResponse handle(ObjectNode node) throws Exception {

        String userId = node.get("userId").asText();
        int cameraId = node.get("cameraId").asInt();

        CameraRecord cameraById = camerasDao.getCameraById(cameraId);

        if (cameraById == null)
            throw new AndroidServerException("CameraNotFound");

        String imageName = userId + "-" + System.currentTimeMillis()+"-";
        CompletableFuture<Boolean> original = CompletableFuture.supplyAsync(
                () -> snapShotter.makeImage(imageName, cameraById.getUrl(), Resolution.ORIGINAL));

        CompletableFuture<Boolean> compressed = CompletableFuture.supplyAsync(
                () -> snapShotter.makeImage(imageName, cameraById.getUrl(), Resolution.COMPRESSED));

        CompletableFuture<Boolean> bothDone = original.thenCombine(compressed, (orig, compr) -> orig && compr);

        if (!bothDone.get()){
            throw new AndroidServerException("Something wrong with Server");
        }

        MakerResponse response = new MakerResponse();

        response.fileName = imageName;
        response.format = "jpg";
        return response;
    }


    public class MakerResponse extends Response {
        public String fileName;
        public String format;
    }
}
