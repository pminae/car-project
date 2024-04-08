package com.green.car.repository;

import com.green.car.entity.Car;
import com.green.car.entity.Dealer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class DealerRepositoryTests {
    @Autowired
    DealerRepository dealerRepository;

    @Test
    public void insertDealerTest(){
        Dealer dealer = Dealer.builder()
                .name("홍길동")
                .phone("01012341234")
                .location("울산 북구")
                .build();

        Car car = Car.builder()
                .brand("현대자동차")
                .model("그랜저")
                .year(2000)
                .color("검정색")
                .price(1800)
                .dealer(dealer)
                .registerNumber("123456789")
                .build();
        //연관관계 설정에 의해 dealerRepository.save 해주면 car Entity도 같이 저장된다.
        List<Car> list = new ArrayList<>();
        list.add(car);
        dealer.setCars(list);
        dealerRepository.save(dealer);
    }
}
