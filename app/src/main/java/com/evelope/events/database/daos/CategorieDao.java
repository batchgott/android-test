package com.evelope.events.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.evelope.events.database.Categorie;

import java.util.List;

@Dao
public interface CategorieDao {

    @Insert
    void insertCategorie(Categorie categorie);

    @Query("SELECT * FROM Categorie")
    List<Categorie> getAll();

    @Query("DELETE FROM Categorie")
    void nukeTable();

    @Query("SELECT * FROM Categorie c JOIN Categorie_Group cg USING(c_id) WHERE cg.g_id=:groupID")
    List<Categorie> getCategoriesForGroupID(Long groupID);
}
