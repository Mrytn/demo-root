package com.example.dubboproductmd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class DemoMdApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoMdApplication.class, args);
    }
}
