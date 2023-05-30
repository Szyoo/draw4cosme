package com.szyoo.draw4cosme.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Present {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String link;

    private String presentName;

    private String brandName;

    private Date discoveredDate;

    private String imgLink;

    @OneToMany(mappedBy = "present")
    private List<UserPresent> userPresents = new ArrayList<>();

    public Present() {}
    
    public Present(String link, String presentName, String brandName, String imgLink) {
        this.link = link;
        this.presentName = presentName;
        this.brandName = brandName;
        this.imgLink = imgLink;
    }

    public Present(String link, String presentName) {
        // 用于在检索奖品列表时获取链接和描述时使用
        this.link = link;
        this.presentName = presentName;
    }

    @PrePersist
    public void prePersist() {
        discoveredDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPresentName() {
        return presentName;
    }

    public void setPresentName(String presentName) {
        this.presentName = presentName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Date getDiscoveredDate() {
        return discoveredDate;
    }

    public void setDiscoveredDate(Date discoveredDate) {
        this.discoveredDate = discoveredDate;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public List<UserPresent> getUserPresents() {
        return userPresents;
    }

    public void setUserPresents(List<UserPresent> userPresents) {
        this.userPresents = userPresents;
    }

}
