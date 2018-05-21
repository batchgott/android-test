package com.evelope.events.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.content.Intent;

import com.evelope.events.database.User_Event;


@Dao
public interface User_EventDao {

    @Insert
    public void insertUser_Event(User_Event user_event);

    @Query("DELETE FROM User_Event WHERE u_id=:userID AND e_id=:eventID ")
    public void deleteUser_Event(Long eventID,Long userID);

    @Query("DELETE FROM User_Event")
    public void nukeTable();

    @Query("SELECT count(*) FROM User_Event ue WHERE ue.u_id=:userID AND ue.e_id=:eventID")
    public Integer User_EventExists(Long userID, Long eventID);
}
