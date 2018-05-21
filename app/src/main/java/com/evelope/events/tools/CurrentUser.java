package com.evelope.events.tools;

import android.util.Log;

import com.evelope.events.MyApplication;
import com.evelope.events.database.AppDatabase;
import com.evelope.events.database.User;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;



public class CurrentUser {

    private static User user;
    private static Long userID;

    public static void setNull(){
        user =null;
    }

    public static User get(){
        if (user ==null)
        getCurrentUser();
        return user;
    }

    //TODO Delete CurrentUser when User logs out
    //TODO Check if someone is logged in, if not throw exception
    private static void getCurrentUser(){
        FileInputStream fis;
        String getFileText;
        try {
            fis = MyApplication.getContext().openFileInput("currentUser");
            InputStreamReader isr=new InputStreamReader(fis);
            BufferedReader br=new BufferedReader(isr);
            StringBuilder sb=new StringBuilder();
            while ((getFileText=br.readLine())!=null){
                sb.append(getFileText);
            }
            fis.close();
            userID=Long.parseLong(sb.toString(),10);
        } catch (Exception e) {
            Log.e("getCurrentUser: ",Log.getStackTraceString(e));
        }
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db= AppDatabase.getAppDatabase(MyApplication.getContext());
                user =db.userDao().getUserByID(userID);
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
    }
}
