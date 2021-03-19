package com.monitoring.documents.model;

import javax.persistence.*;
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
@Table
public class CarTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serialNumber;
    private String carNationalId;
    private String brand;
    private String model;
    private Date expirationDate;



}
