package me.mocha.calendar.user.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class User {

    @Id
    private String username;

    private String password;
    private String name;
    private String cls;
    private String id;

    @Builder
    public User(String username, String password, String name, String cls, String id) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.cls = cls;
        this.id = id;
    }

    public String getStudentID() {
        return cls + id;
    }

}
