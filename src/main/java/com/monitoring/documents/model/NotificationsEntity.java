package com.monitoring.documents.model;





import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Notifications")
public class NotificationsEntity {

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
