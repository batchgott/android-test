package com.evelope.events.apiModel;

import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.util.Date;

public class SGroup implements Serializable {

    public Long id;

    public String name;

    public String description;

    public SUser admin;

    public SPicturepath picturepath;

    public SEvent event;

    public Integer maxMembers;

    public Date time;

    private String meetingPoint;


    public SGroup(Long id, String name, String description, SUser admin, SPicturepath picturepath, SEvent event, Integer maxMembers, Date time, String meetingPoint) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.admin = admin;
        this.picturepath = picturepath;
        this.event = event;
        this.maxMembers = maxMembers;
        this.time = time;
        this.meetingPoint = meetingPoint;
    }

    public SGroup(String name, String description, SUser admin, SPicturepath picturepath, SEvent event, Integer maxMembers, Date time, String meetingPoint) {
        this.name = name;
        this.description = description;
        this.admin = admin;
        this.picturepath = picturepath;
        this.event = event;
        this.maxMembers = maxMembers;
        this.time = time;
        this.meetingPoint = meetingPoint;
    }
}
