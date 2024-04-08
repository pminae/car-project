package com.green.car.Controller;

import com.green.car.dto.CarAddDto;
import com.green.car.entity.Car;
import com.green.car.repository.CarRepository;
import com.green.car.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CarController {

    private final CarService carService;

    @RequestMapping("/cars")
    public ResponseEntity getCars(){
        //자동차를 검색해서 반환
        List<Car> result = carService.carlist();
        for (Car car : result){
            System.out.println(car.toString());
        }
        //stackOverFlow 에러 발생 , json으로 변환할 때
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping("addCar")
    public ResponseEntity addCar(@RequestBody CarAddDto dto){
        carService.addCar(dto);
        return new ResponseEntity("ok", HttpStatus.OK);
    }
}
