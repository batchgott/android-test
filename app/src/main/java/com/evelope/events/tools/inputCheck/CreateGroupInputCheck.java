package com.evelope.events.tools.inputCheck;

import android.graphics.Bitmap;

public class CreateGroupInputCheck extends AInputCheck {

    String name;
    String description;
    String maxParticipants;
    String meetingPoint;
    String meetingTime;
    Bitmap picture;


    public CreateGroupInputCheck(String name, String description, String maxParticipants, String meetingPoint, String meetingTime, Bitmap picture) {
        this.name = name;
        this.description = description;
        this.maxParticipants = maxParticipants;
        this.meetingPoint = meetingPoint;
        this.meetingTime = meetingTime;
        this.picture = picture;
        }

    @Override
    public Boolean Check() {

        if (name==null||name.equals("")){
            errorMessage="Es muss ein Gruppenname angegeben werden";
            return false;
        }
        if (maxParticipants==null||maxParticipants.equals("")){
            errorMessage="Es muss die maximale Teilnehmeranzahl angegeben werden";
            return false;
        }
        if (Integer.parseInt(maxParticipants,10)<=1){
            errorMessage="Die maximale Teilnehmeranzahl muss größer als 1 sein";
            return false;
        }
        if (meetingPoint==null||meetingPoint.equals("")){
            errorMessage="Der Treffpunkt muss angegeben werden";
            return false;
        }
        if (meetingTime==null||meetingTime.equals("")){
            errorMessage="Die Treffzeit mzss angegeben werden";
            return false;
        }
        if (picture==null){
            errorMessage="Es muss ein Bild ausgewählt werden";
            return false;
        }

        return true;
    }
}
