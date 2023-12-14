package com.example.appointmentmanager.model;

import java.util.Date;

public class Patient {
    private Long id;
    private String firstName;
    private String surname;
    private String otherName;
    private String patientNumber;
    private String birthDate;
    private String idNumber;
    private String mobileNumber;
    private String email;
    private String altContactPerson;
    private String atlContactPersonPhone;
    private Boolean disability;
    private String county;

    public Patient() {
    }

    public Patient(String firstName, String surname, String otherName, String patientNumber, String birthDate, String idNumber, String mobileNumber, String email, String altContactPerson, String atlContactPersonPhone, Boolean disability, String county) {
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.otherName = otherName;
        this.patientNumber = patientNumber;
        this.birthDate = birthDate;
        this.idNumber = idNumber;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.altContactPerson = altContactPerson;
        this.atlContactPersonPhone = atlContactPersonPhone;
        this.disability = disability;
        this.county = county;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getPatientNumber() {
        return patientNumber;
    }

    public void setPatientNumber(String patientNumber) {
        this.patientNumber = patientNumber;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAltContactPerson() {
        return altContactPerson;
    }

    public void setAltContactPerson(String altContactPerson) {
        this.altContactPerson = altContactPerson;
    }

    public Boolean getDisability() {
        return disability;
    }

    public void setDisability(Boolean disability) {
        this.disability = disability;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAtlContactPersonPhone() {
        return atlContactPersonPhone;
    }

    public void setAtlContactPersonPhone(String atlContactPersonPhone) {
        this.atlContactPersonPhone = atlContactPersonPhone;
    }
}
