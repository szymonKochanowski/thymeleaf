package com.thymeleaf.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thymeleaf.entity.User;
import com.thymeleaf.excepton.DuplicateUsernameException;
import com.thymeleaf.repository.UserRepository;
import com.thymeleaf.service.BasketService;
import com.thymeleaf.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class UserControllerTest {

    @MockBean
    UserService userService;

    @MockBean
    BasketService basketService;

    @MockBean
    UserRepository userRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @WithMockUser(username = "admin123", roles = {"ADMIN"}, password = "admin123")
    @Test
    void getAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(preparedUserList());
        when(userService.getLogInUser()).thenReturn(preparedUser());

        mockMvc.perform(get("/user/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("loggedInUser"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void showNewUserForm() throws Exception {
        mockMvc.perform(get("/user/addUser"))
                .andExpect(status().isOk())
                .andExpect(view().name("user_form"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("pageTitle"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void addNewUser() throws Exception {
        User newUser = new User(3, "testTest", "testTest", "ROLE_USER", true, null, null, null);
        when(userService.addNewUser(newUser)).thenReturn(newUser);

        mockMvc.perform(post("/user/addUser")
                        .param("id", "3")
                        .param("username", "testTest")
                        .param("password", "testTest")
                        .param("role", "ROLE_USER")
                        .param("enabled", "true"))
                .andExpect(view().name("redirect:/"))
                .andExpect(flash().attributeExists("message"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }

    @Test
    void addNewUserWithResultError() throws Exception {
        User user = preparedUser();
        when(userRepository.findByUsername(user.getUsername())).thenThrow(DuplicateUsernameException.class);
        when(userService.addNewUser(user)).thenReturn(null);

        mockMvc.perform(post("/user/addUser")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(view().name("user_form"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @WithMockUser(username = "test123", roles = "USER", password = "test123")
    @Test
    void showEditUserProfileForm() throws Exception {
        User updateUser = preparedUser();
        String username = updateUser.getUsername();
        when(userService.getLogInUser()).thenReturn(updateUser);
        when(userService.getUserByUsername(username)).thenReturn(updateUser);

        mockMvc.perform(get("/user/updateUserProfile"))
                .andExpect(model().attribute("user", updateUser))
                .andExpect(model().attributeExists("pageTitle"))
                .andExpect(view().name("user_update_form"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @WithMockUser(username = "test123", roles = "USER", password = "test123")
    @Test
    void editUserProfileForm() throws Exception {
        User updateUser = preparedUser();
        String username = updateUser.getUsername();
        when(userService.getLogInUser()).thenReturn(updateUser);
        when(userService.getUserByUsername(username)).thenReturn(updateUser);
        when(userService.updateUser(updateUser, updateUser)).thenReturn(updateUser);

        mockMvc.perform(post("/user/updateUserProfile")
                        .param("id", "1")
                        .param("username", "test123")
                        .param("password", "test123")
                        .param("role", "ROLE_USER")
                        .param("enabled", "true"))
                .andExpect(view().name("redirect:/"))
                .andExpect(flash().attributeExists("message"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }

    @WithMockUser(username = "test123", roles = "USER", password = "test123")
    @Test
    void deleteUserById() throws Exception {
        Integer id = preparedUser().getId();
        when(userService.getLogInUser()).thenReturn(preparedUser());
        when(userService.findUserById(id)).thenReturn(preparedUser());
        doNothing().when(basketService).deleteAllBoughtCarsFromBasketsByUser(preparedUser());
        doNothing().when(userService).deleteUserById(id);

        mockMvc.perform(delete("/user/deleteUser/{id}", id))
                .andExpect(view().name("redirect:/logout"))
                .andExpect(flash().attributeExists("message"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }

    @WithMockUser(username = "test123", roles = "USER", password = "test123")
    @Test
    void showUserPanel() throws Exception {
        when(userService.getLogInUser()).thenReturn(preparedUser());
        when(userService.getUserByUsername(preparedUser().getUsername())).thenReturn(preparedUser());

        mockMvc.perform(get("/user/userPanel"))
                .andExpect(view().name("user_panel"))
                .andExpect(model().attributeExists("user"))
                .andExpect(status().isOk())
                .andReturn();
    }

    private User preparedUser() {
        LocalDateTime localDateTime = LocalDateTime.of(2022, 1, 1, 0, 0);
        User user = new User(1, "test123", "test123", "ROLE_USER", true, localDateTime, localDateTime, null);
        return user;
    }

    private User preparedAdmin() {
        LocalDateTime localDateTime = LocalDateTime.of(2022, 1, 1, 0, 0);
        User admin = new User(2, "admin123", "admin123", "ROLE_ADMIN", true, localDateTime, localDateTime, null);
        return admin;
    }

    private List<User> preparedUserList() {
        List<User> userList = new ArrayList<>();
        userList.add(preparedUser());
        userList.add(preparedAdmin());
        return userList;
    }

}