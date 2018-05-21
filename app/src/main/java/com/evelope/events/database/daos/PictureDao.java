package com.evelope.events.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;
import android.arch.persistence.room.Update;

import com.evelope.events.database.Picture;


@Dao
public interface PictureDao {

    @Insert
    public void insertPicture(Picture picture);

    @Query("SELECT * FROM Picture p ORDER BY p.p_id DESC LIMIT 1")
    public Picture getLast();

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM Picture p JOIN User u USING(p_id) WHERE u.u_id=:userID")
    public Picture getPictureByUserID(Long userID);

    @Update
    public void updatePicture(Picture picture);

    @Query("DELETE FROM Picture")
    public void nukeTable();
}
