package com.selfach.dao;

import com.selfach.dao.jooq.tables.records.CameraRecord;
import com.selfach.dao.jooq.tables.records.CameragroupRecord;

import java.util.List;

/**
 * By gekoreed on 9/26/15.
 */
public interface CamerasDao {
    List<CameraRecord> getAvailableCameras();

    int save(CameraRecord record);

    CameraRecord getCameraById(int cameraId);

    void setCameraNotWorking(CameraRecord cameraById);

    void setCameraWorking(CameraRecord camera);
    List<CameraRecord> getUnavailableCameras();

    List<CameraRecord> getAllCameras();

    List<CameragroupRecord> getCameraGroups(String lang);

    List<CameraRecord> getCamerasByGroup(int cameraGroup);
}
