package me.mocha.calendar.model.repository;

import me.mocha.calendar.model.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar, Integer> {
    List<Calendar> findAllByClassIdAndYearAndMonthAndDay(String classId, int year, int month, int day);
}
