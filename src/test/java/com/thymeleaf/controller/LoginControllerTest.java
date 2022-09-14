package com.thymeleaf.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thymeleaf.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @Test
    void showLoginPage() throws Exception {
        mockMvc.perform(get("/loginPage"))
                .andExpect(view().name("loginPage"))
                .andExpect(status().isOk())
                .andReturn();

    }

    @WithMockUser(username = "test123", roles = "USER", password = "test123")
    @Test
    void logInUser() throws Exception {
        // ---------not able to cover that method in test due to additional config in security class and forwardUrl------------
//        User user = preparedUser();
//        String password = user.getPassword();
//        when(userService.getUserByUsername(user.getUsername())).thenReturn(user);
//        when(userService.checkPassword(password, password)).thenReturn(true);

//        mockMvc.perform(post("/loginPage")
//                        .content(objectMapper.writeValueAsString(user))
//                        .content(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(model().attribute("user", user))
//                .andExpect(flash().attributeExists("message"))
//                .andExpect(view().name("redirect:/"))
//                .andExpect(redirectedUrl("/"))
//                .andExpect(status().is3xxRedirection())
//                .andReturn();
    }

//    private User preparedUser() {
//        LocalDateTime localDateTime = LocalDateTime.of(2022, 1, 1, 0, 0);
//        User user = new User(1, "test123", "test123", "ROLE_USER", true, localDateTime, localDateTime,null);
//        return user;
//    }

}