package com.evelope.events.findGroup;

import android.content.Context;
import android.graphics.Bitmap;

import com.evelope.events.database.User;
import com.evelope.events.tools.GetPictureFromFile;

class UserForList {

    private Bitmap bitmap;
    private User user;
    Context context;

    public User getUser() {return user;}
    public Bitmap getBitmap() {return bitmap;}

    public UserForList(User user, Context context) {
        this.user = user;
        this.context = context;

        GetPictureFromFile gpff=new GetPictureFromFile(context,user);

        this.bitmap=gpff.get();
    }
}
