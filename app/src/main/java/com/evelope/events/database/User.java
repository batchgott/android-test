package com.evelope.events.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.RESTRICT;


@Entity(foreignKeys = @ForeignKey(entity = Picture.class,parentColumns = "p_id",childColumns = "p_id",onDelete = RESTRICT),
        indices = {@Index(value = {"p_id"},unique = true)})
public class User {


    //region Getter and Setter
    public Long getU_id() {
        return u_id;
    }

    public void setU_id(Long u_id) {
        this.u_id = u_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getP_id() {
        return p_id;
    }

    public void setP_id(Long p_id) {
        this.p_id = p_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //endregion

    @PrimaryKey
    @ColumnInfo(name = "u_id")
    private Long u_id;


    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "phonenumber")
    private String phoneNumber;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "p_id")
    private Long p_id;
}
