package com.selfach.dao.impl;

import com.selfach.dao.PhotoDao;
import com.selfach.dao.jooq.tables.Camera;
import com.selfach.dao.jooq.tables.records.PhotoRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.selfach.dao.jooq.tables.Photo.PHOTO;

@Repository
public class PhotoDaoImpl implements PhotoDao{

    @Autowired
    DSLContext context;

    @Override
    public Result<Record> getPhotosByUserId(int userId) {
        return context.select().from(PHOTO).join(Camera.CAMERA).on(Camera.CAMERA.ID.eq(PHOTO.CAMERAID))
                .where(PHOTO.USERID.eq(userId))
                .fetch();
    }

    @Override
    public int savePhoto(PhotoRecord photoRecord) {
        return context.insertInto(PHOTO)
                .set(photoRecord)
                .returning()
                .fetchOne().getId();
    }

    @Override
    public void deletePhoto(String name) {
        context.deleteFrom(PHOTO).where(PHOTO.CREATED.eq(name)).execute();
    }
}
