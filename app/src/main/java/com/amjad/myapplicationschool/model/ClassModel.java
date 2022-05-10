package com.amjad.myapplicationschool.model;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

public class ClassModel {
    @Keep
    @SerializedName("number")
    private String number;
    @SerializedName("numberEn")
    private String numberEn;
    @SerializedName("section")
    private String section;
    @SerializedName("sectionEn")
    private String sectionEn;

    public ClassModel() {
    }

    public ClassModel( String number, String numberEn, String section, String sectionEn) {
        this.number = number;
        this.numberEn = numberEn;
        this.section = section;
        this.sectionEn = sectionEn;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumberEn() {
        return numberEn;
    }

    public void setNumberEn(String numberEn) {
        this.numberEn = numberEn;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSectionEn() {
        return sectionEn;
    }

    public void setSectionEn(String sectionEn) {
        this.sectionEn = sectionEn;
    }
}
