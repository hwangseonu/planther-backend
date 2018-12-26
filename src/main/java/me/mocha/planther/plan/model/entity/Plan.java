package me.mocha.planther.plan.model.entity;

import lombok.*;
import me.mocha.planther.common.model.entity.User;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Plan {

    public enum Type {
        assignment,
        event,
        presentation;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private Type type;

    private int year;

    private int month;

    private int day;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private User user;

    private String classId;

    private LocalDate getDate() {
        return LocalDate.of(year, month, day);
    }

}
