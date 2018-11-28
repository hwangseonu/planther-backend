package me.mocha.calendar.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data

@Entity
public class Calendar {

    public enum Type {
        ASSIGNMENT("assignment"),
        EVENT("event"),
        PRESENTATION("presentation");

        private String stringValue;

        Type(String stringValue) {
            this.stringValue = stringValue;
        }

        @Override
        public String toString() {
            return stringValue;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int year;
    private int month;
    private int day;

    private String title;
    private String content;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private Type type;

}