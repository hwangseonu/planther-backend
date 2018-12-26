package me.mocha.planther.plan.controller;

import lombok.extern.slf4j.Slf4j;
import me.mocha.planther.common.annotation.CurrentUser;
import me.mocha.planther.common.exception.NotFoundException;
import me.mocha.planther.common.model.entity.User;
import me.mocha.planther.plan.model.entity.Plan;
import me.mocha.planther.plan.model.repository.PlanRepository;
import me.mocha.planther.plan.request.AddPlanRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/plans")
@Slf4j
public class PlanController {

    private final PlanRepository planRepository;

    public PlanController(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Plan addPlan(@CurrentUser User user, @Valid @RequestBody AddPlanRequest request) {
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
        return plan;
    }

    @DeleteMapping("/{id}")
    public void deletePlan(@PathVariable("id") long id) {
        if (!planRepository.existsById(id)) {
            throw new NotFoundException("존재하지 않는 계획입니다.");
        }
        planRepository.deleteById(id);
    }

    @GetMapping("/{year}/{month}/{day}/{id}")
    public Plan getPlan(
            @CurrentUser User user,
            @PathVariable("year") int year,
            @PathVariable("month") int month,
            @PathVariable("day") int day,
            @PathVariable("id") long id) {
        return planRepository.findByClassIdAndYearAndMonthAndDayAndId(
                user.getClassId(), year, month, day, id).orElseThrow(() -> new NotFoundException("존재하지 않는 계획힙니다."));
    }

    @GetMapping("/{year}/{month}/{day}")
    public List<Plan> getDayPlans(
            @CurrentUser User user,
            @PathVariable("year") int year,
            @PathVariable("month") int month,
            @PathVariable("day") int day) {
        return planRepository.findAllByClassIdAndYearAndMonthAndDay(user.getClassId(), year, month, day);
    }

}
