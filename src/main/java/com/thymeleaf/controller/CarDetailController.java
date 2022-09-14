package com.thymeleaf.controller;

import com.thymeleaf.entity.Car;
import com.thymeleaf.entity.User;
import com.thymeleaf.excepton.CarNotFoundException;
import com.thymeleaf.service.CarDetailService;
import com.thymeleaf.service.CarService;
import com.thymeleaf.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequestMapping("/carDetail")
public class CarDetailController {

    @Autowired
    private CarDetailService detailService;

    @Autowired
    private CarService carService;

    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public String getCarWithDetails(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        log.info("Start to get car details for car with id: {}.", id);
        try {
            Car carWithDetails = carService.getCarById(id);
            User loggedInUser = userService.getLogInUser();
            model.addAttribute("loggedInUser", loggedInUser);
            model.addAttribute("car", carWithDetails);
            return "car_detail";
        } catch (CarNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/";
        }
    }

}
