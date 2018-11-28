package me.mocha.calendar.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class User {

    @Id
    private String username;

    @JsonIgnore
    private String password;
    private int grade;
    private int cls;
    private int number;
    private String role;


    public String getStudentID() {
        return String.format("%d%02d%02d", grade, cls, number);
    }

    public String getClassID() {
        return String.format("%d%02d", grade, cls);
    }

}
