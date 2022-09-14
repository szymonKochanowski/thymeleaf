package com.thymeleaf.controller;

import com.thymeleaf.entity.Basket;
import com.thymeleaf.entity.Car;
import com.thymeleaf.entity.CarDetail;
import com.thymeleaf.entity.User;
import com.thymeleaf.service.BasketService;
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

import javax.transaction.Transactional;
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
class BasketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BasketService basketService;

    @MockBean
    UserService userService;

    @MockBean
    CarService carService;

    @WithMockUser(username = "test123", roles = "USER", password = "test123")
    @Test
    void showBasket() throws Exception {
        List<Basket> baskets = new ArrayList<>();
        baskets.add(preparedBasket());
        when(userService.getUser()).thenReturn(preparedUser());
        when(basketService.getAllCarsInBasketsByUser(preparedUser())).thenReturn(baskets);

        mockMvc.perform(get("/basket"))
                .andExpect(status().isOk())
                .andExpect(view().name("basket"))
                .andExpect(model().attributeExists("baskets"))
                .andExpect(model().attributeExists("pageTitle"))
                .andExpect(model().attributeExists("loggedInUser"))
                .andReturn();
    }

    @WithMockUser(username = "test123", roles = "USER", password = "test123")
    @Test
    @Transactional
    void addCarToBasket() throws Exception {
        Car car = preparedCar();
        Integer carId = car.getId();
        Integer quantity = 1;
        when(userService.getUser()).thenReturn(preparedUser());
        when(basketService.addCarToBasket(carId, 1, preparedUser())).thenReturn(1);
        when(basketService.countTotalCostInBaskets(preparedUser())).thenReturn(car.getPrice());
        when(carService.getCarById(carId)).thenReturn(car);

        mockMvc.perform(post("/basket/add/{carId}/{quantity}", carId, quantity))
                .andExpect(view().name("redirect:/"))
                .andExpect(flash().attributeExists("message"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }

    @WithMockUser(username = "test123", roles = "USER", password = "test123")
    @Test
    void deleteCarFromBasket() throws Exception {
        Basket basket = preparedBasket();
        when(basketService.findBasketById(basket.getId())).thenReturn(basket);
        doNothing().when(basketService).deleteCarFromBasket(basket.getId());
        when(userService.getUser()).thenReturn(preparedUser());
        when(basketService.countTotalCostInBaskets(preparedUser())).thenReturn(preparedCar().getPrice());

        mockMvc.perform(post("/basket/delete/{basketId}", basket.getId()))
                .andExpect(flash().attributeExists("message"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/basket"))
                .andReturn();
    }

    @WithMockUser(username = "test123", roles = "USER", password = "test123")
    @Test
    void deleteAllBoughtCarsFromBasketsAndRedirectToPaypalWebsite() throws Exception {
        when(userService.getUser()).thenReturn(preparedUser());
        doNothing().when(basketService).deleteAllBoughtCarsFromBasketsByUser(preparedUser());

        mockMvc.perform(post("/basket/buy"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:https://www.paypal.com/pl/home"))
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

    private Basket preparedBasket() {
        Basket basket = new Basket();
        basket.setId(1);
        basket.setCar(preparedCar());
        basket.setUser(preparedUser());
        basket.setQuantity(1);
        basket.setSubtotal(12000.0);
        return basket;
    }


}