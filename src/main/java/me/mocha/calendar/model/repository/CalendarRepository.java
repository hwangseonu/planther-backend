package me.mocha.calendar.model.repository;

import me.mocha.calendar.model.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendar, Integer> {

}
