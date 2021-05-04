package com.monitoring.documents.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "abonamente")
public class Subscriptions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Column(name = "user_id")
    private Long userId;

//    @NotNull
//    @NotBlank
//    @Column(name = "dateOfCreation")
//    private Date dateOfCreation;

    @NotNull
    @NotBlank
    @Column(name = "data_expirarii")
    private Date expireDate;

    @Column(name = "numar_national_masina")
    private String plateNumber;

    @Column(name = "marca_masina")
    private String made;

    @Column(name = "descriere")
    private String description;

    public Subscriptions() {

    }

    public Subscriptions(Long id, @NotNull @NotBlank Long userId, @NotNull @NotBlank Date expireDate, String plateNumber, String made, String description) {
        this.id = id;
        this.userId = userId;
        this.expireDate = expireDate;
        this.plateNumber = plateNumber;
        this.made = made;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
