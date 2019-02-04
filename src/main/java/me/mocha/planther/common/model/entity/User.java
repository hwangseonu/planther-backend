package me.mocha.planther.common.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    private String username;

    @JsonIgnore
    private String password;

    private String name;

    private int grade;

    private int cls;

    private int number;

    @JsonIgnore
    private String role;

    public String getStudentId() {
        return String.format("%d%02d%02d", grade, cls, number);
    }

    public String getClassId() {
        return String.format("%d%02d", grade, cls);
    }

    public boolean equals(User user) {
        if (user == null) return false;
        return this.username.equals(user.username);
    }

}
