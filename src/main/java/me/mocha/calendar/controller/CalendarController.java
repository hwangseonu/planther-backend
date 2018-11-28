package me.mocha.calendar.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calendar")
@Slf4j
public class CalendarController {

    @PostMapping("/{year}/{month}/{day}")
    public void addCalendar(@PathVariable("year") int year, @PathVariable("month") int month, @PathVariable("day") int day) {

    }

}
