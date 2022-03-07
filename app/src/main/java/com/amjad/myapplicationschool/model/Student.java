package com.amjad.myapplicationschool.model;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Student {
    @Keep
    @SerializedName("studentID")
    private String studentID;
    //Student information about first enter school
    @SerializedName("dateInterSchool")
    private String dateInterSchool;
    @SerializedName("ageInterSchool")
    private String ageInterSchool;
    @SerializedName("ageInOctober")
    private String ageInOctober;
    @SerializedName("firstSchoolName") //name
    private String firstSchoolName;
    // Student information about current class
    @SerializedName("currentClass") // name
    private String currentClass;
    @SerializedName("sectionCurrentClass") // currentClass(a), currentClass(b), currentClass(c)
    private String sectionCurrentClass;
    @SerializedName("dateCurrentClass")
    private String dateCurrentClass;
    @SerializedName("typeCurrentClass") // primary, secondary, secondary
    private String typeCurrentClass;
    @SerializedName("majorCurrentClass") // scientific, literary
    private String majorCurrentClass;
    @SerializedName("returnedClass")
    private ArrayList<String> returnedClass;
    // Student personal information
    @SerializedName("dateOfBirth")
    private String dateOfBirth;
    @SerializedName("birthCertificate")
    private String birthCertificate;
    @SerializedName("previousClassCertificate")
    private String previousClassCertificate;
    @SerializedName("identification")
    private String identification;
    @SerializedName("countryBirth")
    private String countryBirth;
    @SerializedName("countryOrigin")
    private String countryOrigin;
    // Responsible student information
    @SerializedName("respStName")//responsibleStudentName
    private String respStName;
    @SerializedName("respStCountry")//responsibleStudentCountry
    private String respStCountry;
    @SerializedName("respStVillage")//responsibleStudentVillage
    private String respStVillage;
    @SerializedName("respStCurrentPlace")//responsibleStudentCurrentPlace
    private String respStCurrentPlace;
    @SerializedName("respStMainPhone")//responsibleStudentCurrentPlace
    private String respStMainPhone;
    @SerializedName("respStSecondaryPhone")//responsibleStudentCurrentPlace
    private String respStSecondaryPhone;
    @SerializedName("respStRationCardNumber")//responsibleStudentCurrentPlace
    private String respStRationCardNumber;
    //Student Secondary Information
    @SerializedName("skills")
    private ArrayList<String> skills;
    @SerializedName("medicalRecord")
    private String medicalRecord;
    @SerializedName("accountStatement")
    private String accountStatement;

    public Student() {
    }

    public Student(String studentID, String dateInterSchool, String ageInterSchool, String ageInOctober
            , String firstSchoolName, String currentClass, String sectionCurrentClass, String dateCurrentClass, String typeCurrentClass
            , String majorCurrentClass, ArrayList<String> returnedClass, String dateOfBirth
            , String birthCertificate, String previousClassCertificate, String identification, String countryBirth, String countryOrigin
            , String respStName, String respStCountry, String respStVillage, String respStCurrentPlace
            , String respStMainPhone, String respStSecondaryPhone, String respStRationCardNumber
            , ArrayList<String> skills, String medicalRecord, String accountStatement) {
        this.studentID = studentID;
        this.dateInterSchool = dateInterSchool;
        this.ageInterSchool = ageInterSchool;
        this.ageInOctober = ageInOctober;
        this.firstSchoolName = firstSchoolName;
        this.currentClass = currentClass;
        this.sectionCurrentClass = sectionCurrentClass;
        this.dateCurrentClass = dateCurrentClass;
        this.typeCurrentClass = typeCurrentClass;
        this.majorCurrentClass = majorCurrentClass;
        this.returnedClass = returnedClass;
        this.dateOfBirth = dateOfBirth;
        this.birthCertificate = birthCertificate;
        this.previousClassCertificate = previousClassCertificate;
        this.identification = identification;
        this.countryBirth = countryBirth;
        this.countryOrigin = countryOrigin;
        this.respStName = respStName;
        this.respStCountry = respStCountry;
        this.respStVillage = respStVillage;
        this.respStCurrentPlace = respStCurrentPlace;
        this.respStMainPhone = respStMainPhone;
        this.respStSecondaryPhone = respStSecondaryPhone;
        this.respStRationCardNumber = respStRationCardNumber;
        this.skills = skills;
        this.medicalRecord = medicalRecord;
        this.accountStatement = accountStatement;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getDateInterSchool() {
        return dateInterSchool;
    }

    public void setDateInterSchool(String dateInterSchool) {
        this.dateInterSchool = dateInterSchool;
    }

    public String getAgeInterSchool() {
        return ageInterSchool;
    }

    public void setAgeInterSchool(String ageInterSchool) {
        this.ageInterSchool = ageInterSchool;
    }

    public String getAgeInOctober() {
        return ageInOctober;
    }

    public void setAgeInOctober(String ageInOctober) {
        this.ageInOctober = ageInOctober;
    }

    public String getFirstSchoolName() {
        return firstSchoolName;
    }

    public void setFirstSchoolName(String firstSchoolName) {
        this.firstSchoolName = firstSchoolName;
    }

    public String getCurrentClass() {
        return currentClass;
    }

    public void setCurrentClass(String currentClass) {
        this.currentClass = currentClass;
    }

    public String getSectionCurrentClass() {
        return sectionCurrentClass;
    }

    public void setSectionCurrentClass(String sectionCurrentClass) {
        this.sectionCurrentClass = sectionCurrentClass;
    }

    public String getDateCurrentClass() {
        return dateCurrentClass;
    }

    public void setDateCurrentClass(String dateCurrentClass) {
        this.dateCurrentClass = dateCurrentClass;
    }

    public String getTypeCurrentClass() {
        return typeCurrentClass;
    }

    public void setTypeCurrentClass(String typeCurrentClass) {
        this.typeCurrentClass = typeCurrentClass;
    }

    public String getMajorCurrentClass() {
        return majorCurrentClass;
    }

    public void setMajorCurrentClass(String majorCurrentClass) {
        this.majorCurrentClass = majorCurrentClass;
    }

    public ArrayList<String> getReturnedClass() {
        return returnedClass;
    }

    public void setReturnedClass(ArrayList<String> returnedClass) {
        this.returnedClass = returnedClass;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBirthCertificate() {
        return birthCertificate;
    }

    public void setBirthCertificate(String birthCertificate) {
        this.birthCertificate = birthCertificate;
    }

    public String getPreviousClassCertificate() {
        return previousClassCertificate;
    }

    public void setPreviousClassCertificate(String previousClassCertificate) {
        this.previousClassCertificate = previousClassCertificate;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getCountryBirth() {
        return countryBirth;
    }

    public void setCountryBirth(String countryBirth) {
        this.countryBirth = countryBirth;
    }

    public String getCountryOrigin() {
        return countryOrigin;
    }

    public void setCountryOrigin(String countryOrigin) {
        this.countryOrigin = countryOrigin;
    }

    public String getRespStName() {
        return respStName;
    }

    public void setRespStName(String respStName) {
        this.respStName = respStName;
    }

    public String getRespStCountry() {
        return respStCountry;
    }

    public void setRespStCountry(String respStCountry) {
        this.respStCountry = respStCountry;
    }

    public String getRespStVillage() {
        return respStVillage;
    }

    public void setRespStVillage(String respStVillage) {
        this.respStVillage = respStVillage;
    }

    public String getRespStCurrentPlace() {
        return respStCurrentPlace;
    }

    public void setRespStCurrentPlace(String respStCurrentPlace) {
        this.respStCurrentPlace = respStCurrentPlace;
    }

    public String getRespStMainPhone() {
        return respStMainPhone;
    }

    public void setRespStMainPhone(String respStMainPhone) {
        this.respStMainPhone = respStMainPhone;
    }

    public String getRespStSecondaryPhone() {
        return respStSecondaryPhone;
    }

    public void setRespStSecondaryPhone(String respStSecondaryPhone) {
        this.respStSecondaryPhone = respStSecondaryPhone;
    }

    public String getRespStRationCardNumber() {
        return respStRationCardNumber;
    }

    public void setRespStRationCardNumber(String respStRationCardNumber) {
        this.respStRationCardNumber = respStRationCardNumber;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
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
}
