package com.pokupaka.dao;

import com.pokupaka.dao.jooq.tables.records.UserRecord;

import java.util.List;

/**
 * By gekoreed on 9/12/15.
 */

public interface UsersDao {
    List<UserRecord> getAllUsers();

    /**
     * Регистрация нового юзера в системе
     * @param userRecord запись о юзере
     * @return айди в БД
     */
    int registerNewUser(UserRecord userRecord);


    /**
     * Проверим существует ли пользователь с таким же мылом
     * @param email мыло
     * @return да - нет
     */
    boolean userExists(String email);
}
