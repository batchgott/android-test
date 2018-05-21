package com.evelope.events.database.write.updateEntity;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;

import com.evelope.events.database.AppDatabase;
import com.evelope.events.database.Path;
import com.evelope.events.database.Picture;
import com.evelope.events.database.write.AWrite;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Stefan on 08.04.2018.
 */

public class UpdateEntityPath extends AWrite {

    private Bitmap image;
    private Picture picture;
    private Path path;
    private File file;

    public UpdateEntityPath(Picture picture, Bitmap image, Context context) {
        this.picture=picture;
        this.image = image;
        this.context=context;
        db= AppDatabase.getAppDatabase(context);
        path=db.pathDao().getPathByPictureID(picture.getP_id());

        saveToInternalStorage();

        alterDatabase();
    }

    @Override
    protected void alterDatabase() {
        db.pathDao().updatePath(path);
    }

    private String saveToInternalStorage(){

        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("imagePath", Context.MODE_PRIVATE);

        File fileDelete = new File(directory, "pa"+path.getPa_id().toString()+".jpg");
        fileDelete.delete();


        file=new File(directory,"pa"+path.getPa_id().toString()+".jpg");

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
