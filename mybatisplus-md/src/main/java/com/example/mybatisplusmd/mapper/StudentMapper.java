package com.example.mybatisplusmd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplusmd.entity.Student;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author PeiDong Yan
 * @date 2023/03/03 17:01
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    Student select(Long id);
}
