package me.mocha.planther.user.request;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequest {

    @Size(min = 4, message = "아이디는 4자 이상이어야합니다.")
    private String username;

    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;

    @Size(min = 2, max = 5, message = "이름은 2글자 이상 5글자 이하여야합니다.")
    private String name;

    @Range(min = 1, max = 3, message = "학년이 범위를 벗어났습니다.")
    private Integer grade;

    @Range(min = 1, max = 4, message = "1반부터 4반까지만 있습니다.")
    private Integer cls;

    @Range(min = 1, max = 20, message = "1번 부터 20번까지만 있습니다.")
    private Integer number;

}
