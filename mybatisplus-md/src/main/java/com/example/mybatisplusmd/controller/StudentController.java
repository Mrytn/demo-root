package com.example.mybatisplusmd.controller;

import com.example.mybatisplusmd.StudentService;
import com.example.mybatisplusmd.entity.Student;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author PeiDong Yan
 * @date 2023/03/08 16:20
 */
@RestController
@RequestMapping("student")
public class StudentController {

    @Resource
    private StudentService studentService;

    @GetMapping("t1/{id}")
    public Student t1(@PathVariable("id") Integer id) {
        Student info = studentService.getInfo(id);
        return info;
    }
}
