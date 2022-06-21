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
    @SerializedName("numberId")
    private String numberId;
    @SerializedName("sectionId")
    private String sectionId;
    @SerializedName("type")
    private int type;//0:Number , 1:Section, 2: Name (Number + Section)
    @SerializedName("order")
    private int order;

    public ClassModel() {
    }

    public ClassModel(String number, String numberEn, String section, String sectionEn, String numberId, String sectionId, int type, int order) {
        this.number = number;
        this.numberEn = numberEn;
        this.section = section;
        this.sectionEn = sectionEn;
        this.numberId = numberId;
        this.sectionId = sectionId;
        this.type = type;
        this.order = order;
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

    public String getNumberId() {
        return numberId;
    }

    public void setNumberId(String numberId) {
        this.numberId = numberId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
