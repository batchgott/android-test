package com.evelope.events.tools;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.evelope.events.MyApplication;
import com.evelope.events.database.AppDatabase;
import com.evelope.events.database.User;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;



public class CurrentUser {

    private static User user;

    public static Long getUserID() {
        getCurrentUser();
        return userID;
    }

    private static Long userID;

    public static void setNull(){
        user =null;
        userID=null;
    }

    public static User get(){
        if (user ==null)
        getCurrentUser();
        return user;
    }

    public static void Logout(){
        user=null;
        userID=-10l;
        FileInputStream fis;
        String getFileText;
        FileOutputStream fos;
        try {
            fos = MyApplication.getContext().openFileOutput("currentUser", Context.MODE_PRIVATE);
            fos.write("-10".getBytes());
            fos.close();
        } catch (Exception e) {
            Log.e("Logout: ",Log.getStackTraceString(e));
        }
    }

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
            userID=-10l;
        }
        if (userID!=-10) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    AppDatabase db = AppDatabase.getAppDatabase(MyApplication.getContext());
                    user = db.userDao().getUserByID(userID);
                }
            });
            t.start();
            try {
                t.join();
            } catch (InterruptedException ex) {
            }
        }
    }
}
