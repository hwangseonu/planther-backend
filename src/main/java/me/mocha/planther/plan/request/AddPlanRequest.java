package me.mocha.planther.plan.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddPlanRequest {

    @NotNull(message = "일정 제목은 빈칸일 수 없습니다.")
    @NotBlank(message = "일정 제목은 빈칸일 수 없습니다.")
    private String title;

    @NotNull(message = "일정 내용은 빈칸일 수 없습니다.")
    @NotBlank(message = "일정 내용은 빈칸일 수 없습니다.")
    private String content;

    @NotNull(message = "일정 종류를 지정해 주세요.")
    @NotBlank(message = "일정 종류를 지정해 주세요.")
    private String type;

    @Range(min = 2018, max = 2100, message = "년도의 범위를 벗어났습니다.")
    private Integer year;

    @Range(min = 1, max = 12, message = "월의 범위를 벗어났습니다.")
    private Integer month;

    @Range(min = 1, max = 31, message = "일의 범위를 벗어났습니다.")
    private Integer day;

}
