package com.evelope.events.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.RESTRICT;


@Entity(foreignKeys = @ForeignKey(entity = Path.class,parentColumns = "pa_id",childColumns = "pa_id",onDelete = RESTRICT),
        indices = {@Index(value = {"pa_id"}, unique = true)})
public class Picture {

    //region Getter and Setter
    public Long getP_id() {
        return p_id;
    }

    public void setP_id(Long p_id) {
        this.p_id = p_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPa_id() {
        return pa_id;
    }

    public void setPa_id(Long pa_id) {
        this.pa_id = pa_id;
    }
    //endregion

    @PrimaryKey
    @ColumnInfo(name = "p_id")
    private Long p_id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "pa_id")
    private Long pa_id;
}
