package com.evelope.events.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.evelope.events.database.daos.CategorieDao;
import com.evelope.events.database.daos.Categorie_GroupDao;
import com.evelope.events.database.daos.EventDao;
import com.evelope.events.database.daos.GroupDao;
import com.evelope.events.database.daos.Group_UserDao;
import com.evelope.events.database.daos.PathDao;
import com.evelope.events.database.daos.PictureDao;
import com.evelope.events.database.daos.UserDao;
import com.evelope.events.database.daos.User_EventDao;


//--------------------------------------------------------
// https://www.youtube.com/watch?v=LCOKWgHdBvE
//--------------------------------------------------------

@Database(entities = {User.class,Picture.class,Path.class,Group_User.class,Group.class,Event.class, Categorie.class,Categorie_Group.class,User_Event.class},
        version = 12,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract EventDao eventDao();
    public abstract PictureDao pictureDao();
    public abstract PathDao pathDao();
    public abstract User_EventDao user_eventDao();
    public abstract GroupDao groupDao();
    public abstract CategorieDao categorieDao();
    public abstract Categorie_GroupDao categorie_groupDao();
    public abstract Group_UserDao group_userDao();

    private static AppDatabase INSTANCE;


    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "evelope-database")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


    public static void Clear(Context context){
        AppDatabase db=AppDatabase.getAppDatabase(context);
        db.eventDao().nukeTable();
        db.userDao().nukeTable();
        db.user_eventDao().nukeTable();
        db.pictureDao().nukeTable();
        db.pathDao().nukeTable();
        //TODO die anderen hinzufügen
    }
}
