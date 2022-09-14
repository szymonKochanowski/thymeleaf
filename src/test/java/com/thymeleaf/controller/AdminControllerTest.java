package com.thymeleaf.controller;

import com.thymeleaf.entity.Car;
import com.thymeleaf.entity.CarDetail;
import com.thymeleaf.entity.User;
import com.thymeleaf.repository.UserRepository;
import com.thymeleaf.service.CarService;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @WithMockUser(username = "admin123", roles = "ADMIN", password = "admin123")
    @Test
    void showAdminPanel() throws Exception {
        when(carService.getAllCars()).thenReturn(preparedCarList());
        when(userService.getLogInUser()).thenReturn(preparedAdmin());
        when(userRepository.findByUsername("admin123")).thenReturn(Optional.of(preparedAdmin()));
        mockMvc.perform(get("/admin/adminPanel"))
                .andExpect(status().isOk())
                .andReturn();
    }

    private List<Car> preparedCarList() {
        List<Car> carList = new ArrayList<>();
        carList.add(new Car(1, "Audi", "A41", 2002, "black", 12000.0, null, new CarDetail(1, 200000, "petrol", 120, "manual", "front wheel system", "coupe", 4, 4, "Germany", false, true, "used")));
        carList.add(new Car(2, "BMW", "X51", 2013, "black", 13000.0, null, new CarDetail(2, 240000, "petrol", 149, "automate", "rear wheel system", "SUV", 4, 4, "Germany", true, true, "used")));
        return carList;
    }

    private User preparedAdmin() {
        LocalDateTime localDateTime = LocalDateTime.of(2022, 1, 1, 0, 0);
        User admin = new User(2, "admin123", "admin123", "ROLE_ADMIN", true, localDateTime, localDateTime,null);
        return admin;
    }
}