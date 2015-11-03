package com.selfach.dao;

import com.selfach.dao.jooq.tables.records.PhotoRecord;

import java.util.List;

/**
 * Created by eshevchenko on 05.10.15 at 17:21.
 */
public interface PhotoDao {
    List<PhotoRecord> getPhotosByUserId(int userId);

    int savePhoto(PhotoRecord photoRecord);

    void deletePhoto(String name);
}
