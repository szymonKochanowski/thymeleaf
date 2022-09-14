package com.thymeleaf.service;

import com.thymeleaf.entity.User;
import com.thymeleaf.excepton.DuplicateUsernameException;
import com.thymeleaf.excepton.IncorrectPasswordException;
import com.thymeleaf.excepton.UserNotFoundException;
import com.thymeleaf.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {


    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(12);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User addNewUser(User user) throws DuplicateUsernameException {
        String encodePassword = this.passwordEncoder.encode(user.getPassword());
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        if (optionalUser.isPresent()) {
            log.error("The username '{}' already exists!", user.getUsername());
            throw new DuplicateUsernameException("User with username " + user.getUsername() + " already exists! Please choose another username.");
        }
        user.setUsername(user.getUsername());
        user.setPassword(encodePassword);
        //check if user is log in and user is admin - if yes set admin role during create a new user
        if (getLogInUser() != null && getLogInUser().getRole().equals("ROLE_ADMIN")) {
            log.info("Set 'ROLE_ADMIN' for username: {}", user.getUsername());
            user.setRole("ROLE_ADMIN");
        } else {
            log.info("Set 'ROLE_USER' for username: {}", user.getUsername());
            user.setRole("ROLE_USER");
        }
        user.setEnabled(true);
        user.setCreatedOn(LocalDateTime.now());
        user.setUpdatedOn(null);
        user.setProfilePictureUrl(user.getProfilePictureUrl());
        return userRepository.save(user);
    }

    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

    public User getLogInUser() {
        Optional<Authentication> auth = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());

        String username = null;
        if (auth.isPresent()) {
            username = auth.get().getName();
        } else {
            log.info("No user is logged in.");
            return null;
        }

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            return null;
        }
    }

    public User getUserByUsername(String username) throws IncorrectPasswordException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            log.error("The username '{}' does not exist!", username);
            throw new UserNotFoundException("Username not exist!");
        }
    }

    public User updateUser(User user, User userFromDb) {
        String encodePassword = this.passwordEncoder.encode(user.getPassword());
        user.setId(userFromDb.getId());
        user.setUsername(userFromDb.getUsername());
        //password min length is 6 - it is set in field requirements in user_update_form.html file
        user.setPassword(encodePassword);
        user.setRole(userFromDb.getRole());
        user.setEnabled(userFromDb.getEnabled());
        user.setCreatedOn(userFromDb.getCreatedOn());
        user.setUpdatedOn(LocalDateTime.now());
        user.setProfilePictureUrl(user.getProfilePictureUrl());
        return userRepository.save(user);
    }

    public boolean checkPassword(String passwordProvidedByUser, String passwordFromDb) {
        if (passwordEncoder.matches(passwordProvidedByUser, passwordFromDb)) {
            return true;
        } else {
            log.error("Password is incorrect! Error on method checkPassword");
            throw new IncorrectPasswordException("Incorrect password!");
        }
    }

    public User findUserById(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            log.error("User with id '{}' does not exist!", id);
            throw new UserNotFoundException("User not found!");
        }
    }

    public User getUser() {
        String username = getLogInUser().getUsername();
        return getUserByUsername(username);
    }
}
