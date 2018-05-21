package com.evelope.events.database.write.createEntity;

import android.content.Context;

import com.evelope.events.database.AppDatabase;
import com.evelope.events.database.User_Event;
import com.evelope.events.database.write.AWrite;

/**
 * Created by Stefan on 31.03.2018.
 */

public class CreateEntityUser_Event extends AWrite {

    private Long userID;
    private Long eventID;

    public CreateEntityUser_Event(Long userID, Long eventID, Context context) {
        this.userID = userID;
        this.eventID = eventID;
        this.context = context;

        db= AppDatabase.getAppDatabase(context);

        alterDatabase();

        increaseParticipants();
    }

    @Override
    protected void alterDatabase() {
        User_Event user_event=new User_Event();
        user_event.setE_id(eventID);
        user_event.setU_id(userID);

        db.user_eventDao().insertUser_Event(user_event);
    }

    private void increaseParticipants(){
    //db.eventDao().increaseParticipantsBy1ByID(eventID);
    }
}
