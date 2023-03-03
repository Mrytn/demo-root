package com.example.mybatisplusmd.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author PeiDong Yan
 * @date 2023/02/14 10:30
 */
@Data
@Accessors(chain = true)
@TableName("student")
public class Student {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String code;
    private String name;
    private Long chengJi;
    private Integer age;
    private BigDecimal value;
    private LocalDate date;
    private LocalDateTime time;
    private LocalDateTime time2;
    private LocalDateTime timestamp;
    private Boolean valid;

}
