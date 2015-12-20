package com.selfach.processor.handlers.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.selfach.dao.PhotoDao;
import com.selfach.dao.jooq.tables.Camera;
import com.selfach.dao.jooq.tables.Photo;
import com.selfach.dao.jooq.tables.records.PhotoRecord;
import com.selfach.processor.handlers.GeneralHandler;
import com.selfach.processor.handlers.Response;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.selfach.dao.jooq.tables.Photo.*;
import static java.util.stream.Collectors.toList;

@Component("getPhotos")
public class GetPhotoListHandler implements GeneralHandler<GetPhotoListHandler.ListResponce> {

    @Autowired
    PhotoDao photoDao;

    @Override
    public ListResponce handle(ObjectNode node) throws Exception {
        ListResponce responce = new ListResponce();

        int userId = node.get("userId").asInt();

        responce.photos = photoDao.getPhotosByUserId(userId).stream()
                .map(this::convert)
                .collect(toList());

        return responce;
    }

    public class ListResponce extends Response {
        public List<Photo> photos;
    }

    class Photo {
        public String name;
        public String format;
        public String cameraName;
        public int cameraId;
    }

    private Photo convert(Record record){
        Photo photo = new Photo();
        photo.cameraId = record.getValue(PHOTO.CAMERAID);
        photo.cameraName = record.getValue(Camera.CAMERA.NAME);
        photo.name = record.getValue(PHOTO.USERID) +"-"+record.getValue(PHOTO.CREATED)+"-";
        photo.format = record.getValue(PHOTO.FORMAT);
        return photo;
    }
}
