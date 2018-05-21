package com.evelope.events.tools.inputCheck;

import android.graphics.Bitmap;

public class CreateEventInputCheck extends AInputCheck {

    private String name,description,location,startDate,endDate;
    Bitmap picture;

    public CreateEventInputCheck(String name, String description, String location, String startDate, String endDate, Bitmap picture) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.picture = picture;
    }

    @Override
    public Boolean Check() {

        if (name==null||name.matches("")){
            errorMessage="Name wurde nicht eingegeben";
            return false;
        }
        if (location==null||location.matches("")){
            errorMessage="Ort wurde nicht eingegeben";
            return false;
        }
        if (startDate==null||startDate.matches("")){
            errorMessage="Start Datum wurde nicht eingegeben";
            return false;
        }
        if (endDate==null||endDate.matches("")){
            errorMessage="End Datum wurde nicht eingegeben";
            return false;
        }
        if (picture==null) {
            errorMessage = "Es wurde kein Bild ausgewählt";
            return false;
        }
        return true;
    }
}
