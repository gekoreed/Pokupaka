package com.selfach.dao;

import com.selfach.dao.jooq.tables.records.CameraratingRecord;

import java.util.List;


public interface CameraRatingDao {
    List<CameraratingRecord> getCameraRating(int cameraId);

    List<CameraratingRecord> getCameraRating(List<Integer> ids);

    void addCameraRating(int cameraId, int rating, int userId);
}
