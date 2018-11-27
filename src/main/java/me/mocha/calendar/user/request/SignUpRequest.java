package me.mocha.calendar.user.request;

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
    private String name;

    @NotNull
    private String cls;

    @NotNull
    private String id;


    public SignUpRequest(String username, String password, String name, String cls, String id) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.cls = cls;
        this.id = id;
    }
}
