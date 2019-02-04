package me.mocha.planther;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.mocha.planther.common.security.jwt.JwtProvider;
import me.mocha.planther.common.security.jwt.JwtType;
import me.mocha.planther.user.request.SignInRequest;
import me.mocha.planther.user.request.SignUpRequest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTest {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void test1SignInNotFound() throws Exception {
        SignInRequest request = new SignInRequest("test", "test1234");
        mockMvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test2SignUpSuccess() throws Exception {
        SignUpRequest request = new SignUpRequest("test", "test1234", "테스트", 1, 1, 1);
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("test"))
                .andExpect(jsonPath("$.name").value("테스트"))
                .andExpect(jsonPath("$.grade").value(1))
                .andExpect(jsonPath("$.cls").value(1))
                .andExpect(jsonPath("$.number").value(1));
    }

    @Test
    public void test3SignUpConflict() throws Exception {
        SignUpRequest request = new SignUpRequest("test", "test1234", "테스트", 1, 1, 1);
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void test4SignInSuccess() throws Exception {
        SignInRequest request = new SignInRequest("test", "test1234");
        mockMvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void test5GetUserNotFound() throws Exception {
        String token = jwtProvider.generateToken("test1", JwtType.ACCESS);
        mockMvc.perform(get("/users")
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test6GetUserSuccess() throws Exception {
        String token = jwtProvider.generateToken("test", JwtType.ACCESS);
        mockMvc.perform(get("/users")
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("test"))
                .andExpect(jsonPath("$.name").value("테스트"))
                .andExpect(jsonPath("$.grade").value(1))
                .andExpect(jsonPath("$.cls").value(1))
                .andExpect(jsonPath("$.number").value(1));
    }

}
