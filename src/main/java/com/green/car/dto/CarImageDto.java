package com.green.car.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarImageDto {
    private Long id;
    private String imgName;         //이미지 이름
    private String oriImgName;      //원본이름
    private String imgUrl;          //전체경로
    private String repimgYn;        //대표이미지여부
}
