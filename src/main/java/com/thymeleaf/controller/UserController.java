package com.thymeleaf.controller;

import com.thymeleaf.entity.User;
import com.thymeleaf.excepton.DuplicateUsernameException;
import com.thymeleaf.service.BasketService;
import com.thymeleaf.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    BasketService basketService;

    @GetMapping("/all")
    public String getAllUsers(Model model) {
        log.info("Start to get all users");
        List<User> userList = userService.getAllUsers();
        User loggedInUser = userService.getLogInUser();
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("users", userList);
        return "users";
    }

    @GetMapping("/addUser")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle", "Add New User");
        return "user_form";
    }

    @PostMapping("/addUser")
    public String addNewUser(@Valid @ModelAttribute User user, BindingResult result, RedirectAttributes redirectAttributes) {
        log.info("Start to add a new user: {}", user);
        try {
            //check if all fields with validation annotations from class User are correct
            if (result.hasErrors()) {
                return "user_form";
            }
            User newUser = userService.addNewUser(user);
            redirectAttributes.addFlashAttribute("message", "The User '" + newUser.getUsername() + "' was successfully saved!" +
                    " Now you can login!");
            return "redirect:/";
        } catch (DuplicateUsernameException e) {
            result.rejectValue("username", "error.user", e.getMessage());
            return "user_form";
        }
    }

    @GetMapping("/updateUserProfile")
    public String showEditUserProfileForm(Model model, RedirectAttributes redirectAttributes) {
        String username = userService.getLogInUser().getUsername();
        User userToUpdate = userService.getUserByUsername(username);
        model.addAttribute("user", userToUpdate);
        model.addAttribute("loggedInUser", userToUpdate);
        model.addAttribute("pageTitle", "Edit User with username: '" + userToUpdate.getUsername() + "')");
        return "user_update_form";
    }

    @PostMapping("/updateUserProfile")
    public String editUserProfileForm(Model model, User updatedUser, RedirectAttributes redirectAttributes) {
        log.info("Start to update user profile: {}", updatedUser);
        String username = userService.getLogInUser().getUsername();
        User userFromDb = userService.getUserByUsername(username);
        userService.updateUser(updatedUser, userFromDb);
        redirectAttributes.addFlashAttribute("message", "User '" + updatedUser.getUsername() + "' was successfully updated!");
        return "redirect:/";
    }

    @RequestMapping(value = "/deleteUser/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
    //need to add method delete and get because after delete we want to display a user list (use get method)WebSecurityConfig
    public String deleteUserById(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        log.info("Start to delete user with id: {}", id);
        User loginUser = userService.getLogInUser();
        User userFromDb = userService.findUserById(id);
        //delete all cars in baskets before delete user
        basketService.deleteAllBoughtCarsFromBasketsByUser(userFromDb);
        userService.deleteUserById(id);
        redirectAttributes.addFlashAttribute("message", "The User with ID: '" + id + "' was successfully deleted!");
        if (loginUser.getRole().equals("ROLE_ADMIN")) {
            return "redirect:/user/all";
        } else {
            return "redirect:/logout";
        }
    }

    @GetMapping("/userPanel")
    public String showUserPanel(Model model, RedirectAttributes redirectAttributes) {
        String loggedUsername = userService.getLogInUser().getUsername();
        User user = userService.getUserByUsername(loggedUsername);
        model.addAttribute("user", user);
        return "user_panel";
    }

}
