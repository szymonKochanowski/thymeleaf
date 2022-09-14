package com.thymeleaf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer mileage;

    @Column(nullable = false, name = "type_of_fuel")
    private String typeOfFuel;

    @Column(nullable = false)
    private Integer power;

    @Column(nullable = false)
    private String transmission;

    @Column(nullable = false)
    private String drive;

    @Column(nullable = false, name = "body_type")
    private String bodyType;

    @Column(nullable = false, name = "number_of_doors")
    private Integer numberOfDoors;

    @Column(nullable = false, name = "number_of_seats")
    private Integer numberOfSeats;

    @Column(nullable = false, name = "country_of_origin")
    private String countryOfOrigin;

    @Column(nullable = false, name = "air_condition")
    private Boolean airCondition;

    @Column(nullable = false, name = "never_crashed")
    private Boolean neverCrashed;

    @Column(nullable = false, name = "vehicle_condition")
    private String vehicleCondition;

}
