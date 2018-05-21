package com.evelope.events.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;



@Entity
public class Categorie {

    //region Getter und Setter
    public Long getC_id() {
        return c_id;
    }

    public void setC_id(Long c_id) {
        this.c_id = c_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //endregion

    @PrimaryKey
    private Long c_id;

    @ColumnInfo(name = "name")
    private String name;

    public Categorie(Long c_id, String name) {
        this.c_id = c_id;
        this.name = name;
    }
}
