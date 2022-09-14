package com.thymeleaf.controller;

import com.thymeleaf.entity.User;
import com.thymeleaf.excepton.IncorrectPasswordException;
import com.thymeleaf.excepton.UserNotFoundException;
import com.thymeleaf.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/loginPage")
@Slf4j
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping()
    public String showLoginPage() {
        log.info("Start to show login page");
        return "loginPage";
    }

    @PostMapping()
    public String logInUser(Model model, User user, RedirectAttributes redirectAttributes) {
        log.info("Start to log in user: {}", user);
        try {
            User userFromDb = userService.getUserByUsername(user.getUsername());
            userService.checkPassword(user.getPassword(), userFromDb.getPassword());
            model.addAttribute("user", userFromDb);
            redirectAttributes.addFlashAttribute("message", "User '" + user.getUsername() + "' was successfully login!");
            return "redirect:/";
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/loginPage";
        } catch (IncorrectPasswordException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/loginPage";
        }
    }

}
