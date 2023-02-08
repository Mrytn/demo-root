package com.example.apppushmd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ApppushMdApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApppushMdApplication.class, args);
    }

}
