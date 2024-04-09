package com.green.car.dto;

import com.green.car.entity.Dealer;
import lombok.*;

@Builder
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainCarDto {
    private Long id;
    private String brand, model, color, registerNumber; //브랜드, 모델, 색상, 등록번호
    private int year, price; //연식, 가격
    private Long dealerId;
    private String imgName;
}
