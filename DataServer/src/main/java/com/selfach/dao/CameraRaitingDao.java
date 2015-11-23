package com.selfach.dao;

import com.selfach.dao.jooq.tables.records.CameraratingRecord;

import java.util.List;


public interface CameraRaitingDao {
    List<CameraratingRecord> getCameraRaiting(int cameraId);

    List<CameraratingRecord> getCameraRaiting(List<Integer> ids);

    void addCameraRaiting(int cameraId, int rating, int userId);
}
