package me.mocha.planther.common.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @Size(min = 4)
    private String username;

    @JsonIgnore
    @Size(min = 8)
    private String password;

    @Size(min = 2, max = 5)
    private String name;

    @Range(min = 1, max = 3)
    private int grade;

    @Range(min = 1, max = 4)
    private int cls;

    @Range(min = 1, max = 20)
    private int number;

    @JsonIgnore
    private String role;

    public String getStudentID() {
        return String.format("%d%02d%02d", grade, cls, number);
    }

}
