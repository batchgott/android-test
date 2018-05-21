package com.evelope.events.database.write.createEntity;

import android.content.Context;
import android.graphics.Bitmap;

import com.evelope.events.database.AppDatabase;
import com.evelope.events.database.Picture;
import com.evelope.events.database.write.AWrite;

import lombok.Getter;



public class CreateEntityPicture extends AWrite {

    private String name;

    @Getter
    protected Long pictureID;

    private Long pathID;

    private Bitmap image;

    private CreateEntityPath entityPath;

    public CreateEntityPicture(Bitmap image, Context context){
        this.image=image;

        this.context =context;

        db=AppDatabase.getAppDatabase(context);

        if (db.pictureDao().getLast()==null)
            pictureID=0l;
        else
            pictureID=db.pictureDao().getLast().getP_id()+1;

        name="p"+pictureID.toString();

        createPath();

        alterDatabase();
    }

    @Override
    protected void alterDatabase(){
        Picture picture =new Picture();

        picture.setName(name);
        picture.setP_id(pictureID);
        picture.setPa_id(pathID);

        db.pictureDao().insertPicture(picture);
    }

    private void createPath(){

        entityPath=new CreateEntityPath(image,context);

        pathID=entityPath.pathID;

    }
}
