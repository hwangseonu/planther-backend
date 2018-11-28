package me.mocha.calendar.payload.request.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class SignInRequest {

    @NotNull
    private String username;

    @NotNull
    private String password;

}
