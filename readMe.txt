Project created to learn the Thymeleaf and create basic frontend.

Main aim application is management cars salon.

Used language, frameworks and libraries:
- Java,
- Spring Boot: Thymeleaf, JPA, JDBC, Web, Test, Security, Validation,
- BootStrap, HTML, CSS,
- MySql, Hibernate,
- Lombok,
- Maven,
- JUnit.


Application allows to:
- for all users: show all cars in shop and add new user;
- for log in user: show all cars, add new user, go to user panel, edit specific user profile;
- for log in admin: crate new cars, edit existing cars, delete cars, delete users, add new admin, edit admin profile, show admin profile;
- for logged-in users and admins: show the profile picture and basket only;
- count subtotal and total price for cars in basket,
- after click button bought take user to payment page and delete all bought cars from basket


Security is implemented using Spring Security and Spring Boot.
Main security:
- allows changing password and picture profile only for logged-in users and admins,
- allows to create a new user only when all credentials are correct (username must contain between 3 and 4 characters,
password must contain between 8 and 64 characters, profilePicture must contain max 2000 characters),
- allows to create a new car only when all credentials are correct (model must contain between 3 and 45 characters,
mark must contain between 3 and 45 characters, color must contain between 3 and 45 characters,
pictureUrl must contain max 2000 characters)
- show the profile picture only for logged-in users and admins,
- allows accessing for specific endpoints only for users and admins,


Not solved problem:
- decode password in update user form - allows user to only update profilePictureUrl with previous
password (user must provide the password during update because in other case hash code will be encoded again and will change
current user password) and allows user to update only password only when all requirements are met;
- read style.css file in index.html file - not able to read it due to security settings;

Application is tested using JUnit and Mockito - tests coverage in lines is approximately 94%.




