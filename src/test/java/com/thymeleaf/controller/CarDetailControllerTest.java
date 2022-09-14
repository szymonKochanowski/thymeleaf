package com.thymeleaf.controller;

import com.thymeleaf.entity.Car;
import com.thymeleaf.entity.CarDetail;
import com.thymeleaf.excepton.CarNotFoundException;
import com.thymeleaf.service.CarDetailService;
import com.thymeleaf.service.CarService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class CarDetailControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CarService carService;

    @MockBean
    private CarDetailService detailService;

    @Test
    void getCarWithDetails() throws Exception {
        Integer carId = preparedCar().getId();
        when(carService.getCarById(carId)).thenReturn(preparedCar());

        mockMvc.perform(get("/carDetail/{id}", carId))
                .andExpect(model().attribute("car", preparedCar()))
                .andExpect(view().name("car_detail"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void getCarWithDetailsWithException() throws Exception {
        Integer incorrectCarId = 122;
        when(carService.getCarById(incorrectCarId)).thenThrow(CarNotFoundException.class);

        mockMvc.perform(get("/carDetail/{id}", incorrectCarId))
                .andExpect(view().name("redirect:/"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }

    private Car preparedCar() {
        Car car = new Car(1, "Audi", "A41", 2002, "black", 12000.0, null, new CarDetail(1, 200000, "petrol", 120, "manual", "front wheel system", "coupe", 4, 4, "Germany", false, true, "used"));
        return car;
    }

}