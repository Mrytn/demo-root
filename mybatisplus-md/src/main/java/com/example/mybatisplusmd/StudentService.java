package com.example.mybatisplusmd;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatisplusmd.entity.Student;

import java.util.List;

/**
 * @author PeiDong Yan
 * @date 2023/03/03 16:37
 */
public interface StudentService extends IService<Student> {

    void create(Student student);

    void update(Student student);

    Student getInfo(Integer id);

    List<Student> getList();

}
