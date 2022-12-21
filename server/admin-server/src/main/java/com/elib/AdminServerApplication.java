package com.elib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdminServerApplication {
    public static void main(String[] args) {
//        System.setProperty("server.port", "9090");  // 여러 개 실행할 때 포트 설정
        SpringApplication.run(AdminServerApplication.class, args);
    }

}
