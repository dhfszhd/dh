package com.dhf.dh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dhf.dh.mapper")
public class DhApplication {

    public static void main(String[] args) {
        SpringApplication.run(DhApplication.class, args);
    }

}
