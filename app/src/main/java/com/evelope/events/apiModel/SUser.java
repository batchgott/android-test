package com.evelope.events.apiModel;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SUser implements Serializable{

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phonenumber")
    @Expose
    private String phonenumber;
    @SerializedName("picturepath")
    @Expose
    private SPicturepath picturepath;
    @SerializedName("roles")
    @Expose
    private Set<SRole> roles = null;


    public SUser(String lastname, String firstname, String username, String password, String description, String email, String phonenumber, SPicturepath picturepath) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.username = username;
        this.password = password;
        this.description = description;
        this.email = email;
        this.phonenumber = phonenumber;
        this.picturepath = picturepath;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("lastname", lastname).append("firstname", firstname).append("username", username).append("password", password).append("description", description).append("email", email).append("phonenumber", phonenumber).append("picturepath", picturepath).append("roles", roles).toString();
    }


    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public SPicturepath getPicturepath() {
        return picturepath;
    }

    public void setPicturepath(SPicturepath picturepath) {
        this.picturepath = picturepath;
    }

    public Set<SRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<SRole> roles) {
        this.roles = roles;
    }

}