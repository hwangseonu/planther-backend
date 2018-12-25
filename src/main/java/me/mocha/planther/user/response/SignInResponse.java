package me.mocha.planther.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignInResponse {

    private String access;
    private String refresh;

}
