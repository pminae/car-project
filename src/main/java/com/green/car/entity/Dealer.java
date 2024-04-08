package com.green.car.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "cars") //toString 할 때 cars 필드 제외
@SequenceGenerator(name = "dealer", sequenceName = "dealer_seq", allocationSize = 1, initialValue = 1)
//직렬화 프로세스 중에 cars 필드를 무시하게 변경 (하이버네이트가 생성한 필드를 무시하도록 설정)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Dealer {
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "dealer_seq")
    @Column(name = "dealer_id")
    @Id
    private Long id;
    private String name;
    private String phone;
    private String location;
    //cascade : 차가 삭제될 때, 그 글을 작성한 dealer도 같이 삭제되게 설정
    @JsonIgnore //직렬화 제외시킬 필드
    @OneToMany(mappedBy = "dealer", cascade = CascadeType.ALL)
    private List<Car> cars = new ArrayList<>();
}
