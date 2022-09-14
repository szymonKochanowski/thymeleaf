package com.thymeleaf.service;

import com.thymeleaf.entity.Car;
import com.thymeleaf.entity.CarDetail;
import com.thymeleaf.entity.User;
import com.thymeleaf.excepton.DuplicateUsernameException;
import com.thymeleaf.excepton.IncorrectPasswordException;
import com.thymeleaf.excepton.UserNotFoundException;
import com.thymeleaf.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    PasswordEncoder passwordEncoder;

    @Test
    void getAllUsers() {
        //Given
        List<User> expectedUsersList = preparedUsersList();
        when(userRepository.findAll()).thenReturn(expectedUsersList);
        //When
        List<User> actualUsersList = userService.getAllUsers();
        //Then
        assertEquals(expectedUsersList, actualUsersList);
    }

    @Test
    void addNewUserWithRoleUser() {
        //Given
        User expectedUser = preparedUser();
        String password = expectedUser.getPassword();
        when(passwordEncoder.encode(password)).thenReturn(password);
        when(userRepository.findByUsername(expectedUser.getUsername())).thenReturn(Optional.ofNullable(null));
        when(userRepository.save(expectedUser)).thenReturn(expectedUser);
        //When
        User actualUser = userService.addNewUser(expectedUser);
        //Then
        assertEquals(expectedUser, actualUser);
    }

    @WithMockUser(username = "admin1234", roles = "ADMIN", password = "admin1234")
    @Test
    void addNewUserWithRoleAdmin() {
        //Given
        User admin = new User(4, "admin1234", "admin1234", "ROLE_ADMIN", true, null, null, null);
        User expectedAdmin = preparedAdmin();
        String password = expectedAdmin.getPassword();
        when(passwordEncoder.encode(password)).thenReturn(password);
        when(userRepository.findByUsername(expectedAdmin.getUsername())).thenReturn(Optional.ofNullable(null));
        when(userRepository.findByUsername(admin.getUsername())).thenReturn(Optional.of(admin));
        when(userRepository.save(expectedAdmin)).thenReturn(expectedAdmin);
        //When
        User actualUser = userService.addNewUser(expectedAdmin);
        //Then
        assertEquals(expectedAdmin, actualUser);
    }

    @Test
    void addNewUserWithException() {
        //Given
        User expectedUser = preparedUser();
        String password = expectedUser.getPassword();
        when(passwordEncoder.encode(password)).thenReturn(password);
        when(userRepository.findByUsername(expectedUser.getUsername())).thenReturn(Optional.of(expectedUser));
        //When
        //Then
        assertThrows(DuplicateUsernameException.class, () -> userService.addNewUser(expectedUser));
    }

    @Test
    void deleteUserById() {
        //Given
        Integer carId = preparedCar().getId();
        doNothing().when(userRepository).deleteById(carId);
        //When
        userService.deleteUserById(carId);
        //Then
        verify(userRepository, times(1)).deleteById(carId);
    }

    @WithMockUser(username = "test123", roles = "USER", password = "test123")
    @Test
    void getLogInUser() {
        //Given
        User expectedUser = preparedUser();
        when(userRepository.findByUsername(expectedUser.getUsername())).thenReturn(Optional.of(expectedUser));
        //When
        User actualUser = userService.getLogInUser();
        //Then
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void getLogInUserReturnLogInfo() {
        //Given
        User expectedUser = preparedUser();
        when(userRepository.findByUsername(expectedUser.getUsername())).thenReturn(Optional.ofNullable(null));
        //When
        User actualUser = userService.getLogInUser();
        //Then
        assertNull(actualUser);
    }

    @WithMockUser(username = "test123", roles = "USER", password = "test123")
    @Test
    void getLogInUserReturnOptionalUserAsNull() {
        //Given
        User expectedUser = preparedUser();
        when(userRepository.findByUsername(expectedUser.getUsername())).thenReturn(Optional.ofNullable(null));
        //When
        User actualUser = userService.getLogInUser();
        //Then
        assertNull(actualUser);
    }

    @Test
    void getUserByUsername() {
        //Given
        User expectedUser = preparedUser();
        String username = expectedUser.getUsername();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(expectedUser));
        //When
        User actualUser = userService.getUserByUsername(username);
        //Then
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void getUserByUsernameWithException() {
        //Given
        User expectedUser = preparedUser();
        String username = expectedUser.getUsername();
        when(userRepository.findByUsername(username)).thenReturn(Optional.ofNullable(null));
        //When
        //Then
        assertThrows(UserNotFoundException.class, () -> userService.getUserByUsername(username));
    }

    @Test
    void updateUser() {
        //Given
        LocalDateTime localDateTime = LocalDateTime.of(2022, 8, 14, 14, 28, 00);
        User expectedUser = preparedUser();
        expectedUser.setUpdatedOn(localDateTime);
        when(userRepository.save(expectedUser)).thenReturn(expectedUser);
        //When
        User actualUser = userService.updateUser(expectedUser, expectedUser);
        //Then
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void checkPassword() {
        //Given
        String passwordProvidedByUser =  preparedUser().getPassword();
        String passwordFromDb = "$2a$12$r3HD8f1bogt9mUGlMIB0Q.8qUbGKvYmZgiAFfXjzCHzX/.usM0XEe";
        when(passwordEncoder.matches(passwordProvidedByUser, passwordFromDb)).thenReturn(true);
        //When
        boolean checkPassword = userService.checkPassword(passwordProvidedByUser, passwordFromDb);
        //Then
        assertTrue(checkPassword);
    }

    @Test
    void checkPasswordWithException() {
        //Given
        String passwordProvidedByUser =  preparedUser().getPassword();
        String passwordFromDb = "$2a$12$r3HD8f1bogt9mUGlMIB0Q.8qUbGKvYmZgiAFfXjzCHzX/.usM0X99";
        when(passwordEncoder.matches(passwordProvidedByUser, passwordFromDb)).thenThrow(IncorrectPasswordException.class);
        //When
        //Then
        assertThrows(IncorrectPasswordException.class, () -> userService.checkPassword(passwordProvidedByUser, passwordFromDb));
    }

    @Test
    void findUserById() {
        //Given
        User expectedUser = preparedUser();
        Integer id = expectedUser.getId();
        when(userRepository.findById(id)).thenReturn(Optional.of(expectedUser));
        //When
        User actualUser = userService.findUserById(id);
        //Then
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void findUserByIdWithException() {
        //Given
        Integer incorrectId = 122;
        when(userRepository.findById(incorrectId)).thenReturn(Optional.ofNullable(null));
        //When
        //Then
        assertThrows(UserNotFoundException.class, () -> userService.findUserById(incorrectId));
    }

    @WithMockUser(username = "test123", roles = "USER", password = "test123")
    @Test
    void getUser() {
        //Given
        User expectedUser = preparedUser();
        when(userRepository.findByUsername(expectedUser.getUsername())).thenReturn(Optional.of(expectedUser));
        userService.getLogInUser();
        when(userRepository.findByUsername(expectedUser.getUsername())).thenReturn(Optional.of(expectedUser));
        //When
        User actualUser = userService.getUser();
        //Then
        assertEquals(expectedUser, actualUser);
    }

    private User preparedUser() {
        LocalDateTime localDateTime = LocalDateTime.of(2022, 1, 1, 0, 0);
        User user = new User(1, "test123", "test123", "ROLE_USER", true, localDateTime, localDateTime,null);
        return user;
    }

    private User preparedAdmin() {
        LocalDateTime localDateTime = LocalDateTime.of(2022, 1, 1, 0, 0);
        User admin = new User(2, "admin123", "admin123", "ROLE_ADMIN", true, localDateTime, localDateTime, null);
        return admin;
    }

    private List<User> preparedUsersList() {
        List<User> userList = new ArrayList<>();
        userList.add(preparedUser());
        userList.add(preparedAdmin());
        return userList;
    }

    private Car preparedCar() {
        Car car = new Car(1, "Audi", "A41", 2002, "black", 12000.0, null, new CarDetail(1, 200000, "petrol", 120, "manual", "front wheel system", "coupe", 4, 4, "Germany", false, true, "used"));
        return car;
    }

}