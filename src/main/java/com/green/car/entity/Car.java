package com.green.car.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "car_seq", sequenceName = "car_seq", allocationSize = 1, initialValue = 1)
public class Car extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "car_seq")
    @Column(name = "car_id")
    private Long id;
    private String brand, model, color, registerNumber; //브랜드, 모델, 색상, 등록번호
    private int year, price; //연식, 가격
    @ManyToOne(fetch = FetchType.LAZY)
    private Dealer dealer;
}
