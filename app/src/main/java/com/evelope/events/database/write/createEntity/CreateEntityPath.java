package com.evelope.events.database.write.createEntity;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;

import com.evelope.events.database.AppDatabase;
import com.evelope.events.database.Path;
import com.evelope.events.database.write.AWrite;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import lombok.Getter;



public class CreateEntityPath extends AWrite {

    private String name;

    @Getter
    protected Long pathID;

    private Bitmap image;

    private File file;


    public CreateEntityPath(Bitmap image, Context context){
        this.image=image;

        this.context =context;

        db=AppDatabase.getAppDatabase(context);

        if (db.pathDao().getLast()==null)
            pathID=0l;
        else
            pathID=db.pathDao().getLast().getPa_id()+1;

        name=saveToInternalStorage();

        alterDatabase();

    }

    @Override
    protected void alterDatabase(){
        Path path =new Path();
        path.setPa_id(pathID);
        path.setPath_name(name);

        db.pathDao().insertPath(path);
    }



    private String saveToInternalStorage(){
        ContextWrapper cw = new ContextWrapper(context);

        // path to /data/data/evelope/app_data/imagePaths
        File directory = cw.getDir("imagePath", Context.MODE_PRIVATE);
        // Create imageDir
        file=new File(directory,"pa"+pathID.toString()+".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            // Use the compress method on the BitMap object to write image to the OutputStream
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

}
