package com.demo.test.module.util.stream.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author PeiDong Yan
 * @date 2023/02/14 10:30
 */
@Data
@Accessors(chain = true)
public class Person {
    public Person() {
    }

    public Person(String code, String name, Integer chengJi, Integer age, BigDecimal value) {
        this.code = code;
        this.name = name;
        this.chengJi = chengJi;
        this.age = age;
        this.value = value;
    }

    private String code;
    private String name;
    private Integer chengJi;
    private Integer age;
    private BigDecimal value;

}
