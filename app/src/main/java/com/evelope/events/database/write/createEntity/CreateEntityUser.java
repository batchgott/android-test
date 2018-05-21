package com.evelope.events.database.write.createEntity;

import android.content.Context;
import android.graphics.Bitmap;

import com.evelope.events.database.AppDatabase;
import com.evelope.events.database.User;
import com.evelope.events.database.write.AWrite;


public class CreateEntityUser extends AWrite {

    private String firstName;
    private String lastName;
    private String description;
    private String email;
    private String phonenumber;
    private String password;

    private Long userID;
    private Long pictureID;

    private Bitmap image;

    private CreateEntityPicture entityPicture;

    //Ohne Server
    public CreateEntityUser(String firstName, String lastName, String description, String email, String phonenumber, String password, Bitmap image, Context context) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.email = email;
        this.phonenumber = phonenumber;
        this.password = password;
        this.image = image;

        this.context=context;

        db= AppDatabase.getAppDatabase(context);

        if (db.userDao().getLast()==null)
            userID=0l;
        else
            userID=db.userDao().getLast().getU_id()+1;

        createPicture();

        alterDatabase();
    }

    //mit Server
    public CreateEntityUser(String firstName, String lastName, String description, String email, String phonenumber, String password, Long userID, Bitmap image, Context context) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.email = email;
        this.phonenumber = phonenumber;
        this.password = password;
        this.userID = userID;
        this.image = image;

        this.context=context;

        db= AppDatabase.getAppDatabase(context);

        createPicture();

        alterDatabase();
    }

    @Override
    protected void alterDatabase() {
        User user =new User();

        user.setU_id(userID);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDescription(description);
        user.setEmail(email);
        user.setPhoneNumber(phonenumber);
        user.setPassword(password);
        user.setP_id(pictureID);

        db.userDao().insertUser(user);
    }

    private void createPicture(){
        entityPicture=new CreateEntityPicture(image,context);
        pictureID=entityPicture.pictureID;
    }
}
