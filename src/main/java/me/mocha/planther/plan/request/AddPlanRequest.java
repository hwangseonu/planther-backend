package me.mocha.planther.plan.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddPlanRequest {

    @NotNull(message = "일정 제목은 빈칸일 수 없습니다.")
    @NotBlank(message = "일정 제목은 빈칸일 수 없습니다.")
    @Size(min = 1, max = 255, message = "일정 제목은 1자이상 255자 이하여야합니다.")
    private String title;

    @NotNull(message = "일정 내용은 빈칸일 수 없습니다.")
    @NotBlank(message = "일정 내용은 빈칸일 수 없습니다.")
    private String content;

    @NotNull(message = "일정 종류를 지정해 주세요.")
    @NotBlank(message = "일정 종류를 지정해 주세요.")
    private String type;

    @NotNull(message = "년도를 지정해주세요.")
    @Range(min = 2018, max = 2100, message = "년도의 범위를 벗어났습니다.")
    private Integer year;

    @NotNull(message = "월을 지정해주세요.")
    @Range(min = 1, max = 12, message = "월의 범위를 벗어났습니다.")
    private Integer month;

    @NotNull(message = "일을 지정해주세요.")
    @Range(min = 1, max = 31, message = "일의 범위를 벗어났습니다.")
    private Integer day;

}