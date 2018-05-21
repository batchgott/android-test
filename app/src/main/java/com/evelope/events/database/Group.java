package com.evelope.events.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;


@Entity(foreignKeys = {
        @ForeignKey(entity = User.class,parentColumns = "u_id",childColumns = "admin_id"),
        @ForeignKey(entity = Picture.class,parentColumns = "p_id",childColumns = "p_id"),
        @ForeignKey(entity = Event.class,parentColumns = "e_id",childColumns = "e_id")
        },
        indices = {
        @Index(value = {"admin_id"}, unique = false),
        @Index(value = {"p_id"}, unique = true),
        @Index(value = {"e_id"}, unique = false)
        })
public class Group {

    //region Getter und Setter
    public Long getG_id() {
        return g_id;
    }

    public void setG_id(Long g_id) {
        this.g_id = g_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(Long admin_id) {
        this.admin_id = admin_id;
    }

    public Long getP_id() {
        return p_id;
    }

    public void setP_id(Long p_id) {
        this.p_id = p_id;
    }

    public Long getE_id() {
        return e_id;
    }

    public void setE_id(Long e_id) {
        this.e_id = e_id;
    }

    public String getMeeting_point() {
        return meeting_point;
    }

    public void setMeeting_point(String meeting_point) {
        this.meeting_point = meeting_point;
    }

    public String getMeeting_time() {
        return meeting_time;
    }

    public void setMeeting_time(String meeting_time) {
        this.meeting_time = meeting_time;
    }

    public Integer getMax_participants() {
        return max_participants;
    }

    public void setMax_participants(Integer max_participants) {
        this.max_participants = max_participants;
    }
    //endregion

    @PrimaryKey
    @ColumnInfo(name = "g_id")
    private Long g_id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "meeting_point")
    private String meeting_point;

    //Todo Create Type Converter for Date 3
    @ColumnInfo(name = "meeting_time")
    private String meeting_time;

    @ColumnInfo(name = "max_participants")
    private Integer max_participants;

    @ColumnInfo(name="admin_id")
    private Long admin_id;

    @ColumnInfo(name = "p_id")
    private Long p_id;

    @ColumnInfo(name = "e_id")
    private Long e_id;

}
