package com.selfach.dao.impl;

import com.selfach.dao.CameraRaitingDao;
import com.selfach.dao.jooq.tables.Cameraraiting;
import com.selfach.dao.jooq.tables.records.CameraraitingRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by eshevchenko on 12.10.15 at 15:42.
 */
@Repository
public class CameraRaitingDaoImpl implements CameraRaitingDao{

    @Autowired
    DSLContext context;

    @Override
    public List<CameraraitingRecord> getCameraRaiting(int cameraId) {
        return context.selectFrom(Cameraraiting.CAMERARAITING)
                .where(Cameraraiting.CAMERARAITING.CAMERAID.eq(cameraId))
                .fetchInto(CameraraitingRecord.class);
    }

    @Override
    public List<CameraraitingRecord> getCameraRaiting(List<Integer> ids) {
        return context.selectFrom(Cameraraiting.CAMERARAITING)
                .where(Cameraraiting.CAMERARAITING.CAMERAID.in(ids))
                .fetchInto(CameraraitingRecord.class);
    }

    @Override
    public void addCameraRaiting(int cameraId, int rating) {
        CameraraitingRecord record = new CameraraitingRecord();
        record.setCameraid(cameraId);
        record.setRaiting(rating);
        context.insertInto(Cameraraiting.CAMERARAITING).set(record).execute();
    }
}
