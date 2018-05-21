package com.evelope.events.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.widget.ListView;

import com.evelope.events.database.Event;
import com.evelope.events.database.Group;

import java.util.List;

@Dao
public interface GroupDao {

    @Insert
    void insertGroup(Group group);

    @Query("SELECT * FROM `Group` g ORDER BY g.g_id DESC LIMIT 1")
    Group getLast();

    @Query("SELECT * FROM Group_User gu JOIN `GROUP` g USING(g_id) WHERE gu.u_id=:userID")
    List<Group> getGroupsByUserID(Long userID);

    @Query("SELECT * FROM `Group` g WHERE g.g_id=:groupID")
    Group getGroupByID(Long groupID);
}
