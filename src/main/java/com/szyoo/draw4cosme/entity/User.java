package com.szyoo.draw4cosme.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String name;

    private int age;

    @Enumerated(EnumType.STRING)
    private Profession profession;

    @OneToMany(mappedBy = "user")
    private List<UserPresent> userPresents = new ArrayList<>();

    public int getTotalPresents() {
        return userPresents.size();
    }

    public enum Profession {
        EMPLOYEE("会社員"),
        PART_TIME("パート・アルバイト"),
        SELF_EMPLOYED("自営業・自由業"),
        HOUSEWIFE("専業主婦"),
        STUDENT("学生"),
        UNEMPLOYED("仕事はしていない");

        private String description;

        Profession(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public User() {
    }

    public User(String email, String password, String name, int age, Profession profession) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
        this.profession = profession;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public List<UserPresent> getUserPresents() {
        return userPresents;
    }

    public void setUserPresents(List<UserPresent> userPresents) {
        this.userPresents = userPresents;
    }

}
