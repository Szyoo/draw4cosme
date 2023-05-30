package com.szyoo.draw4cosme.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_present")
public class UserPresent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "present_id")
    private Present present;

    private Date drawDate;

    private Boolean isDrawn;

    public UserPresent() {
    }

    public UserPresent(User user, Present present, Date drawDate, Boolean isDrawn) {
        this.user = user;
        this.present = present;
        this.drawDate = drawDate;
        this.isDrawn = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Present getPresent() {
        return present;
    }

    public void setPresent(Present present) {
        this.present = present;
    }

    public Date getDrawDate() {
        return drawDate;
    }

    public void setDrawDate(Date drawDate) {
        this.drawDate = drawDate;
    }

    public Boolean getIsDrawn() {
        return isDrawn;
    }

    public void setIsDrawn(Boolean isDrawn) {
        this.isDrawn = isDrawn;
    }

}
