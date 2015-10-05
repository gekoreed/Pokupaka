package com.selfach.dao.impl;

import com.selfach.dao.PhotoDao;
import com.selfach.dao.jooq.tables.records.PhotoRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

import static com.selfach.dao.jooq.tables.Photo.PHOTO;

@Repository
public class PhotoDaoImpl implements PhotoDao{

    @Autowired
    DSLContext context;

    @Override
    public List<PhotoRecord> getPhotosByUserId(int userId) {
        return context.selectFrom(PHOTO)
                .where(PHOTO.USERID.eq(userId))
                .fetchInto(PhotoRecord.class);
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
        context.deleteFrom(PHOTO).where(PHOTO.CREATED.eq(new Timestamp(Long.valueOf(name)))).execute();
    }
}
