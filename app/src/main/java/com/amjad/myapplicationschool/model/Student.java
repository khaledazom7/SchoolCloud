package com.amjad.myapplicationschool.model;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

public class Student {
    @Keep
    @SerializedName("studentID")
    private String studentID;
    @Keep
    @SerializedName("level")
    private String level;
    @Keep
    @SerializedName("skills")
    private String skills;
    @Keep
    @SerializedName("identification")
    private String identification;
    @Keep
    @SerializedName("eduCourses")
    private String eduCourses;
    @Keep
    @SerializedName("medicalRecord")
    private String medicalRecord;
    @Keep
    @SerializedName("accountStatement")// 1:active , 0:not active ,
    private String accountStatement;
}
