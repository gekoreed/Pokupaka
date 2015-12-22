package com.selfach.processor.handlers.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.selfach.dao.UsersDao;
import com.selfach.dao.jooq.tables.records.UserRecord;
import com.selfach.processor.handlers.GeneralHandler;
import com.selfach.processor.handlers.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("langSave")
public class LangHandler implements GeneralHandler<Response> {


    @Autowired
    UsersDao usersDao;


    @Override
    public Response handle(ObjectNode node) {

        String lang = node.get("lang").asText();
        int userId = node.get("userId").asInt();
        usersDao.updateLang(userId, lang);

        return new Response();
    }

}
