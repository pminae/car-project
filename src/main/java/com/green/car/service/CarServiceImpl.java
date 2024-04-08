package com.green.car.service;

import com.green.car.dto.CarAddDto;
import com.green.car.entity.Car;
import com.green.car.entity.Dealer;
import com.green.car.repository.CarRepository;
import com.green.car.repository.DealerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final DealerRepository dealerRepository;

    //차량 등록하기
    @Override
    public void addCar(CarAddDto carAddDto) {
        Car car = dtoToEntity(carAddDto);
        Dealer dealer = dealerRepository.findById(carAddDto.getDealerId()).get();
        car.setDealer(dealer);
        carRepository.save(car);
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
}
