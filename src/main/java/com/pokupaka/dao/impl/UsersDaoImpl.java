package com.pokupaka.dao.impl;

import com.pokupaka.dao.UsersDao;
import com.pokupaka.dao.jooq.tables.records.UserRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.pokupaka.dao.jooq.tables.User.USER;

/**
 * By gekoreed on 9/12/15.
 */
@Repository
public class UsersDaoImpl implements UsersDao {

    @Autowired
    DSLContext context;

    /**
     * Это просто для проверки работоспособности БД
     */
    @Override
    public List<UserRecord> getAllUsers() {
        return context.selectFrom(USER).fetchInto(UserRecord.class);
    }
}
