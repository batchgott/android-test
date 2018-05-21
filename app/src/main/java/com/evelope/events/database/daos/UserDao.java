package com.evelope.events.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.evelope.events.database.User;

import java.util.List;


@Dao
public interface UserDao {

    @Query("SELECT * FROM User u WHERE u.email = :email")
    User getUserByEmail(String email);

    @Query("SELECT * FROM User u WHERE u.u_id=:id")
    User getUserByID(Long id);

    @Insert
    public void insertUser(User user);

    @Query("SELECT * FROM User u ORDER BY u.u_id DESC LIMIT 1")
    public User getLast();

    @Update
    public void updateUser(User user);

    @Query("DELETE FROM User")
    public void nukeTable();

    @Query("SELECT * FROM User u JOIN Group_User gu USING(u_id) WHERE gu.g_id=:groupID")
    List<User> getUserForGroupID(Long groupID);

}
