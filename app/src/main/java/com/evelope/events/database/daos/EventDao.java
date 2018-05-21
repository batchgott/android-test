package com.evelope.events.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;

import com.evelope.events.database.Event;

import java.util.List;


@Dao
public interface EventDao {

    @Insert
    void insertEvent(Event event);

    @Query("SELECT * FROM Event e ORDER BY e.e_id DESC LIMIT 1")
    Event getLast();

    @Query("SELECT * FROM Event e WHERE e.e_id=:id")
    Event getEventByID(Long id);

    @Query("UPDATE Event SET participants=(participants+1) WHERE e_id = :id")
    void increaseParticipantsBy1ByID(Long id);

    @Query("UPDATE Event SET participants=(participants-1) WHERE e_id = :id")
    void decreaseParticipantsBy1ByID(Long id);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT e.e_id FROM User_Event ue JOIN Event e WHERE e.e_id=ue.e_id AND ue.u_id=:userID")
    Long[] getEventIDsByUserID(Long userID);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM User_Event ue JOIN Event e WHERE e.e_id=ue.e_id AND ue.u_id=:userID")
    List<Event> getEventByUserID(Long userID);

    @Query("SELECT * FROM Event e WHERE e.e_id NOT IN(:eventIDs)")
    List<Event> getAllEventsExceptOf(Long[] eventIDs);

    @Query("SELECT e.name FROM EVENT e JOIN `Group` g USING(e_id) WHERE g.g_id=:groupID")
    String getEventNameByGroupID(Long groupID);

    @Query("SELECT e.e_id FROM EVENT e JOIN `Group` g USING(e_id) WHERE g.g_id=:groupID")
    Long getEventIDByGroupID(Long groupID);

    @Query("DELETE FROM Event")
    public void nukeTable();
}
