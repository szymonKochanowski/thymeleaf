package com.thymeleaf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    @NotNull(message = "Username can not be null!")
    @Size(min = 3, max = 45)
    private String username;

    @NotNull(message = "Password can not be null!")
    @Size(min = 6, max = 64)
    private String password;

    private String role;

    private Boolean enabled;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    @Size(max = 2000)
    private String profilePictureUrl;

}
