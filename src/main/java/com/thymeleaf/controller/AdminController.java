package com.thymeleaf.controller;

import com.thymeleaf.entity.Car;
import com.thymeleaf.entity.User;
import com.thymeleaf.service.CarService;
import com.thymeleaf.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    CarService carService;

    @Autowired
    UserService userService;

    @GetMapping("/adminPanel")
    public String showAdminPanel(Model model, RedirectAttributes redirectAttributes) {
        List<Car> cars = carService.getAllCars();
        User loggedInUser = userService.getLogInUser();
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("cars", cars);
        return "admin_panel";
    }

}
