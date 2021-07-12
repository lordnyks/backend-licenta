package com.monitoring.documents.model;

import java.time.LocalDate;

public class SubscriptionSaverHelper {


    private String email;
    private String plateNumber;
    private String made;
    private String model;
    private String lastName;
    private String firstName;
    private String personalIdentificationNumber;
    private String banca;
    private String mentions;
    private String fullAddress;
    private LocalDate expireDate;

    public SubscriptionSaverHelper() {

    }


    public SubscriptionSaverHelper(String email, String plateNumber, String made, String model, String lastName, String firstName, String personalIdentificationNumber, String banca, String mentions, String fullAddress, LocalDate expireDate) {
        this.email = email;
        this.plateNumber = plateNumber;
        this.made = made;
        this.model = model;
        this.lastName = lastName;
        this.firstName = firstName;
        this.personalIdentificationNumber = personalIdentificationNumber;
        this.banca = banca;
        this.mentions = mentions;
        this.fullAddress = fullAddress;
        this.expireDate = expireDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getMade() {
        return made;
    }

    public void setMade(String made) {
        this.made = made;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPersonalIdentificationNumber() {
        return personalIdentificationNumber;
    }

    public void setPersonalIdentificationNumber(String personalIdentificationNumber) {
        this.personalIdentificationNumber = personalIdentificationNumber;
    }

    public String getBanca() {
        return banca;
    }

    public void setBanca(String banca) {
        this.banca = banca;
    }

    public String getMentions() {
        return mentions;
    }

    public void setMentions(String mentions) {
        this.mentions = mentions;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }
}
