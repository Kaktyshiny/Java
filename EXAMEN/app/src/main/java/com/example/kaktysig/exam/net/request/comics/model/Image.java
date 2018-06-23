package com.example.kaktysig.exam.net.request.comics.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Kaktysig on 22.06.18.
 */

public class Image implements Serializable {

    @SerializedName("path")
    private String path;
    @SerializedName("extension")
    private String extension;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Override
    public String toString() {
        return "Image{" +
                "path='" + path + '\'' +
                ", extension='" + extension + '\'' +
                '}';
    }

}
