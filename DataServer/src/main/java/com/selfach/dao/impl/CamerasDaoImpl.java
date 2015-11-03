package com.selfach.dao.impl;

import com.selfach.dao.CamerasDao;
import com.selfach.dao.jooq.tables.records.CameraRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.selfach.dao.jooq.tables.Camera.CAMERA;

/**
 * By gekoreed on 9/26/15.
 */
@Repository
public class CamerasDaoImpl implements CamerasDao {

    @Autowired
    DSLContext context;

    @Override
    public List<CameraRecord> getAvailableCameras() {
        return context.selectFrom(CAMERA).fetchInto(CameraRecord.class);
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
}
