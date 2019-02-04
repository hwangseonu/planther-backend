package me.mocha.planther.plan.controller;

import lombok.extern.slf4j.Slf4j;
import me.mocha.planther.common.annotations.CurrentUser;
import me.mocha.planther.common.model.entity.User;
import me.mocha.planther.plan.model.entity.Plan;
import me.mocha.planther.plan.model.repository.PlanRepository;
import me.mocha.planther.plan.request.AddPlanRequest;
import me.mocha.planther.plan.request.UpdatePlanRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/plans")
@Slf4j
public class PlanController {

    private final PlanRepository planRepository;

    @Autowired
    public PlanController(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @PostMapping
    public ResponseEntity<Plan> newPlan(@CurrentUser User user, @Valid @RequestBody AddPlanRequest request) {
        try {
            Plan plan = planRepository.save(Plan.builder()
                    .title(request.getTitle())
                    .content(request.getContent())
                    .type(Plan.Type.valueOf(request.getType()))
                    .year(request.getYear())
                    .month(request.getMonth())
                    .day(request.getDay())
                    .user(user)
                    .classId(user.getClassId())
                    .build());
            log.info("plan {} added", plan.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(plan);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plan> getPlan(@CurrentUser User user, @PathVariable("id") long id) {
        Plan plan = planRepository.findById(id).orElse(null);
        if (plan == null) return ResponseEntity.notFound().build();
        if (!plan.getClassId().equals(user.getClassId())) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.ok(plan);
    }

    @GetMapping("/{year}/{month}/{day}")
    public ResponseEntity<List<Plan>> getDayPlan(@CurrentUser User user,
                                                 @PathVariable("year") int year,
                                                 @PathVariable("month") int month,
                                                 @PathVariable("day") int day) {
        List<Plan> plans = planRepository.findAllByClassIdAndYearAndMonthAndDay(user.getClassId(), year, month, day);
        return ResponseEntity.ok(plans);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Plan> updatePlan(@CurrentUser User user, @Valid @RequestBody UpdatePlanRequest request, @PathVariable("id") long id) {
        Plan plan = planRepository.findById(id).orElse(null);
        if (plan == null) return ResponseEntity.notFound().build();
        if (!plan.getUser().equals(user)) ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        plan.setTitle(request.getTitle());
        plan.setContent(request.getContent());
        plan = planRepository.save(plan);
        return ResponseEntity.ok(plan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlan(@CurrentUser User user, @PathVariable("id") long id) {
        Plan plan = planRepository.findById(id).orElse(null);
        if (plan == null) return ResponseEntity.notFound().build();
        if (!plan.getUser().equals(user)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        planRepository.deleteById(id);
        return ResponseEntity.ok(null);
    }
}
