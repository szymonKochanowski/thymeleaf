package com.thymeleaf.service;

import com.thymeleaf.entity.Car;
import com.thymeleaf.excepton.CarNotFoundException;
import com.thymeleaf.repository.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CarService {

    @Autowired
    CarRepository carRepository;

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car addNewCar(Car car) {
        return carRepository.save(car);
    }

    public Car getCarById(Integer id) {
        Optional<Car> carToUpdate = carRepository.findById(id);
        if(carToUpdate.isPresent()) {
            return carToUpdate.get();
        } else {
            log.error("Car with id: {} not found", id);
            throw new CarNotFoundException("Car with id: '" + id + "' not found in database");
        }
    }

    public void deleteCarById(Integer id) {
        carRepository.deleteById(id);
    }

    public List<Car> getAllCarsByKeywordAndPriceBetweenAndYearBetween(String keyword, Double startPrice, Double endPrice, Integer startYear, Integer endYear) {
        return carRepository.findByKeywordAndPriceBetweenAndYearBetween(keyword, startPrice, endPrice, startYear, endYear);
    }

}
