package com.selfach.dao;

import com.selfach.dao.jooq.tables.records.UserRecord;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

/**
 * By gekoreed on 9/12/15.
 */

public interface UsersDao {

    /**
     * Запрос для взятия всех пользователей системы
     * @return список пользователей
     */
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
    Optional<UserRecord> userExists(String email);

    /**
     * При логине пользователя взять его запись по паре email-passwordHash
     * @param email email
     * @param password passwordHash
     * @return запись о пользователе в БД
     */
    UserRecord getUserByLoginAndPwd(String email, String password);

    /**
     * Взять запись о пользователе по его id
     * @param userId id пользователя
     * @return пользователь
     */
    UserRecord getUserById(int userId);

    /**
     * Сохранить пользователя
     * @param user пользователь
     */
    void save(UserRecord user);

    void setDataSource(DataSource dataSource);

    void deleteUser(int id);

    void update(UserRecord user);

    void updateLang(int userId, String lang);

    String getLang(int userId);
}
