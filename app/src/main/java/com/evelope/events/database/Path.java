package com.evelope.events.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity
public class Path {

    //region Getter and Setter
    public Long getPa_id() {
        return pa_id;
    }

    public void setPa_id(Long pa_id) {
        this.pa_id = pa_id;
    }

    public String getPath_name() {
        return path_name;
    }

    public void setPath_name(String path_name) {
        this.path_name = path_name;
    }
    //endregion

    @PrimaryKey
    @ColumnInfo(name = "pa_id")
    private Long pa_id;

    @ColumnInfo(name = "path_name")
    private String path_name;
}
