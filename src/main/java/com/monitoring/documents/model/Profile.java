package com.monitoring.documents.model;

import java.util.Date;

public class Profile {
    private String phoneNumber;
    private Address address;
    private Date dateOfBirth;
    private Integer age;
    private String personalIdentificationNumber;

    public Profile() {

    }

    public Profile(String phoneNumber, Address address, Date dateOfBirth, Integer age, String personalIdentificationNumber) {
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.personalIdentificationNumber = personalIdentificationNumber;
    }

    public Profile(String phoneNumber, String address) {
        this.phoneNumber = phoneNumber;

    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPersonalIdentificationNumber() {
        return personalIdentificationNumber;
    }

    public void setPersonalIdentificationNumber(String personalIdentificationNumber) {
        this.personalIdentificationNumber = personalIdentificationNumber;
    }
}
