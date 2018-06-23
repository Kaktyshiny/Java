package com.example.kaktysig.exam.net.request.comics.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Kaktysig on 22.06.18.
 */

public class ComicPrice implements Serializable {

    @SerializedName("type")
    private String type;
    @SerializedName("price")
    private float price;

    public ComicPrice(String type, float price)
    {
        this.type = type;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public float getPrice() {
        return price;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(float price) {
        this.price = price;
    }

}
