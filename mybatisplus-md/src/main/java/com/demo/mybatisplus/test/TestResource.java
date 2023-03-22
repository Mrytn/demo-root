package com.demo.mybatisplus.test;

import com.demo.mybatisplus.StudentService;
import com.demo.mybatisplus.entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 * @author PeiDong Yan
 * @date 2023/02/14 9:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestResource {

    @Resource
    private StudentService studentService;

    @Test
    public void t1() {
        Student student = new Student()
                .setName("张三")
                .setAge(12)
                .setDate(LocalDate.now());
        studentService.save(student);
    }

    @Test
    public void t2() {
        Student info = studentService.getInfo(1);
        System.out.println("c");
    }
}
