package com.evelope.events.tools.inputCheck;

import android.content.Context;

import com.evelope.events.database.AppDatabase;
import com.evelope.events.database.User;


public class LoginInputCheck extends AInputCheck {

    private String email, password;
    private Context context;

    public User getUser() {
        return user;
    }

    private User user;

    public LoginInputCheck(String email, String password, Context context) {
        this.email = email;
        this.password = password;
        this.context=context;
    }

    @Override
    public Boolean Check() {
        Thread t=new Thread(new Runnable() {
                @Override
                public void run() {
                    AppDatabase db= AppDatabase.getAppDatabase(context);
                    user =db.userDao().getUserByEmail(email);
                }
            });
            t.start();
            try
            {
                t.join();
            }
            catch(InterruptedException ex)
            {
            }
            if (user !=null){
                if (!user.getPassword().matches(password)) {
                    errorMessage = "Falsches Passwort";
                    return false;
                }
            }
            else
            {
                errorMessage="Es gibt keinen User mit dieser E-Mail Adresse";
                return false;
            }
        return true;
    }

}
