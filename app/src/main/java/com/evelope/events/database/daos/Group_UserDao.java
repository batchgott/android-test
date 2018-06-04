package com.evelope.events.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.evelope.events.database.Group_User;

@Dao
public interface Group_UserDao {

    @Insert
    void insertGroup_User(Group_User group_user);

    @Query("SELECT count(*) FROM GROUP_USER gu WHERE gu.g_id=:groupID")
    int countParticipantsForGroupID(Long groupID);

    @Delete
    void deleteUserFromGroup(Group_User gu);
}
