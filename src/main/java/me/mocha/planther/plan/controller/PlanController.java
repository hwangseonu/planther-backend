package me.mocha.planther.plan.controller;

import lombok.extern.slf4j.Slf4j;
import me.mocha.planther.common.annotations.CurrentUser;
import me.mocha.planther.common.model.entity.User;
import me.mocha.planther.plan.model.entity.Plan;
import me.mocha.planther.plan.model.repository.PlanRepository;
import me.mocha.planther.plan.request.AddPlanRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

}
