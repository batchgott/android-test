package com.evelope.events.database.daos;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.evelope.events.database.Categorie_Group;

import java.util.List;

@Dao
public interface Categorie_GroupDao {



    @Insert
    void insertCategorie_Group(Categorie_Group categorie_group);


}
