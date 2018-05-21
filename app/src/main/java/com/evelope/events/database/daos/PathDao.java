package com.evelope.events.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;
import android.arch.persistence.room.Update;

import com.evelope.events.database.Path;


@Dao
public interface PathDao {

    @Insert
    public void insertPath(Path path);

    @Query("SELECT * FROM Path p ORDER BY p.pa_id DESC LIMIT 1")
    public Path getLast();

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM Path pa JOIN Picture pi USING(pa_id) JOIN Event e USING(p_id) WHERE e.e_id==:eventID")
    public Path getPathForEventID(Long eventID);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM Path pa JOIN Picture pi USING(pa_id) JOIN User u USING(p_id) WHERE u.u_id==:userID")
    public Path getPathForUserID(Long userID);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM Path pa JOIN Picture pi USING(pa_id) JOIN `Group` g USING(p_id) WHERE g.g_id==:groupID")
    public Path getPathForGroupID(Long groupID);

    @Update
    public void updatePath(Path path);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM Path pa JOIN Picture p USING(pa_id) WHERE p.p_id=:pictureID")
    public Path getPathByPictureID(Long pictureID);

    @Query("DELETE FROM Path")
    public void nukeTable();
}
