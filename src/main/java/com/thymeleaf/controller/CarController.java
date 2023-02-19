package com.thymeleaf.controller;

import com.thymeleaf.entity.Car;
import com.thymeleaf.entity.User;
import com.thymeleaf.excepton.CarNotFoundException;
import com.thymeleaf.service.CarService;
import com.thymeleaf.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
public class CarController {

    @Autowired
    CarService carService;

    @Autowired
    UserService userService;

    @GetMapping("/getCars")
    public String getAllCars(Model model,
                             @RequestParam(required = false) String keyword,
                             @RequestParam(required = false) Double startPrice,
                             @RequestParam(required = false) Double endPrice,
                             @RequestParam(required = false) Integer startYear,
                             @RequestParam(required = false) Integer endYear) {
        if (keyword != null || startPrice != null && endPrice != null || startYear != null && endYear != null) {
            log.info("Start to get all cars by keyword: {}, price between: {} and {}, year between: {}, and {}", keyword, startPrice, endPrice, startYear, endYear);
            model.addAttribute("cars", carService.getAllCarsByKeywordAndPriceBetweenAndYearBetween(keyword, startPrice, endPrice, startYear, endYear));
        } else {
            log.info("Start to get all cars");
            List<Car> cars = carService.getAllCars();
            model.addAttribute("cars", cars);
        }
        User loggedInUser = userService.getLogInUser();
        model.addAttribute("loggedInUser", loggedInUser);
        return "index";
    }

    @GetMapping("/addCar")
    public String showNewCarForm(Model model) {
        model.addAttribute("car", new Car());
        model.addAttribute("pageTitle", "Add new car");
        return "car_form";
    }

    @PostMapping("/addCar")
    public String addNewCar(Car car, RedirectAttributes redirectAttributes) {
        log.info("Start to add a new car: {}", car);
        carService.addNewCar(car);
        redirectAttributes.addFlashAttribute("message", "The car " + car.getMark() + " " + car.getModel() + " was successfully saved and added to the car table!");
        return "redirect:/";
    }

    //We don't need to create additional put method to update car - we just used redirection to user_form and use post method
    @GetMapping("/updateCar/{id}")
    public String showEditCarForm(@PathVariable(value = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        log.info("Start to get car with id: {}", id);
        try {
            Car updateCar = carService.getCarById(id);
            model.addAttribute("car", updateCar);
            model.addAttribute("pageTitle", "Edit Car (ID: " + id + ")");
            return "car_form";
        } catch (CarNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/deleteCar/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteCarById(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        log.info("Start to delete car with id: {}", id);
        carService.deleteCarById(id);
        redirectAttributes.addFlashAttribute("message", "The Car with ID: '" + id + "' was successfully deleted!");
        return "redirect:/admin/adminPanel";
    }

}
