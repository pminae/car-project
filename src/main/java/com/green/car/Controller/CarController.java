package com.green.car.Controller;

import com.green.car.dto.CarAddDto;
import com.green.car.dto.MainCarDto;
import com.green.car.entity.Car;
import com.green.car.repository.CarRepository;
import com.green.car.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CarController {

    private final CarService carService;

    @RequestMapping("/cars")
    public ResponseEntity getCars(){
        //자동차를 검색해서 반환
        List<MainCarDto> result = carService.getCarList();
        for (MainCarDto car : result){
            System.out.println(car.toString());
        }
        //stackOverFlow 에러 발생 , json으로 변환할 때
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping("/addCar")
    public ResponseEntity addCar(CarAddDto dto, @RequestParam("uploadFiles")List<MultipartFile> uploadFiles) {
        try {
            carService.addCar(dto, uploadFiles);
        } catch (IOException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity("ok", HttpStatus.OK);
    }

    //http://localhost:8081/image?image=abc.jpg
    @GetMapping("/image")
    public ResponseEntity<?> returnImage(@RequestParam("image") String image){
        String path = "C:\\car\\image\\"; //이미지가 저장된 위치
        Resource resource = new FileSystemResource(path+image);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
