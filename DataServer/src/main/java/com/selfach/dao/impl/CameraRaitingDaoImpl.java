package com.selfach.dao.impl;

import com.selfach.dao.CameraRaitingDao;
import com.selfach.dao.jooq.tables.Camerarating;
import com.selfach.dao.jooq.tables.records.CameraratingRecord;
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
    public List<CameraratingRecord> getCameraRaiting(int cameraId) {
        return context.selectFrom(Camerarating.CAMERARATING)
                .where(Camerarating.CAMERARATING.CAMERAID.eq(cameraId))
                .fetchInto(CameraratingRecord.class);
    }

    @Override
    public List<CameraratingRecord> getCameraRaiting(List<Integer> ids) {
        return context.selectFrom(Camerarating.CAMERARATING)
                .where(Camerarating.CAMERARATING.CAMERAID.in(ids))
                .fetchInto(CameraratingRecord.class);
    }

    @Override
    public void addCameraRaiting(int cameraId, int rating, int userId) {
        CameraratingRecord record = new CameraratingRecord();
        record.setCameraid(cameraId);
        record.setRaiting(rating);
        record.setUserid(userId);
        context.insertInto(Camerarating.CAMERARATING).set(record).execute();
    }
}
