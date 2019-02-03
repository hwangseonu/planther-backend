package me.mocha.planther.user.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequest {

    @NotNull(message = "아이디는 빈칸일 수 없습니다.")
    @NotBlank(message = "아이디는 빈칸일 수 없습니다.")
    @Size(min = 4, message = "아이디는 4자 이상이어야합니다.")
    private String username;

    @NotNull(message = "비밀번호는 빈칸일 수 없습니다.")
    @NotBlank(message = "비밀번호는 빈칸일 수 없습니다.")
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;

    @NotNull(message = "이름은 빈칸일 수 없습니다.")
    @NotBlank(message = "이름은 빈칸일 수 없습니다.")
    @Pattern(regexp = "^[가-힣]{2,5}$", message = "이름은 한글 2~5자여야합니다.")
    private String name;

    @NotNull(message = "학년을 지정해주세요.")
    @Range(min = 1, max = 3, message = "학년이 범위를 벗어났습니다.")
    private Integer grade;

    @NotNull(message = "반을 지정해주세요.")
    @Range(min = 1, max = 4, message = "1반부터 4반까지만 있습니다.")
    private Integer cls;

    @NotNull(message = "번호을 지정해주세요.")
    @Range(min = 1, max = 20, message = "1번 부터 20번까지만 있습니다.")
    private Integer number;

}
