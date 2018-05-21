package com.evelope.events.groups;

import android.content.Context;
import android.graphics.Bitmap;

import com.evelope.events.database.AppDatabase;
import com.evelope.events.database.Group;
import com.evelope.events.tools.GetPictureFromFile;

class GroupForList {

    private Bitmap bitmap;
    private Group group;
    private String eventName;



    private Long eventID;
    Context context;

    public String getEventName() {
        return eventName;
    }
    public Group getGroup() {
        return group;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }
    public Long getEventID() {
        return eventID;
    }


    public GroupForList(Group group, Context context) {
        this.group=group;
        this.context=context;

        GetPictureFromFile getPictureFromFile=new GetPictureFromFile(context,group);

        this.bitmap=getPictureFromFile.get();
        eventName = AppDatabase.getAppDatabase(context).eventDao().getEventNameByGroupID(group.getG_id());
        eventID=AppDatabase.getAppDatabase(context).eventDao().getEventIDByGroupID(group.getG_id());

    }




}
