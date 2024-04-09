package com.green.car.repository;

import com.green.car.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    //전체 목록 검색하기
    @Query("select c, i from Car c left outer join CarImage i on i.car = c" +
        " where i.repimgYn='Y'")
    List<Object[]> getCarList();
}
