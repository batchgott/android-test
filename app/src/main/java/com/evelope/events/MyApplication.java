package com.evelope.events;

import android.app.Application;


public class MyApplication extends Application {

    private static MyApplication Context;

    @Override
    public void onCreate() {
        super.onCreate();
        Context = this;
    }

    public static MyApplication getContext() {
        return Context;
    }

}
