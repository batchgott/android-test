package com.evelope.events.database.write.createEntity;

import android.content.Context;
import android.graphics.Bitmap;

import com.evelope.events.database.AppDatabase;
import com.evelope.events.database.Event;
import com.evelope.events.database.write.AWrite;


public class CreateEntityEvent extends AWrite {

    private String name;
    private String description;
    private String location;
    private String startDate;
    private String endDate;

    private Integer participants;

    public Long getEventID() {
        return eventID;
    }

    private Long eventID;
    private Long pictureID;

    private Bitmap image;

    private CreateEntityPicture entityPicture;

    //Ohne Server
    public CreateEntityEvent(String name, String description, String location, String startDate, String endDate, Bitmap image, Context context){
        this.participants=0;
        this.name=name;
        this.description=description;
        this.location=location;
        this.startDate=startDate;
        this.endDate=endDate;
        this.image=image;

        this.context =context;

        db=AppDatabase.getAppDatabase(context);

        if (db.eventDao().getLast()==null)
            eventID=0l;
        else
            eventID=db.eventDao().getLast().getE_id()+1;

        createPicture();

        alterDatabase();
    }

    //mit Server
    public CreateEntityEvent(String name, String description, String location, String startDate, String endDate, Integer participants, Long eventID, Bitmap image, Context context) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.participants = participants;
        this.eventID = eventID;
        this.image = image;

        this.context=context;

        db=AppDatabase.getAppDatabase(context);

        createPicture();

        alterDatabase();
    }

    @Override
    protected void alterDatabase() {
        Event event =new Event();

        event.setE_id(eventID);
        event.setName(name);
        event.setDescription(description);
        event.setLocation(location);
        event.setParticipants(participants);
        event.setStartDate(startDate);
        event.setEndDate(endDate);
        event.setP_id(pictureID);

        db.eventDao().insertEvent(event);
    }

    private void createPicture(){
        entityPicture=new CreateEntityPicture(image,context);
        pictureID=entityPicture.pictureID;
    }
}
