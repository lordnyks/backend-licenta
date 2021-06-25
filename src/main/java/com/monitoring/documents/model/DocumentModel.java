package com.monitoring.documents.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "documents")
public class DocumentModel {

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
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateOfCreation;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    private String banca;

    @Column(name = "data_expirarii")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expireDate;

    @Column(name = "numar_national_masina")
    private String plateNumber;

    @Column(name = "marca_masina")
    private String made;

    @Column(name = "model_masina")
    private String model;

    private String personalIdentificationNumber;

    private String mentions;


    private String fullAddress;

    @Column(name = "descriere")
    private String description;



    public DocumentModel() {

    }

    public DocumentModel(Long id, @NotNull(message = "Introduceti un user_id") Long userId, @NotNull String email, @NotNull LocalDate dateOfCreation, String firstName, String lastName, String banca, LocalDate expireDate, String plateNumber, String made, String model, String personalIdentificationNumber, String mentions, String fullAddress, String description) {
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.dateOfCreation = dateOfCreation;
        this.firstName = firstName;
        this.lastName = lastName;
        this.banca = banca;
        this.expireDate = expireDate;
        this.plateNumber = plateNumber;
        this.made = made;
        this.model = model;
        this.personalIdentificationNumber = personalIdentificationNumber;
        this.mentions = mentions;
        this.fullAddress = fullAddress;
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

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getBanca() {
        return banca;
    }

    public void setBanca(String banca) {
        this.banca = banca;
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

    public String getPersonalIdentificationNumber() {
        return personalIdentificationNumber;
    }

    public void setPersonalIdentificationNumber(String personalIdentificationNumber) {
        this.personalIdentificationNumber = personalIdentificationNumber;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getMentions() {
        return mentions;
    }

    public void setMentions(String mentions) {
        this.mentions = mentions;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
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
