package com.example.demoroot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
        //(scanBasePackages = {"com.example"})
public class BootDemo1Application {

    public static void main(String[] args) {
        SpringApplication.run(BootDemo1Application.class, args);
    }

}
