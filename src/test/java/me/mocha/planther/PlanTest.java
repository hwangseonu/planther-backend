package me.mocha.planther;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.mocha.planther.common.security.jwt.JwtProvider;
import me.mocha.planther.common.security.jwt.JwtType;
import me.mocha.planther.plan.request.AddPlanRequest;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlanTest {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void beforeSignUp() throws Exception {
        SignUpRequest request = new SignUpRequest("test", "test1234", "테스트", 1, 1, 1);
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        request = new SignUpRequest("test1", "test1234", "테스트", 2, 2, 2);
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void test1NewPlanBadRequest() throws Exception {
        String access = jwtProvider.generateToken("test", JwtType.ACCESS);
        AddPlanRequest request = new AddPlanRequest("test", "testContent", "test", 2019, 1, 1);
        mockMvc.perform(post("/plans")
                .header("Authorization", "Bearer " + access)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test2NewPlanSuccess() throws Exception {
        String access = jwtProvider.generateToken("test", JwtType.ACCESS);
        AddPlanRequest request = new AddPlanRequest("test", "test", "event", 2019, 1, 1);
        mockMvc.perform(post("/plans")
                .header("Authorization", "Bearer " + access)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("test"))
                .andExpect(jsonPath("$.content").value("test"))
                .andExpect(jsonPath("$.type").value("event"))
                .andExpect(jsonPath("$.year").value(2019))
                .andExpect(jsonPath("$.month").value(1))
                .andExpect(jsonPath("$.day").value(1))
                .andExpect(jsonPath("$.classId").value("101"));
    }

    @Test
    public void test3GetPlanNotFound() throws Exception {
        String access = jwtProvider.generateToken("test", JwtType.ACCESS);
        mockMvc.perform(get("/plans/2")
                .header("Authorization", "Bearer " + access)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test4GetPlanForbidden() throws Exception {
        String access = jwtProvider.generateToken("test1", JwtType.ACCESS);
        mockMvc.perform(get("/plans/1")
                .header("Authorization", "Bearer " + access)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void test5GetPlanSuccess() throws Exception {
        String access = jwtProvider.generateToken("test", JwtType.ACCESS);
        mockMvc.perform(get("/plans/1")
                .header("Authorization", "Bearer " + access)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("test"))
                .andExpect(jsonPath("$.content").value("test"))
                .andExpect(jsonPath("$.type").value("event"))
                .andExpect(jsonPath("$.year").value(2019))
                .andExpect(jsonPath("$.month").value(1))
                .andExpect(jsonPath("$.day").value(1))
                .andExpect(jsonPath("$.classId").value("101"));
    }

    @Test
    public void test6DeletePlanNotFound() throws Exception {
        String access = jwtProvider.generateToken("test", JwtType.ACCESS);
        mockMvc.perform(delete("/plans/2")
                .header("Authorization", "Bearer " + access)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test7DeletePlanForbidden() throws Exception {
        String access = jwtProvider.generateToken("test1", JwtType.ACCESS);
        mockMvc.perform(delete("/plans/1")
                .header("Authorization", "Bearer " + access)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void test8DeletePlanSuccess() throws Exception {
        String access = jwtProvider.generateToken("test", JwtType.ACCESS);
        mockMvc.perform(delete("/plans/1")
                .header("Authorization", "Bearer " + access)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
