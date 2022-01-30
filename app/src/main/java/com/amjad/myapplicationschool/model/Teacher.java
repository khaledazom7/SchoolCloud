package com.amjad.myapplicationschool.model;

import android.content.Context;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Teacher {
    @Keep
    @SerializedName("teacherID")
    private String teacherID;
    @SerializedName("major")
    private String major;
    @SerializedName("degree")
    private String degree;
    @SerializedName("experiences")
    private String experiences;
    @SerializedName("skills")
    private String skills;
    @SerializedName("identification")
    private String identification;
    @SerializedName("eduCourses")
    private String eduCourses;
    @SerializedName("medicalRecord")
    private String medicalRecord;
    @SerializedName("accountStatement")
    private String accountStatement;
    @SerializedName("callInfo")//responsibleStudentCurrentPlace
    private String callInfo;

    public Teacher() {

    }

    public Teacher(String teacherID,String major, String degree, String experiences, String skills
            , String identification, String eduCourses, String medicalRecord, String accountStatement, String callInfo) {
        this.teacherID = teacherID;
        this.major = major;
        this.degree = degree;
        this.experiences = experiences;
        this.skills = skills;
        this.identification = identification;
        this.eduCourses = eduCourses;
        this.medicalRecord = medicalRecord;
        this.accountStatement = accountStatement;
        this.callInfo = callInfo;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getExperiences() {
        return experiences;
    }

    public void setExperiences(String experiences) {
        this.experiences = experiences;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getEduCourses() {
        return eduCourses;
    }

    public void setEduCourses(String eduCourses) {
        this.eduCourses = eduCourses;
    }

    public String getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(String medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public String getAccountStatement() {
        return accountStatement;
    }

    public void setAccountStatement(String accountStatement) {
        this.accountStatement = accountStatement;
    }

    public String getCallInfo() {
        return callInfo;
    }

    public void setCallInfo(String callInfo) {
        this.callInfo = callInfo;
    }
}
