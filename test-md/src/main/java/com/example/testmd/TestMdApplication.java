package com.example.testmd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TestMdApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestMdApplication.class, args);
    }

}
