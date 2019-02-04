package me.mocha.planther.plan.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdatePlanRequest {

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

}