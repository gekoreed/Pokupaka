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

    @Override
    public int registerNewUser(UserRecord userRecord) {
        return context.insertInto(USER)
                .set(userRecord)
                .returning(USER.ID)
                .fetchOne().getId();
    }

    @Override
    public boolean userExists(String email) {
        return !context.selectFrom(USER)
                .where(USER.EMAIL.eq(email))
                .fetch()
                .isEmpty();
    }

    @Override
    public UserRecord getUserByLoginAndPwd(String email, String password) {
        return context.selectFrom(USER)
                .where(USER.EMAIL.eq(email)
                        .and(USER.PASSWORDHASH.eq(password)))
                .fetchOne();
    }


}
