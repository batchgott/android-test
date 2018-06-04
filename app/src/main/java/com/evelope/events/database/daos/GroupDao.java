package com.evelope.events.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.widget.ListView;

import com.evelope.events.database.Event;
import com.evelope.events.database.Group;
import com.evelope.events.database.User;

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

    @Query("SELECT * FROM `Group` g JOIN Categorie_Group cg USING(g_id) WHERE cg.c_id=:catID AND g.e_id=:eventID")
    List<Group> getGroupForCategoryIDandEventID(Long catID,Long eventID);

    @Query("SELECT * FROM `Group` g JOIN User u ON g.admin_id=u.u_id WHERE g.g_id=:groupIDforFragment ")
    User getAdminForGroupID(Long groupIDforFragment);
}
