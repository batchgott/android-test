package com.evelope.events.database.write.updateEntity;

import android.content.Context;
import android.graphics.Bitmap;

import com.evelope.events.database.AppDatabase;
import com.evelope.events.database.Picture;
import com.evelope.events.database.User;
import com.evelope.events.database.write.AWrite;

/**
 * Created by Stefan on 08.04.2018.
 */

public class UpdateEntityPicture extends AWrite {

    private User user;
    private Picture picture;
    private Bitmap image;

    private UpdateEntityPath entityPath;


    public UpdateEntityPicture(User user, Bitmap image, Context context) {
        this.user=user;
        this.image=image;
        this.context=context;
        db= AppDatabase.getAppDatabase(context);
        picture=db.pictureDao().getPictureByUserID(user.getU_id());

        updatePath();

        alterDatabase();
    }

    @Override
    protected void alterDatabase() {
        db.pictureDao().updatePicture(picture);
    }

    private void updatePath(){
        entityPath=new UpdateEntityPath(picture,image,context);
    }
}
