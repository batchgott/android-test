package com.evelope.events.database.write.updateEntity;

import android.content.Context;
import android.graphics.Bitmap;

import com.evelope.events.database.AppDatabase;
import com.evelope.events.database.User;
import com.evelope.events.database.write.AWrite;


public class UpdateEntityUser extends AWrite {

    User oldUser;

    private String firstName;
    private String lastName;
    private String description;
    private String email;
    private String phonenumber;
    private String password;

    private Long userID;
    private Long pictureID;

    private Bitmap image;

    private UpdateEntityPicture updatePicture;


    public UpdateEntityUser(User oldUser, String firstName, String lastName, String description, String email, String phonenumber, String password, Bitmap image, Context context) {
        this.oldUser=oldUser;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.email = email;
        this.phonenumber = phonenumber;
        this.password = password;
        this.image = image;

        this.context=context;

        db=AppDatabase.getAppDatabase(context);

        updatePicture();

        alterDatabase();
    }

    @Override
    protected void alterDatabase() {
        db= AppDatabase.getAppDatabase(context);
        oldUser.setFirstName(firstName);
        oldUser.setLastName(lastName);
        oldUser.setDescription(description);
        oldUser.setEmail(email);
        oldUser.setPassword(password);
        oldUser.setPhoneNumber(phonenumber);
        db.userDao().updateUser(oldUser);
    }
    private void updatePicture(){
        updatePicture=new UpdateEntityPicture(oldUser,image,context);
    }
}
