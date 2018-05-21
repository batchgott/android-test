package com.evelope.events.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;


@Entity(foreignKeys = {@ForeignKey(entity = Categorie.class,parentColumns = "c_id",childColumns = "c_id"),
@ForeignKey(entity = Group.class,parentColumns = "g_id",childColumns = "g_id")},
        primaryKeys = {"c_id","g_id"},indices = {@Index(value = "c_id"),@Index(value = "g_id")})
public class Categorie_Group {

    //region Getter und Setter
    public Long getC_id() {
        return c_id;
    }

    public void setC_id(Long c_id) {
        this.c_id = c_id;
    }

    public Long getG_id() {
        return g_id;
    }

    public void setG_id(Long g_id) {
        this.g_id = g_id;
    }
    //endregion

    @NonNull
    @ColumnInfo(name = "c_id")
    private Long c_id;

    @NonNull
    @ColumnInfo(name = "g_id")
    private Long g_id;

}
