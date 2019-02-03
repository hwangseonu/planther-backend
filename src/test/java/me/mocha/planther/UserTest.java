package me.mocha.planther;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test1SignInNotFound() throws Exception {
        mockMvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"test\", \"password\": \"test1234\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test2SignUpSuccess() throws Exception {
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"test\", \"password\": \"test1234\", \"name\": \"테스트\", \"grade\": 1, \"cls\": 1, \"number\": 1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void test3SignUpConflict() throws Exception {
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"test\", \"password\": \"test1234\", \"name\": \"테스트\", \"grade\": 1, \"cls\": 1, \"number\": 1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void test4SignInSuccess() throws Exception {
        mockMvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"test\", \"password\": \"test1234\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
