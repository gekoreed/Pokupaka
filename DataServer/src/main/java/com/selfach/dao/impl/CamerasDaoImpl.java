package com.selfach.dao.impl;

import com.selfach.dao.CamerasDao;
import com.selfach.dao.jooq.tables.records.CameraRecord;
import com.selfach.dao.jooq.tables.records.CameragroupRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.selfach.dao.jooq.tables.Camera.CAMERA;
import static com.selfach.dao.jooq.tables.Cameragroup.*;

/**
 * By gekoreed on 9/26/15.
 */
@Repository
public class CamerasDaoImpl implements CamerasDao {

    @Autowired
    DSLContext context;

    @Override
    public List<CameraRecord> getAvailableCameras() {
        return context.selectFrom(CAMERA)
                .where(CAMERA.WORKING.eq(1))
                .fetchInto(CameraRecord.class);
    }

    @Override
    public int save(CameraRecord record) {
        return context.insertInto(CAMERA)
                .set(record)
                .returning().fetchOne()
                .getId();
    }

    @Override
    public CameraRecord getCameraById(int cameraId) {
        return context.selectFrom(CAMERA)
                .where(CAMERA.ID.eq(cameraId))
                .fetchOne();
    }

    @Override
    public void setCameraNotWorking(CameraRecord cameraById) {
        context.update(CAMERA)
                .set(CAMERA.WORKING, 0)
                .where(CAMERA.ID.eq(cameraById.getId()))
                .execute();
    }
    @Override
    public void setCameraWorking(CameraRecord camera) {
        context.update(CAMERA)
                .set(CAMERA.WORKING, 1)
                .where(CAMERA.ID.eq(camera.getId()))
                .execute();
    }

    @Override
    public List<CameraRecord> getUnavailableCameras() {
        return context.selectFrom(CAMERA)
                .where(CAMERA.WORKING.eq(0))
                .fetchInto(CameraRecord.class);
    }

    @Override
    public List<CameraRecord> getAllCameras() {
        return context.selectFrom(CAMERA)
                .fetchInto(CameraRecord.class);
    }

    @Override
    public List<CameragroupRecord> getCameraGroups() {
        return context.select().from(CAMERA).join(CAMERAGROUP).on(CAMERAGROUP.ID.eq(CAMERA.CAMERAGROUP))
                .groupBy(CAMERAGROUP.ID)
                .fetchInto(CameragroupRecord.class);
    }

    @Override
    public List<CameraRecord> getCamerasByGroup(int cameraGroup) {
        return context.select().from(CAMERA).join(CAMERAGROUP).on(CAMERAGROUP.ID.eq(CAMERA.CAMERAGROUP))
                .where(CAMERAGROUP.ID.eq(cameraGroup))
                .fetchInto(CameraRecord.class);
    }


}
