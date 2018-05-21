package com.evelope.events.database.write.deleteEntity;

import android.content.Context;

import com.evelope.events.database.AppDatabase;
import com.evelope.events.database.write.AWrite;

/**
 * Created by Stefan on 09.04.2018.
 */

public class DeleteEntityUser_Event extends AWrite {

    private Long userID;
    private Long eventID;

    public DeleteEntityUser_Event(Long userID, Long eventID, Context context) {
        this.userID = userID;
        this.eventID = eventID;
        this.context = context;

        db= AppDatabase.getAppDatabase(context);

        alterDatabase();

        decreaseParticipants();
    }

    @Override
    protected void alterDatabase() {
        db.user_eventDao().deleteUser_Event(eventID,userID);
    }

    private void decreaseParticipants(){
        db.eventDao().decreaseParticipantsBy1ByID(eventID);
    }
}
