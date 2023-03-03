package com.example.mybatisplusmd.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatisplusmd.StudentService;
import com.example.mybatisplusmd.entity.Student;
import com.example.mybatisplusmd.mapper.StudentMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PeiDong Yan
 * @date 2023/03/03 16:38
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Override
    public void create(Student student) {
        save(student);
    }

    @Override
    public void update(Student student) {
        saveOrUpdate(student);
    }

    @Override
    public Student getInfo(Integer id) {
        Student student = baseMapper.selectById(id);
        return student;
    }

    @Override
    public List<Student> getList() {
        List<Student> list = lambdaQuery().list();
        return list;
    }
}
