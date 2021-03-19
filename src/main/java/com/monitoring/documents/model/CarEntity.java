package com.monitoring.documents.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;


/*
* ~ Cars ~
Numar sasiu(optional)
Numar masina
Marca
Model
Data expirarii
* */

@Entity
@Table(name = "car")
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serialNumber")
    private String serialNumber;

    private String carNationalId;
    @Column(name = "carBrand")
    @NotNull
    @Size(min = 2)
    private String brand;

    @Column
    @NotNull
    private String model;


    private Date expirationDate;



}
