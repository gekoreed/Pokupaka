package com.pokupaka.processor.handlers.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pokupaka.dao.UsersDao;
import com.pokupaka.dao.jooq.tables.records.UserRecord;
import com.pokupaka.exceptions.AndroidServerException;
import com.pokupaka.processor.handlers.GeneralHandler;
import com.pokupaka.processor.handlers.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * By gekoreed on 9/12/15.
 */
@Component("registerUser")
public class RegisterUserHandler implements GeneralHandler<RegisterUserHandler.RegisterResponse> {


    @Autowired
    UsersDao usersDao;

    public void setUsersDao(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    @Override
    public RegisterResponse handle(ObjectNode node) throws Exception {

        RegisterResponse response = new RegisterResponse();

        Timestamp timestamp = new Timestamp(node.get("timestamp").asLong());

        if (!node.has("email") || !node.has("passwordHash"))
            throw new AndroidServerException("missing_data");

        if(usersDao.userExists(node.get("email").asText())){
            throw new AndroidServerException("user_exists");
        }

        UserRecord record = new UserRecord();
        record.setEmail(node.get("email").asText());
        record.setName(node.has("name") ? node.get("name").asText() : "");
        record.setSurname(node.has("surname") ? node.get("surname").asText() : "");

        record.setPasswordhash(node.get("passwordHash").asText());

        record.setCreated(timestamp);
        record.setModified(timestamp);

        response.serverUserId = usersDao.registerNewUser(record);
        return response;
    }

    public class RegisterResponse extends Response {
         public int serverUserId;
    }
}
