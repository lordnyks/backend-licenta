package com.monitoring.documents.model;

public class Address {
    private String county;
    private String city;
    private String townShip;
    private String village;
    private String street;
    private String gateNumber;


    public Address() { }

    public Address(String county, String city, String townShip, String village, String street, String gateNumber) {
        this.county = county;
        this.city = city;
        this.townShip = townShip;
        this.village = village;
        this.street = street;
        this.gateNumber = gateNumber;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTownShip() {
        return townShip;
    }

    public void setTownShip(String townShip) {
        this.townShip = townShip;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getGateNumber() {
        return gateNumber;
    }

    public void setGateNumber(String gateNumber) {
        this.gateNumber = gateNumber;
    }
}
