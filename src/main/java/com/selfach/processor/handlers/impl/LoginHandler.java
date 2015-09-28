package com.selfach.processor.handlers.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.selfach.dao.UsersDao;
import com.selfach.dao.jooq.tables.records.UserRecord;
import com.selfach.exceptions.AndroidServerException;
import com.selfach.processor.handlers.GeneralHandler;
import com.selfach.processor.handlers.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * By gekoreed on 9/12/15.
 */
@Component("login")
public class LoginHandler implements GeneralHandler<LoginHandler.LoginResponse> {


    @Autowired
    UsersDao usersDao;

    public void setUsersDao(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    @Override
    public LoginResponse handle(ObjectNode node) throws Exception {

        String email = node.get("email").asText();
        String password = node.get("passwordHash").asText();

        UserRecord user = usersDao.getUserByLoginAndPwd(email, password);
        if (user == null) {
            throw new AndroidServerException("user_not_found");
        }

        return convertToJSON(user);
    }

    private LoginResponse convertToJSON(UserRecord record){
        LoginResponse user = new LoginResponse();
        user.name = record.getName();
        user.surname = record.getSurname();
        user.serverUserId = record.getId();
        return user;
    }

    public class LoginResponse extends Response {
        public int serverUserId;
        public String name;
        public String surname;
    }
}
