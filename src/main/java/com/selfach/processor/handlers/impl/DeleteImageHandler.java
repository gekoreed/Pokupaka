package com.selfach.processor.handlers.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.selfach.dao.PhotoDao;
import com.selfach.processor.handlers.GeneralHandler;
import com.selfach.processor.handlers.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("deleteImage")
public class DeleteImageHandler implements GeneralHandler<Response> {

    @Autowired
    PhotoDao photoDao;

    @Override
    public Response handle(ObjectNode node) {

        String imageName = node.get("imageName").asText();

        photoDao.deletePhoto(imageName);

        return new Response();
    }

}
