package com.example.kaktysig.laba_2;

import java.io.Serializable;

/**
 * Created by Kaktysig on 28.03.18.
 */

public class Questions implements Serializable {

    private String text;
    private String variant_1;
    private String variant_2;
    private String variant_3;
    private String right_answer;


    public Questions(String text, String variant_1, String variant_2, String variant_3, String right_answer) {
        this.text = text;
        this.variant_1 = variant_1;
        this.variant_2 = variant_2;
        this.variant_3 = variant_3;
        this.right_answer = right_answer;
    }

    public String getText() {
        return text;
    }

    public String getVariant_1() {
        return variant_1;
    }

    public String getVariant_2() {
        return variant_2;
    }

    public String getVariant_3() {
        return variant_3;
    }

    public String getRight_aswer() {
        return right_answer;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setVariant_1(String variant_1) {
        this.variant_1 = variant_1;
    }

    public void setVariant_2(String variant_2) {
        this.variant_2 = variant_2;
    }

    public void setVariant_3(String variant_3) {
        this.variant_3 = variant_3;
    }

    public void setRight_aswer(String right_aswer) {
        this.right_answer = right_aswer;
    }

    @Override
    public String toString() {
        return "Questions{" +
                "text='" + text + '\'' +
                ", variant_1='" + variant_1 + '\'' +
                ", variant_2='" + variant_2 + '\'' +
                ", variant_3='" + variant_3 + '\'' +
                ", right_answer=" + right_answer +
                '}';
    }
};