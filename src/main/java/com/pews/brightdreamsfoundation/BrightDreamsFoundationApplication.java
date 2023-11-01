package com.pews.brightdreamsfoundation;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.pews.brightdreamsfoundation.mapper")
public class BrightDreamsFoundationApplication {

    public static void main(String[] args) {
        SpringApplication.run(BrightDreamsFoundationApplication.class, args);
    }

}
