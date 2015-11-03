package com.selfach.processor.handlers.Devs;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.selfach.annotations.Dev;
import com.selfach.dao.UsersDao;
import com.selfach.processor.handlers.GeneralHandler;
import com.selfach.processor.handlers.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * By gekoreed on 9/15/15.
 */
@Component("remover")
@Dev
public class RemoveInfo implements GeneralHandler<RemoveInfo.Resp> {

    @Autowired
    UsersDao usersDao;

    @Override
    public Resp handle(ObjectNode node) throws Exception {

        int id = node.get("id").asInt();
        usersDao.deleteUser(id);
        return new Resp();
    }

    public class Resp extends Response {
    }
}
