package com.demo.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.springboot.entity.Person;
import com.demo.springboot.entity.Student;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author PeiDong Yan
 * @date 2023/03/03 17:01
 */
@Mapper
public interface PersonMapper extends BaseMapper<Person> {
    Student select(Long id);
}
