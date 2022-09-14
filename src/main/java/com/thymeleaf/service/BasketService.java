package com.thymeleaf.service;

import com.thymeleaf.entity.Basket;
import com.thymeleaf.entity.Car;
import com.thymeleaf.entity.User;
import com.thymeleaf.excepton.BasketNotFoundException;
import com.thymeleaf.repository.BasketReposiotry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BasketService {

    @Autowired
    BasketReposiotry basketReposiotry;

    @Autowired
    CarService carService;

    public List<Basket> getAllCarsInBasketsByUser(User user) {
       return basketReposiotry.findByUser(user);
    }

    @Transactional
    public Integer addCarToBasket(Integer carId, Integer quantity, User user) {
        Integer addedQuantity = quantity;
        Car car = carService.getCarById(carId);
        Basket basket = basketReposiotry.findByUserAndCar(user, car);
        Double subtotal = null;
        if (basket != null) {
            addedQuantity = basket.getQuantity() + quantity;
            basket.setQuantity(addedQuantity);
            subtotal = car.getPrice() * addedQuantity;
            basket.setSubtotal(subtotal);
        } else {
            basket = new Basket();
            basket.setCar(car);
            basket.setQuantity(quantity);
            basket.setUser(user);
            subtotal = car.getPrice() * quantity;
            basket.setSubtotal(subtotal);
        }
        basketReposiotry.save(basket);

        return addedQuantity;
    }

    public void deleteCarFromBasket(Integer basketId) {
        basketReposiotry.deleteById(basketId);
    }

    public Basket findBasketById(Integer basketId) {
        Optional<Basket> optionalBasket = basketReposiotry.findBasketById(basketId);
        if (optionalBasket.isEmpty()) {
            log.error("Basket with id: {} not found", basketId);
            throw new BasketNotFoundException("Basket with id " + basketId + " not found");
        }
        return optionalBasket.get();
    }

    public Double countTotalCost(List<Basket> allCarsInBasketsByUser) {
        Optional<Double> optionalTotalCost = allCarsInBasketsByUser.stream()
                .map(basket -> basket.getSubtotal())
                .reduce((subtotal1, subtotal2) -> subtotal1 + subtotal2);
        if (optionalTotalCost.isPresent()) {
            return optionalTotalCost.get();
        } else {
            return null;
        }
    }

    public void deleteAllBoughtCarsFromBasketsByUser(User user) {
        getAllCarsInBasketsByUser(user).forEach(basket ->
            deleteCarFromBasket(basket.getId()));
    }

    public Double countTotalCostInBaskets(User user) {
        List<Basket> allCarsInBasketsByUser = getAllCarsInBasketsByUser(user);
        return countTotalCost(allCarsInBasketsByUser);
    }
}
