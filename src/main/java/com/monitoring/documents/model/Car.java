package com.monitoring.documents.model;

import javax.persistence.*;


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
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
