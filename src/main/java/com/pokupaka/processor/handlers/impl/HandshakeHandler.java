package com.pokupaka.processor.handlers.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pokupaka.dao.UsersDao;
import com.pokupaka.dao.jooq.tables.records.UserRecord;
import com.pokupaka.processor.handlers.GeneralHandler;
import com.pokupaka.processor.handlers.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * By gekoreed on 9/12/15.
 * Запрос для проверки связи с сервером андроид приложения
 */
@Component("handShake")
public class HandshakeHandler implements GeneralHandler<HandshakeHandler.HandShakeResponse> {


    @Autowired
    UsersDao usersDao;

    public void setUsersDao(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    @Override
    public HandShakeResponse handle(ObjectNode node) {
        HandShakeResponse handShakeResponse = new HandShakeResponse();
        handShakeResponse.result = "connected";
        List<UserRecord> allUsers = usersDao.getAllUsers();

        handShakeResponse.usersCount = allUsers.size();
        return handShakeResponse;
    }

    public class HandShakeResponse extends Response {
         public int usersCount;
    }
}
