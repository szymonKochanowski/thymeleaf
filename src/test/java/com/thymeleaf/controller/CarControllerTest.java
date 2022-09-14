package com.thymeleaf.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thymeleaf.entity.Car;
import com.thymeleaf.entity.CarDetail;
import com.thymeleaf.entity.User;
import com.thymeleaf.excepton.CarNotFoundException;
import com.thymeleaf.repository.CarRepository;
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

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class CarControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CarService carService;

    @MockBean
    UserService userService;

    @MockBean
    CarRepository carRepository;

    @Test
    void getAllCarsByKeywordAndPriceAndYear() throws Exception {
        Car car = preparedCar();
        when(carService.getAllCarsByKeywordAndPriceBetweenAndYearBetween(car.getColor(), 10000.0, 22000.0, 2000, 2022)).thenReturn(preparedCars());
        when(userService.getLogInUser()).thenReturn(preparedUser());

        mockMvc.perform(get("/")
                .param("keyword", car.getColor())
                .param("priceFrom", "10000")
                .param("priceTo", "22000")
                .param("yearFrom", "2000")
                .param("yearTo", "2022"))
                .andExpect(model().attributeExists("cars"))
                .andExpect(model().attributeExists("loggedInUser"))
                .andExpect(view().name("index"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void getAllCarWithoutParams() throws Exception {
        when(carService.getAllCars()).thenReturn(preparedCars());
        when(userService.getLogInUser()).thenReturn(preparedUser());

        mockMvc.perform(get("/"))
                .andExpect(model().attributeExists("cars"))
                .andExpect(model().attributeExists("loggedInUser"))
                .andExpect(view().name("index"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @WithMockUser(username = "admin12345", roles = "ADMIN", password = "admin12345")
    @Test
    void showNewCarForm() throws Exception {
        mockMvc.perform(get("/addCar"))
                .andExpect(model().attributeExists("car"))
                .andExpect(model().attributeExists("pageTitle"))
                .andExpect(view().name("car_form"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @WithMockUser(username = "admin12345", roles = "ADMIN", password = "admin12345")
    @Test
    void addNewCar() throws Exception {
        when(carService.addNewCar(preparedCar())).thenReturn(preparedCar());

        mockMvc.perform(post("/addCar")
                .content(objectMapper.writeValueAsString(preparedCar()))
                        .contentType("application/json"))
                .andExpect(view().name("redirect:/"))
                .andExpect(flash().attributeExists("message"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }

    @WithMockUser(username = "admin12345", roles = "ADMIN", password = "admin12345")
    @Test
    void showEditCarForm() throws Exception {
        Car car = preparedCar();
        Integer carId = car.getId();
        when(carService.getCarById(carId)).thenReturn(car);

        mockMvc.perform(get("/updateCar/{id}", carId))
                .andExpect(model().attributeExists("car"))
                .andExpect(model().attributeExists("pageTitle"))
                .andExpect(view().name("car_form"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @WithMockUser(username = "admin12345", roles = "ADMIN", password = "admin12345")
    @Test
    void showEditCarFormWithException() throws Exception {
        Integer incorrectCarId = 122;
        when(carService.getCarById(incorrectCarId)).thenThrow(CarNotFoundException.class);

        mockMvc.perform(get("/updateCar/{id}", incorrectCarId))
                .andExpect(view().name("redirect:/"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }

    @WithMockUser(username = "admin12345", roles = "ADMIN", password = "admin12345")
    @Test
    void deleteCarById() throws Exception {
        Integer carId = preparedCar().getId();
        doNothing().when(carService).deleteCarById(carId);

        mockMvc.perform(get("/deleteCar/{id}", carId))
                .andExpect(view().name("redirect:/admin/adminPanel"))
                .andExpect(flash().attributeExists("message"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

    }

    private User preparedUser() {
        LocalDateTime localDateTime = LocalDateTime.of(2022, 1, 1, 0, 0);
        User user = new User(1, "test123", "test123", "ROLE_USER", true, localDateTime, localDateTime,null);
        return user;
    }

    private Car preparedCar() {
        Car car = new Car(1, "Audi", "A41", 2002, "black", 12000.0, null, new CarDetail(1, 200000, "petrol", 120, "manual", "front wheel system", "coupe", 4, 4, "Germany", false, true, "used"));
        return car;
    }

    private Car preparedCar2() {
        Car car2 = new Car(2, "X5", "BMW", 2012, "black", 15000.0, null, new CarDetail(2, 200000, "petrol", 120, "manual", "front wheel system", "coupe", 4, 4, "Germany", false, true, "used"));
        return car2;
    }

    private List<Car> preparedCars() {
        List<Car> cars = new ArrayList<>();
        cars.add(preparedCar());
        cars.add(preparedCar2());
        return cars;
    }

}