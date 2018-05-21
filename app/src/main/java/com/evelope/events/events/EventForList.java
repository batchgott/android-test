package com.evelope.events.events;

import android.content.Context;
import android.graphics.Bitmap;

import com.evelope.events.database.Event;
import com.evelope.events.database.Path;
import com.evelope.events.tools.GetPictureFromFile;

public class EventForList {

    private Bitmap bitmap;
    private Event event;
    Context context;

    public Event getEvent() {
        return event;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }


    public EventForList(Event event, Context context) {
        this.event = event;
        this.context=context;

        GetPictureFromFile getPictureFromFile=new GetPictureFromFile(context,event);

        this.bitmap=getPictureFromFile.get();
    }


}
