package com.green.car.service;

import com.green.car.entity.CarImage;
import com.green.car.repository.CarImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class CarImageServiceImpl implements CarImageService {

    //application.properties에 있는 값을 매핑
    @Value("${carImgLocation}")
    private String carImageLocation;
    //의존주입
    private final CarImageRepository carImageRepository;
    private final FileService fileService;

    //파일업로드
    @Override
    public void saveCarImage(CarImage carImg, MultipartFile carImgFile) throws IOException {
        //경로 : carImageLocation,
        //원본이름
        String oriImgName = carImgFile.getOriginalFilename();
        //파일이 있을때만 업로드
        if (oriImgName != null) {
            //uploadFile을 호출하면 경로에 해당 이미지가 업로드 되고,
            //저장된 파일이름을 리턴함!
            String imgName = fileService.uploadFile(carImageLocation, oriImgName, carImgFile.getBytes());
            //경로+파일명
            String imgUrl = "/images/carimg/"+imgName;
            //dog1.jpg, dsfdkfljd.jpg, images/carimg/dsfdkfljd.jpg
            carImg.update(oriImgName, imgName, imgUrl);
            //데이터베이스에 저장
            carImageRepository.save(carImg);
        }
    }
}
