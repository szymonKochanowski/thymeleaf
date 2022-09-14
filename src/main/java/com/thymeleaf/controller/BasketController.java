package com.thymeleaf.controller;

import com.thymeleaf.entity.Basket;
import com.thymeleaf.entity.User;
import com.thymeleaf.service.BasketService;
import com.thymeleaf.service.CarService;
import com.thymeleaf.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/basket")
public class BasketController {

    @Autowired
    BasketService basketService;

    @Autowired
    UserService userService;

    @Autowired
    CarService carService;

    @GetMapping()
    public String showBasket(Model model, RedirectAttributes redirectAttributes) {
        log.info("Start to show basket");
            User loggedInUser = userService.getUser();
            List<Basket> baskets = basketService.getAllCarsInBasketsByUser(loggedInUser);
            model.addAttribute("baskets", baskets);
            model.addAttribute("pageTitle", "Basket");
            model.addAttribute("loggedInUser", loggedInUser);
            return "basket";
    }

    @PostMapping("/add/{carId}/{quantity}")
    public String addCarToBasket(Model model, @PathVariable Integer carId, @PathVariable Integer quantity, RedirectAttributes redirectAttributes, HttpSession httpSession) {
        log.info("Start to add car to basket");
            User user = userService.getUser();
            basketService.addCarToBasket(carId, quantity, user);
            Double totalCost = basketService.countTotalCostInBaskets(user);
            httpSession.setAttribute("totalCost", totalCost);
            redirectAttributes.addFlashAttribute("message", "Car " + carService.getCarById(carId).getModel() + " added to basket");
            return "redirect:/";
    }

    @PostMapping("/delete/{basketId}")
    public String deleteCarFromBasket(@PathVariable Integer basketId, RedirectAttributes redirectAttributes, HttpSession httpSession) {
        log.info("Start to delete car from basket");
            basketService.findBasketById(basketId);
            basketService.deleteCarFromBasket(basketId);
            User user = userService.getUser();
            Double totalCost = basketService.countTotalCostInBaskets(user);
            httpSession.setAttribute("totalCost", totalCost);
            redirectAttributes.addFlashAttribute("message", "Car deleted from basket successfully");
            return "redirect:/basket";
    }

    @PostMapping("/buy")
    public String deleteAllBoughtCarsFromBasketsAndRedirectToPaypalWebsite() {
        log.info("Start to buy all cars in basket");
        User user = userService.getUser();
        basketService.deleteAllBoughtCarsFromBasketsByUser(user);
        log.info("All cars in basket was deleted");
        return "redirect:https://www.paypal.com/pl/home";
    }

}
