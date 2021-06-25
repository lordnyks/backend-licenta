package com.monitoring.documents.model;

import java.time.LocalDate;

public class EmailHelper {
    private LocalDate expireDate;
    private String description;


    public EmailHelper() {

    }

    public EmailHelper(LocalDate expireDate, String description) {
        this.expireDate = expireDate;
        this.description = description;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
