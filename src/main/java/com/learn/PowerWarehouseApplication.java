package com.learn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.learn.mapper")
public class PowerWarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(PowerWarehouseApplication.class, args);
    }

}
