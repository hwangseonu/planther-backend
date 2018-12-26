package me.mocha.planther.plan.model.repository;

import me.mocha.planther.plan.model.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    Optional<Plan> findByClassIdAndYearAndMonthAndDayAndId(String classId, int year, int month, int day, long id);
    List<Plan> findAllByClassIdAndYearAndMonthAndDay(String classId, int year, int month, int day);
}
