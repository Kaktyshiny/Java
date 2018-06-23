package com.example.kaktysig.exam.net.request.characters.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Kaktysig on 22.06.18.
 */

public class CharacterDataContainer implements Serializable {
    @SerializedName("results")
    private ArrayList<Character> results;

    public ArrayList<Character> getResults() {
        return results;
    }

    public void setResults(ArrayList<Character> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "CharacterDataContainer{" +
                "results=" + results +
                '}';
    }
}
