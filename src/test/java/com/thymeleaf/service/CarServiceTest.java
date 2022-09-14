package com.thymeleaf.service;

import com.thymeleaf.entity.Car;
import com.thymeleaf.entity.CarDetail;
import com.thymeleaf.excepton.CarNotFoundException;
import com.thymeleaf.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
class CarServiceTest {

    @Autowired
    private CarService carService;

    @MockBean
    CarRepository carRepository;

    @Test
    void getAllCars() {
        //Given
        List<Car> expectedCarsList = preparedCarsList();
        when(carRepository.findAll()).thenReturn(expectedCarsList);
        //When
        List<Car> actualCarsList = carService.getAllCars();
        //Then
        assertEquals(expectedCarsList, actualCarsList);
    }

    @Test
    void addNewCar() {
        //Given
        Car expectedCar = preparedCar();
        when(carRepository.save(expectedCar)).thenReturn(expectedCar);
        //When
        Car actualCar = carService.addNewCar(expectedCar);
        //Then
        assertEquals(expectedCar, actualCar);
    }

    @Test
    void getCarById() {
        //Given
        Car expectedCar = preparedCar();
        when(carRepository.findById(expectedCar.getId())).thenReturn(Optional.of(expectedCar));
        //When
        Car actualCar = carService.getCarById(expectedCar.getId());
        //Then
        assertEquals(expectedCar, actualCar);
    }

    @Test
    void getCarByIdWithException() {
        //Given
        Car expectedCar = preparedCar();
        when(carRepository.findById(expectedCar.getId())).thenReturn(Optional.ofNullable(null));
        //When
        //Then
        assertThrows(CarNotFoundException.class, () -> carService.getCarById(expectedCar.getId()));
    }

    @Test
    void deleteCarById() {
        //Given
        Car expectedCar = preparedCar();
        when(carRepository.findById(expectedCar.getId())).thenReturn(Optional.of(expectedCar));
        //When
        carService.deleteCarById(expectedCar.getId());
        //Then
        verify(carRepository, times(1)).deleteById(expectedCar.getId());
    }

    @Test
    void getAllCarsByKeywordAndPriceBetweenAndYearBetween() {
        //Given
        List<Car> expectedCarsList = preparedCarsList();
        when(carRepository.findByKeywordAndPriceBetweenAndYearBetween("A41", 1000.0, 20000.0, 2000, 2003)).thenReturn(expectedCarsList);
        //When
        List<Car> actualCarsList = carService.getAllCarsByKeywordAndPriceBetweenAndYearBetween("A41", 1000.0, 20000.0, 2000, 2003);
        //Then
        assertEquals(expectedCarsList, actualCarsList);
    }

    private Car preparedCar() {
        Car car = new Car(1, "Audi", "A41", 2002, "black", 12000.0, null, new CarDetail(1, 200000, "petrol", 120, "manual", "front wheel system", "coupe", 4, 4, "Germany", false, true, "used"));
        return car;
    }

    private List<Car> preparedCarsList() {
        List<Car> cars = new ArrayList<>();
        cars.add(preparedCar());
        return cars;
    }

}