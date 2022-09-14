package com.thymeleaf.service;

import com.thymeleaf.entity.Basket;
import com.thymeleaf.entity.Car;
import com.thymeleaf.entity.CarDetail;
import com.thymeleaf.entity.User;
import com.thymeleaf.excepton.BasketNotFoundException;
import com.thymeleaf.repository.BasketReposiotry;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
class BasketServiceTest {

    @Autowired
    private BasketService basketService;

    @MockBean
    private BasketReposiotry basketReposiotry;

    @MockBean
    private CarService carService;

    @Test
    void getAllCarsInBasketsByUser() {
        //Given
        List<Basket> expectedBasketsList = preparedBasketsList();
        when(basketReposiotry.findByUser(preparedUser())).thenReturn(expectedBasketsList);
        //When
        List<Basket> actualBasketsList = basketService.getAllCarsInBasketsByUser(preparedUser());
        //Then
        assertEquals(expectedBasketsList, actualBasketsList);
    }

    @Test
    void addCarToBasketWhenBasketIsEmpty() {
        //Given
        Car car = preparedCar();
        Integer expectedQuantity = 1;
        when(carService.getCarById(car.getId())).thenReturn(car);
        when(basketReposiotry.findByUserAndCar(preparedUser(), car)).thenReturn(null);
        when(basketReposiotry.save(preparedBasket())).thenReturn(preparedBasket());
        //When
        Integer actualQuantity = basketService.addCarToBasket(car.getId(), 1, preparedUser());
        //Then
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void addCarToBasketWhenBasketAlreadyContainsOneCar() {
        //Given
        Car car = preparedCar();
        Integer expectedQuantity = 2;
        when(carService.getCarById(car.getId())).thenReturn(car);
        when(basketReposiotry.findByUserAndCar(preparedUser(), car)).thenReturn(preparedBasket());
        when(basketReposiotry.save(preparedBasket())).thenReturn(preparedBasket());
        //When
        Integer actualQuantity = basketService.addCarToBasket(car.getId(), 1, preparedUser());
        //Then
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void deleteCarFromBasket() {
        //Given
        Integer basketId = preparedBasket().getId();
        //When
        basketService.deleteCarFromBasket(basketId);
        //Then
        verify(basketReposiotry, times(1)).deleteById(basketId);
    }

    @Test
    void findBasketById() {
        //Given
        Basket expectedBasket = preparedBasket();
        Integer basketId = expectedBasket.getId();
        when(basketReposiotry.findBasketById(basketId)).thenReturn(Optional.of(expectedBasket));
        //When
        Basket actualBasket = basketService.findBasketById(basketId);
        //Then
        assertEquals(expectedBasket, actualBasket);
    }

    @Test
    void findBasketByIdWithException() {
        //Given
        Basket expectedBasket = new Basket();
        Integer basketId = expectedBasket.getId();
        when(basketReposiotry.findBasketById(basketId)).thenReturn(Optional.ofNullable(null));
        //When
        //Then
        assertThrows(BasketNotFoundException.class, () -> basketService.findBasketById(basketId));
    }

    @Test
    void countTotalCost() {
        //Given
        Basket basket = preparedBasket();
        Double expectedTotalCost = basket.getSubtotal();
        //When
        Double actualTotalCost = basketService.countTotalCost(preparedBasketsList());
        //Then
        assertEquals(expectedTotalCost, actualTotalCost);
    }

    @Test
    void countTotalCostReturnNull() {
        //Given
        List<Basket> baskets = new ArrayList<>();
        //When
        Double actualTotalCost = basketService.countTotalCost(baskets);
        //Then
        assertNull(actualTotalCost);
    }

    @Test
    void deleteAllBoughtCarsFromBasketsByUser() {
        //Given
        Basket basket = preparedBasket();
        Integer basketId = basket.getId();
        when(basketReposiotry.findByUser(preparedUser())).thenReturn(preparedBasketsList());
        doNothing().when(basketReposiotry).deleteById(basketId);
        //When
        basketService.deleteAllBoughtCarsFromBasketsByUser(preparedUser());
        //Then
        verify(basketReposiotry, times(1)).deleteById(basketId);
    }

    @Test
    void countTotalCostInBaskets() {
        //Given
        Basket basket = preparedBasket();
        Double expectedTotalCost = basket.getSubtotal();
        when(basketReposiotry.findByUser(preparedUser())).thenReturn(preparedBasketsList());
        //When
        Double actualTotalCost = basketService.countTotalCostInBaskets(preparedUser());
        //Then
        assertEquals(expectedTotalCost, actualTotalCost);
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
        basket.setSubtotal(preparedCar().getPrice());
        return basket;
    }

    private List<Basket> preparedBasketsList() {
        List<Basket> baskets = new ArrayList<>();
        baskets.add(preparedBasket());
        return baskets;
    }

}