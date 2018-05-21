package com.evelope.events.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.RESTRICT;


@Entity(foreignKeys = @ForeignKey(entity = Picture.class,parentColumns = "p_id",childColumns = "p_id",onDelete = RESTRICT),
    indices = {@Index(value = {"p_id"},unique = true)})
public class Event {

    //region Getter und Setter
    public Long getE_id() {
        return e_id;
    }

    public void setE_id(Long e_id) {
        this.e_id = e_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Long getP_id() {
        return p_id;
    }

    public void setP_id(Long p_id) {
        this.p_id = p_id;
    }

    public Integer getParticipants() {
        return participants;
    }

    public void setParticipants(Integer participants) {
        this.participants = participants;
    }
    //endregion

    @PrimaryKey
    @ColumnInfo(name = "e_id")
    private Long e_id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "participants")
    private Integer participants;

    //Todo Create Type Converter for Date 1
    @ColumnInfo(name = "start_date")
    private String startDate;

    //Todo Create Type Converter for Date 2
    @ColumnInfo(name = "end_date")
    private String endDate;

    @ColumnInfo(name = "p_id")
    private Long p_id;
}
