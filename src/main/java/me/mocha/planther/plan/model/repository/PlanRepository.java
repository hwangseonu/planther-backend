package me.mocha.planther.plan.model.repository;

import me.mocha.planther.plan.model.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {

}
