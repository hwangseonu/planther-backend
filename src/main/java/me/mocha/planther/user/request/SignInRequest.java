package me.mocha.planther.user.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInRequest {

    @Size(min = 4, message = "아이디는 4자 이상이어야합니다.")
    private String username;

    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;

}
