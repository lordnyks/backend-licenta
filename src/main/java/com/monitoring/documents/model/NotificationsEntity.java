package com.monitoring.documents.model;





import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "Notifications")
public class NotificationsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;


    private Date sendedAt;
    private boolean isSent;
    private Long subscriptionId;

    public NotificationsEntity() {

    }

    public NotificationsEntity(String userEmail,  Date sendedAt, boolean isSent, Long subscriptionId) {
        this.userEmail = userEmail;
        this.sendedAt = sendedAt;
        this.isSent = isSent;
        this.subscriptionId = subscriptionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


    public Date getSendedAt() {
        return sendedAt;
    }

    public void setSendedAt(Date sendedAt) {
        this.sendedAt = sendedAt;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }
}
