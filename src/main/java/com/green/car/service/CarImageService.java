package com.green.car.service;

import com.green.car.entity.CarImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CarImageService {
    //차량 이미지 엔티티 저장
    void saveCarImage(CarImage carImg, MultipartFile carImgFile) throws IOException;
}
