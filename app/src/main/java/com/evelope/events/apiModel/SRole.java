package com.evelope.events.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SRole {

    @SerializedName("roleId")
    @Expose
    private String roleId;
    @SerializedName("role")
    @Expose
    private String role;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("roleId", roleId).append("role", role).toString();
    }

}
