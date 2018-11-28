package me.mocha.calendar.payload.response.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignInResponse {

    @NotNull
    @JsonProperty("access")
    private String accessToken;

    @JsonProperty("refresh")
    private String refreshToken;

}
