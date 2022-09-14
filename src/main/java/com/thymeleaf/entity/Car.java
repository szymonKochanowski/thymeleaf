package com.thymeleaf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 45)
    private String model;

    @Column(nullable = false, length = 45)
    private String mark;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false, length = 45)
    private String color;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String pictureUrl;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_detail_id")
    private CarDetail carDetail;

}
