package com.selfach.processor.handlers.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.selfach.dao.UsersDao;
import com.selfach.dao.jooq.tables.records.UserRecord;
import com.selfach.exceptions.AndroidServerException;
import com.selfach.processor.handlers.GeneralHandler;
import com.selfach.processor.handlers.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * By gekoreed on 9/13/15.
 */
@Component("personalDataChange")
public class ChangePersonalDataHandler implements GeneralHandler<Response> {

    @Autowired
    UsersDao usersDao;

    @Override
    public Response handle(ObjectNode node) throws Exception {
        int userId = node.get("id").asInt();
        Timestamp time = new Timestamp(node.get("timestamp").asLong());

        UserRecord user = usersDao.getUserById(userId);
        if  (user == null)
            throw new AndroidServerException("user_not_found");

        user.setEmail(node.has("email") ? node.get("email").asText() : user.getEmail());
        user.setName(node.has("name") ? node.get("name").asText() : user.getName());
        user.setSurname(node.has("surname") ? node.get("surname").asText() : user.getSurname());

        user.setModified(time);

        user.setPasswordhash(node.has("passwordHash") ? node.get("passwordHash").asText() : user.getPasswordhash());

        usersDao.update(user);

        return new Response();
    }
}
