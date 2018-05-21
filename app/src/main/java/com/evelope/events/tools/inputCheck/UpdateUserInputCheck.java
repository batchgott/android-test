package com.evelope.events.tools.inputCheck;

import android.graphics.Bitmap;

import com.evelope.events.tools.CurrentUser;

/**
 * Created by Stefan on 08.04.2018.
 */

public class UpdateUserInputCheck extends AInputCheck {

    private String firstName,lastName,email,description,phoneNumber,oldPassword,newPassword,newPasswordRepeate;
    private Bitmap picture;

    public UpdateUserInputCheck(String firstName, String lastName, String description, String phoneNumber, Bitmap picture, String oldPassword, String newPassword, String newPasswordRepeate ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.picture=picture;
        this.oldPassword=oldPassword;
        this.newPassword=newPassword;
        this.newPasswordRepeate=newPasswordRepeate;
    }

    @Override
    public Boolean Check() {
        if (firstName==null || firstName.matches("")) {
            errorMessage = "Vorname wurde nicht eingegeben";
            return false;
        }
        if (lastName==null || lastName.matches("")) {
            errorMessage ="Nachname wurde nicht eingegeben";
            return false;
        }
        if (phoneNumber==null || phoneNumber.matches("")) {
            errorMessage= "Telephonnummer wurde nicht eingegeben";
            return false;
        }
        //TODO funktioniert noch nicht wie gewollt
        if (!oldPassword.matches("")&&!newPassword.matches("")&&!newPasswordRepeate.matches(""))
        {
            if (!oldPassword.matches(CurrentUser.get().getPassword().toString())){
                errorMessage="Altes Passwort stimmt nicht";
                return false;
            }
            if (oldPassword.matches(newPassword)){
                errorMessage="Das neue Passwort darf nicht mit dem alten übereinstimmten";
                return false;
            }
            if (!newPassword.matches(newPasswordRepeate)){
                errorMessage="Die Passwörter stimmen nicht überein";
                return false;
            }
        }
        if (picture==null){
            errorMessage="Es wurde kein Bild ausgewählt";
            return false;
        }

        return true;
    }
}
