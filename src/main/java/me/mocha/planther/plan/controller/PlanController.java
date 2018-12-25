package me.mocha.planther.plan.controller;

import lombok.extern.slf4j.Slf4j;
import me.mocha.planther.common.annotation.CurrentUser;
import me.mocha.planther.common.exception.NotFoundException;
import me.mocha.planther.common.model.entity.User;
import me.mocha.planther.plan.model.entity.Plan;
import me.mocha.planther.plan.model.repository.PlanRepository;
import me.mocha.planther.plan.request.AddPlanRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/plans")
@Slf4j
public class PlanController {

    private final PlanRepository planRepository;

    public PlanController(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @PostMapping
    public Plan addPlan(@CurrentUser User user, @Valid @RequestBody AddPlanRequest request) {
        return planRepository.save(Plan.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .type(Plan.Type.valueOf(request.getType()))
                .year(request.getYear())
                .month(request.getMonth())
                .day(request.getDay())
                .user(user)
                .classId(user.getClassId())
                .build());
    }

    @DeleteMapping("/{id}")
    public void deletePlan(@PathVariable("id") long id) {
        if (!planRepository.existsById(id)) {
            throw new NotFoundException("존재하지 않는 계획입니다.");
        }
        planRepository.deleteById(id);
    }

}
