package com.green.car.service;

import com.green.car.dto.CarAddDto;
import com.green.car.dto.MainCarDto;
import com.green.car.entity.Car;
import com.green.car.entity.CarImage;
import com.green.car.entity.Dealer;
import com.green.car.repository.CarRepository;
import com.green.car.repository.DealerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final DealerRepository dealerRepository;
    private final CarImageService carImageService;

    //차량 등록하기
    @Override
    public void addCar(CarAddDto carAddDto, List<MultipartFile> carImgFileList) throws IOException {
        Car car = dtoToEntity(carAddDto);
        Dealer dealer = dealerRepository.findById(carAddDto.getDealerId()).get();
        car.setDealer(dealer);
        carRepository.save(car);
        //이미지 등록, 이미지 list의 길이만큼 반복
        for (int i=0; i<carImgFileList.size(); i++){
            CarImage carImage = new CarImage();
            //참조타입 필드 지정
            carImage.setCar(car);
            if (i==0){
                //첫번째 이미지 파일일때만 Y값 지정
                carImage.setRepimgYn("Y");
            }else {
                //나머지 이미지 파일은 N값 지정
                carImage.setRepimgYn("N");
            }
            carImageService.saveCarImage(carImage, carImgFileList.get(i));
        }
    }

    //차량 삭제하기
    @Override
    public void carRemove(Long id) {

    }

    //차량 검색하기 (1개)
    @Override
    public Car findById(Long id) {
        return null;
    }

    //전체 자동차 리스트
    @Override
    public List<Car> carlist() {
        List<Car> cars = carRepository.findAll();
        return cars;
    }

    //전체 차량 리스트 MainCarDto 타입의 리스트 리턴
    @Override
    public List<MainCarDto> getCarList() {
        List<Object[]> result = carRepository.getCarList();
        List<MainCarDto> carDtoList = new ArrayList<>();
        for (Object[] obj:result){
            MainCarDto dto = entityObjToDto((Car) obj[0], (CarImage) obj[1]);
            carDtoList.add(dto);
        }
        return carDtoList;
    }
}
