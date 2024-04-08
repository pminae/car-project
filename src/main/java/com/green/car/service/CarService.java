package com.green.car.service;

import com.green.car.dto.CarAddDto;
import com.green.car.entity.Car;

import java.util.List;

public interface CarService {
    //차량 등록하기
    void addCar(CarAddDto carAddDto);

    //삭제하기
    void carRemove(Long id);

    //id로 하나 조회하기
    Car findById(Long id);

    //전체 조회하기
    List<Car> carlist();

    //dto->entity
    default Car dtoToEntity(CarAddDto dto){
        Car car = Car.builder()
                .price(dto.getPrice())
                .brand(dto.getBrand())
                .year(dto.getYear())
                .color(dto.getColor())
                .model(dto.getModel())
                .registerNumber(dto.getRegisterNumber())
                .build();
        return car;
    }
}
