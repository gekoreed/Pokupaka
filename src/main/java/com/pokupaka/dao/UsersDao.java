package com.pokupaka.dao;

import com.pokupaka.dao.jooq.tables.records.UserRecord;

import java.util.List;

/**
 * By gekoreed on 9/12/15.
 */

public interface UsersDao {
    List<UserRecord> getAllUsers();
}
