package com.example.testmd.common.bo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author PeiDong Yan
 * @date 2023/02/14 10:30
 */
@Data
public class Student{
    public Student() {
    }

    public Student(String code, String name, Long chengJi, Integer age, BigDecimal value) {
        this.code = code;
        this.name = name;
        this.chengJi = chengJi;
        this.age = age;
        this.value = value;
    }

    private String code;
    private String name;
    private Long chengJi;
    private Integer age;
    private BigDecimal value;

}
