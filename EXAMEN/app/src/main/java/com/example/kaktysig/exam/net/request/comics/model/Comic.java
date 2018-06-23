package com.example.kaktysig.exam.net.request.comics.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Kaktysig on 22.06.18.
 */

public class Comic implements Serializable {

    @SerializedName("title")
    private String title;
    @SerializedName("thumbnail")
    private Image thumbnail;
    @SerializedName("type")
    private String type;
    @SerializedName("price")
    private ComicPrice price;

    private String description;

    private Integer id;

    public void setPk(Integer id) {
        this.id = id;
    }

    public Integer getPk() {

        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Image getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Image thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Comic(Integer id, String title, Image thumbnail, String description) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Comic{" +
                "title='" + title + '\'' +
                ", thumbnail=" + thumbnail +
                ", price=" + price +
                '}';
    }
}
