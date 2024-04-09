package com.green.car.service;

import com.green.car.dto.CarAddDto;
import com.green.car.dto.MainCarDto;
import com.green.car.entity.Car;
import com.green.car.entity.CarImage;
import com.sun.tools.javac.Main;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CarService {
    //차량 등록하기
    //addCar 호출할 때 매개변수 dto, List<MultipartFile>
    void addCar(CarAddDto carAddDto, List<MultipartFile> carImgFileList) throws IOException;

    //삭제하기
    void carRemove(Long id);

    //id로 하나 조회하기
    Car findById(Long id);

    //전체 조회하기
    List<Car> carlist();
    
    //전체 조회하기
    List<MainCarDto> getCarList();

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

    //Car엔티티 오브젝트를 MainCarDTO로 변환하기
    default MainCarDto entityObjToDto(Car car, CarImage carImage){
        MainCarDto mainCarDto = MainCarDto.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .color(car.getColor())
                .year(car.getYear())
                .price(car.getPrice())
                .model(car.getModel())
                .registerNumber(car.getRegisterNumber())
                .dealerId(car.getDealer().getId())
                .imgName(carImage.getImgName())
                .build();
        return mainCarDto;
    }
}
