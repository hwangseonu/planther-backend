package me.mocha.calendar.payload.request.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class SignUpRequest {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private Integer grade;

    @NotNull
    private Integer cls;

    @NotNull
    private Integer number;

}
