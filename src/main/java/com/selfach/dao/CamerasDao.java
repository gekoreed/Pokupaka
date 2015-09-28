package com.selfach.dao;

import com.selfach.dao.jooq.tables.records.CameraRecord;

import java.util.List;

/**
 * By gekoreed on 9/26/15.
 */
public interface CamerasDao {
    List<CameraRecord> getAvailableCameras();

    int save(CameraRecord record);

    CameraRecord getCameraById(int cameraId);
}
