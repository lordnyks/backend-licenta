package com.monitoring.documents.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "abonamente")
public class Subscriptions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Introduceti un user_id")
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    private String email;

    @NotNull
    @Column(name = "dateOfCreation")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfCreation;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "data_expirarii")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expireDate;

    @Column(name = "numar_national_masina")
    private String plateNumber;

    @Column(name = "marca_masina")
    private String made;

    @Column(name = "model_masina")
    private String model;

    @Column(name = "descriere")
    private String description;

    public Subscriptions() {

    }

    public Subscriptions(Long id, @NotNull(message = "Introduceti un user_id") Long userId, @NotNull String email, @NotNull Date dateOfCreation, String firstName, String lastName, Date expireDate, String plateNumber, String made, String model, String description) {
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.dateOfCreation = dateOfCreation;
        this.firstName = firstName;
        this.lastName = lastName;
        this.expireDate = expireDate;
        this.plateNumber = plateNumber;
        this.made = made;
        this.model = model;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
