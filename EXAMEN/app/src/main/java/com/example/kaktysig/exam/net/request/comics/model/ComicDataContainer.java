package com.example.kaktysig.exam.net.request.comics.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Kaktysig on 22.06.18.
 */

public class ComicDataContainer implements Serializable {
    @SerializedName("results")
    private ArrayList<Comic> results;

    public ArrayList<Comic> getResults() {
        return results;
    }

    public void setResults(ArrayList<Comic> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "ComicDataContainer{" +
                "results=" + results +
                '}';
    }
}
