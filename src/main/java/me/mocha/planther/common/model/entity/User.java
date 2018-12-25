package me.mocha.planther.common.model.entity;

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

    @Size(min = 8)
    private String password;

    @Range(min = 1, max = 3)
    private int grade;

    @Range(min = 1, max = 4)
    private int cls;

    @Range(min = 1, max = 20)
    private int number;

    private String studentID() {
        return String.format("%d%02d%02d", grade, cls, number);
    }

}
