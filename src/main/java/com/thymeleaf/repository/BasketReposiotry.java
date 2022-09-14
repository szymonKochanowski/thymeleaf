package com.thymeleaf.repository;

import com.thymeleaf.entity.Basket;
import com.thymeleaf.entity.Car;
import com.thymeleaf.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BasketReposiotry extends JpaRepository<Basket, Integer> {

    public List<Basket> findByUser(User user);

    public Basket findByUserAndCar(User user, Car car);

    public Optional<Basket> findBasketById(Integer basketId);
}
