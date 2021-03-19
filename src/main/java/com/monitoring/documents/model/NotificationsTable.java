package com.monitoring.documents.model;



/*
* ~ Notificari ~
Id
Tip(SMS, Browser)
Data trimis
Data primit
Seen
Grad de alerta
User id sent
* */


import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class NotificationsTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private Date sendedAt;
    private Date arrivedAt;
    private boolean hasSeen;
    private String gradeOfAlert;
    private String userIdSent;
    
    



}
