package com.evelope.events.database.write.createEntity;

import android.content.Context;
import android.graphics.Bitmap;

import com.evelope.events.database.AppDatabase;
import com.evelope.events.database.Group;
import com.evelope.events.database.write.AWrite;

public class CreateEntityGroup extends AWrite {

    private String name;
    private String description;
    private Integer maxParticipants;
    private String meetingPoint;
    private String meetingTime;

    public Long getGroupID() {
        return groupID;
    }
    private Long groupID;

    private Long eventID;
    private Long pictureID;
    private Long adminID;

    private Boolean _1drink,_2dance,_3sing,_4smoke,_5talk,_6play;

    private Bitmap image;

    private CreateEntityPicture entityPicture;
    private CreateEntityCategorie_Group entityCategorie_group;
    private CreateEntityGroup_User entityGroup_user;


    public CreateEntityGroup(String name, String description, String maxParticipants, String meetingPoint, String meetingTime, Long eventID, Long adminID, Boolean _1drink, Boolean _2dance, Boolean _3sing, Boolean _4smoke, Boolean _5talk, Boolean _6play, Bitmap image, Context context) {
        this.name = name;
        this.description = description;
        this.maxParticipants = Integer.parseInt(maxParticipants,10);
        this.meetingPoint = meetingPoint;
        this.meetingTime = meetingTime;
        this.eventID = eventID;
        this.adminID = adminID;
        this._1drink = _1drink;
        this._2dance = _2dance;
        this._3sing = _3sing;
        this._4smoke = _4smoke;
        this._5talk = _5talk;
        this._6play = _6play;
        this.image = image;
        this.context=context;
        db= AppDatabase.getAppDatabase(context);

        if (db.groupDao().getLast()==null)
            groupID=0l;
        else
            groupID=db.groupDao().getLast().getG_id()+1;

        createPicture();
        alterDatabase();
        createCategories();
        createGroup_User();
    }

    @Override
    protected void alterDatabase() {
        Group group=new Group();
        group.setG_id(groupID);
        group.setName(name);
        group.setDescription(description);
        group.setMax_participants(maxParticipants);
        group.setMeeting_time(meetingTime);
        group.setMeeting_point(meetingPoint);
        group.setAdmin_id(adminID);
        group.setE_id(eventID);
        group.setP_id(pictureID);

        db.groupDao().insertGroup(group);
    }

    private void createPicture(){
        entityPicture=new CreateEntityPicture(image,context);
        pictureID=entityPicture.pictureID;
    }

    private void createCategories(){
        if (_1drink)
            entityCategorie_group=new CreateEntityCategorie_Group(0l,groupID,context);
        if (_2dance)
            entityCategorie_group=new CreateEntityCategorie_Group(1l,groupID,context);
        if (_3sing)
            entityCategorie_group=new CreateEntityCategorie_Group(2l,groupID,context);
        if (_4smoke)
            entityCategorie_group=new CreateEntityCategorie_Group(3l,groupID,context);
        if (_5talk)
            entityCategorie_group=new CreateEntityCategorie_Group(4l,groupID,context);
        if (_6play)
            entityCategorie_group=new CreateEntityCategorie_Group(5l,groupID,context);
    }

    private void createGroup_User(){
        entityGroup_user=new CreateEntityGroup_User(groupID,adminID,context);
    }
}
