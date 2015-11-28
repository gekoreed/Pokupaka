package com.selfach.processor.handlers.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.selfach.dao.CameraRatingDao;
import com.selfach.dao.jooq.tables.records.CameraratingRecord;
import com.selfach.processor.handlers.GeneralHandler;
import com.selfach.processor.handlers.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.OptionalDouble;

/**
 * Created by eshevchenko on 12.10.15 at 16:51.
 */
@Component("rateCamera")
public class RateCameraHandler implements GeneralHandler<RateCameraHandler.RatingResponse> {

    @Autowired
    CameraRatingDao cameraRatingDao;

    @Override
    public RatingResponse handle(ObjectNode node) throws Exception {

        int userId = node.get("userId").asInt();
        int cameraId = node.get("cameraId").asInt();
        int rating = node.get("rating").asInt();

        cameraRatingDao.addCameraRating(cameraId, rating, userId);

        OptionalDouble average = cameraRatingDao.getCameraRating(cameraId).stream()
                .mapToDouble(CameraratingRecord::getRaiting)
                .average();
        return new RatingResponse(average.orElse(0));
    }

    class RatingResponse extends Response {
        public double newRating;

        public RatingResponse(double newRating) {
            this.newRating = newRating;
        }
    }
}
