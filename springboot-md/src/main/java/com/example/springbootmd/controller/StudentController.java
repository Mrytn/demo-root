package com.example.springbootmd.controller;

import com.example.springbootmd.StudentService;
import com.example.springbootmd.entity.Student;
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
