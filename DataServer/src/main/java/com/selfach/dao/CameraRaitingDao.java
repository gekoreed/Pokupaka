package com.selfach.dao;

import com.selfach.dao.jooq.tables.records.CameraraitingRecord;

import java.util.List;


public interface CameraRaitingDao {
    List<CameraraitingRecord> getCameraRaiting(int cameraId);

    List<CameraraitingRecord> getCameraRaiting(List<Integer> ids);

    void addCameraRaiting(int cameraId, int rating);
}
