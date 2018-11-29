package me.mocha.calendar.controller;

import lombok.extern.slf4j.Slf4j;
import me.mocha.calendar.model.entity.Calendar;
import me.mocha.calendar.model.entity.User;
import me.mocha.calendar.model.repository.CalendarRepository;
import me.mocha.calendar.payload.request.Calendar.AddCalendarRequest;
import me.mocha.calendar.annotation.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/calendar")
@Slf4j
public class CalendarController {

    private int days[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    @Autowired
    private CalendarRepository calendarRepository;

    @PostMapping
    public Calendar addCalendar(@CurrentUser User user, @Valid @RequestBody AddCalendarRequest request) {
        return calendarRepository.save(Calendar.builder()
                .year(request.getYear())
                .month(request.getMonth())
                .day(request.getDay())
                .title(request.getTitle())
                .content(request.getContent())
                .user(user)
                .type(request.getType())
                .classId(user.getClassID())
                .build());
    }

    @GetMapping("/{year}")
    public Map<Integer, Map<Integer, List<Calendar>>> getYearCalendar(@CurrentUser User user, @PathVariable("year") int year) {
        Map<Integer, Map<Integer, List<Calendar>>> yearCalendar = new HashMap<>();
        IntStream.range(1, 13).forEach(m -> {
            Map<Integer, List<Calendar>> monthCalendar = new HashMap<>();
            int d = days[m - 1] + (m == 2 && isLeapYear(year) ? 1 : 0);
            IntStream.range(1, d + 1).forEach(i -> {
                List<Calendar> calendars = calendarRepository.findAllByClassIdAndYearAndMonthAndDay(user.getClassID(), year, m, i);
                calendars.sort(Comparator.comparingInt(Calendar::getId));
                monthCalendar.put(i, calendars);
            });
            yearCalendar.put(m, monthCalendar);
        });
        return yearCalendar;
    }

    @GetMapping("/{year}/{month}")
    public Map<Integer, List<Calendar>> getMonthCalendar(@CurrentUser User user, @PathVariable("year") int year, @PathVariable("month") int month) {
        int d = days[month - 1] + (month == 2 && isLeapYear(year) ? 1 : 0);
        Map<Integer, List<Calendar>> monthCalendar = new HashMap<>();
        IntStream.range(1, d + 1).forEach(i -> {
            List<Calendar> calendars = calendarRepository.findAllByClassIdAndYearAndMonthAndDay(user.getClassID(), year, month, i);
            calendars.sort(Comparator.comparingInt(Calendar::getId));
            monthCalendar.put(i, calendars);
        });
        return monthCalendar;


    }

    @GetMapping("/{year}/{month}/{day}")
    public List<Calendar> getDayCalendar(@CurrentUser User user, @PathVariable("year") int year, @PathVariable("month") int month, @PathVariable("day") int day) {
        List<Calendar> calendars = calendarRepository.findAllByClassIdAndYearAndMonthAndDay(user.getClassID(), year, month, day);
        calendars.sort(Comparator.comparingInt(Calendar::getId));
        return calendars;
    }

    private boolean isLeapYear(int year) {
        return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
    }

}
