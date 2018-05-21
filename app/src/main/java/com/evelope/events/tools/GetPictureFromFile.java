package com.evelope.events.tools;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.evelope.events.database.AppDatabase;
import com.evelope.events.database.Event;
import com.evelope.events.database.Group;
import com.evelope.events.database.Path;
import com.evelope.events.database.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class GetPictureFromFile {

    private Context context;

    public Bitmap get() {
        return bitmap;
    }

    private Bitmap bitmap;
    private Path path;
    private User user;
    private Group group;
    private Event event;



    public GetPictureFromFile(Context context,Event event) {
        this.context = context;
        this.event=event;
        getPicture();
    }
    public GetPictureFromFile(Context context,User user) {
        this.context = context;
        this.user=user;
        getPicture();
    }
    public GetPictureFromFile(Context context,Group group) {
        this.context = context;
        this.group=group;
        getPicture();
    }

    private void getPicture()
    {
        Thread t= new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = AppDatabase.getAppDatabase(context);
                if (event!=null)
                    path = db.pathDao().getPathForEventID(event.getE_id());
                else if (group!=null)
                    path=db.pathDao().getPathForGroupID(group.getG_id());
                else if (user!=null)
                    path =db.pathDao().getPathForUserID(user.getU_id());
            }
        });
        t.start();
        try {
            t.join();
        }
        catch (InterruptedException ex)
        {
        }

        ContextWrapper cw = new ContextWrapper(context);

        try {
            File directory = cw.getDir("imagePath", Context.MODE_PRIVATE);
            File picturePath=new File(directory,"pa"+ path.getPa_id().toString()+".jpg");
            bitmap= BitmapFactory.decodeStream(new FileInputStream(picturePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("GetPicture", "exception", e);
        }

    }

}
