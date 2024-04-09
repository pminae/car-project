package com.green.car.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//프로젝트 외부에 저장된 이미지를 사용하고자 할 때는 WebMvcConfigurer인터페이스를 구현하여 설정
//addResourceHandlers 메소드를 오버라이드
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${uploadPath}")
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")   //웹에서 접근할 url 경로
                .addResourceLocations(uploadPath);              //실제 파일이 존재하는 경로 "file://경로"
    }
}
