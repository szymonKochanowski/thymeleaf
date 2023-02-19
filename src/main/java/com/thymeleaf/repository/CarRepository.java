package com.thymeleaf.repository;

import com.thymeleaf.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    @Query(value = "select * from heroku_4cc95115bcc07f5.car c where " +
            "((c.model like %:keyword%) or (c.mark like %:keyword%) or (c.color like %:keyword%)) " +
            "and ((:startPrice is null or :endPrice is null) or c.price between :startPrice and :endPrice) " +
            "and ((:startYear is null or :endYear is null) or c.year between :startYear and :endYear);",
            nativeQuery = true)
    List<Car> findByKeywordAndPriceBetweenAndYearBetween(
            @Param(value = "keyword") String keyword,
            @Param(value = "startPrice") Double startPrice,
            @Param(value = "endPrice") Double endPrice,
            @Param(value = "startYear") Integer startYear,
            @Param(value = "endYear") Integer endYear);
}
