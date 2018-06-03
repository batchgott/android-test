package com.evelope.events.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SPicturepath {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("image")
    @Expose
    private byte[] image;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("path", path).append("image", image).toString();
    }


    public SPicturepath(byte[] image) {
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}
