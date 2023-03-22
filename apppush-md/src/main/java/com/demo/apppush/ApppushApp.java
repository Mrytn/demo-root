package com.demo.apppush;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ApppushApp {

    public static void main(String[] args) {
        SpringApplication.run(ApppushApp.class, args);
    }

}
