package com.evelope.events.database.write;

import android.content.Context;

import com.evelope.events.database.AppDatabase;



public abstract class AWrite {

    protected AppDatabase db;
    protected Context context;

    protected abstract void alterDatabase();
}
