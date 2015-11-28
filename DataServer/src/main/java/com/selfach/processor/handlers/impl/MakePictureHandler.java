package com.selfach.processor.handlers.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.selfach.dao.CamerasDao;
import com.selfach.dao.PhotoDao;
import com.selfach.dao.jooq.tables.records.CameraRecord;
import com.selfach.dao.jooq.tables.records.PhotoRecord;
import com.selfach.enums.Resolution;
import com.selfach.exceptions.AndroidServerException;
import com.selfach.processor.handlers.GeneralHandler;
import com.selfach.processor.handlers.Response;
import com.selfach.service.PictureCompressor;
import com.selfach.service.SnapShotter;
import com.selfach.service.ThreadsExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    @Autowired
    PhotoDao photoDao;

    @Autowired
    ThreadsExecutor threadsExecutor;

    @Override
    public MakerResponse handle(ObjectNode node) throws Exception {

        int userId = node.get("userId").asInt();
        int cameraId = node.get("cameraId").asInt();

        CameraRecord cameraById = camerasDao.getCameraById(cameraId);

        if (cameraById == null)
            throw new AndroidServerException("CameraNotFound");

        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        String imageName = userId + "-" + date +"-";
        boolean done = snapShotter.makeImage(imageName, cameraById.getUrl(), Resolution.ORIGINAL);

        if (!done){
//            camerasDao.setCameraNotWorking(cameraById);
            throw new AndroidServerException("Something wrong with Server");
        }

        threadsExecutor.submit(() -> compressor.resizeImage(new File("pictures/"+imageName+".jpg")));

        PhotoRecord photoRecord = new PhotoRecord();
        photoRecord.setCreated(date);
        photoRecord.setUserid(userId);
        photoRecord.setCameraid(cameraId);
        photoRecord.setFormat("jpg");
        photoDao.savePhoto(photoRecord);

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
