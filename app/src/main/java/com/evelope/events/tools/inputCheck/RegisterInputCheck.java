package com.evelope.events.tools.inputCheck;

import android.graphics.Bitmap;

public class RegisterInputCheck extends AInputCheck{

    private String firstName,lastName,email,passwort,passwortConfirm,description,phoneNumber;
    private Bitmap picture;


    public RegisterInputCheck(String firstName, String lastName, String email, String passwort, String passwortConfirm, String description, String phoneNumber, Bitmap picture) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwort = passwort;
        this.passwortConfirm = passwortConfirm;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.picture=picture;
    }

    //TODO Check if User already exists
    @Override
    public Boolean Check(){
        if (firstName==null || firstName.matches("")) {
            errorMessage = "Vorname wurde nicht eingegeben";
            return false;
        }
        if (lastName==null || lastName.matches("")) {
            errorMessage ="Nachname wurde nicht eingegeben";
            return false;
        }
        if (email==null || email.matches("")) {
            errorMessage= "E-Mail wurde nicht eingegeben";
            return false;
        }
        if (passwort==null || passwort.matches("")) {
            errorMessage= "Passwort wurde nicht eingegeben";
            return false;
        }
        if (phoneNumber==null || phoneNumber.matches("")) {
            errorMessage= "Telephonnummer wurde nicht eingegeben";
            return false;
        }
        if (!passwort.matches(passwortConfirm)) {
            errorMessage= "PASSWORT und PASSWORT BESTÄTIGEN müssen übereinstimmen";
            return false;
        }
        if (picture==null){
            errorMessage="Es wurde kein Bild ausgewählt";
            return false;
        }
        errorMessage= "User Input korrekt";
        return true;
    }
}
