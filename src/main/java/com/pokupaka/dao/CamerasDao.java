package com.pokupaka.dao;

import com.pokupaka.dao.jooq.tables.records.CameraRecord;

import java.util.List;

/**
 * By gekoreed on 9/26/15.
 */
public interface CamerasDao {
    public List<CameraRecord> getAvailableCameras();

    public int save(CameraRecord record);

    CameraRecord getCameraById(int cameraId);
}
