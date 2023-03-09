package com.example.testmd.common.test;

import com.example.testmd.common.bo.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author PeiDong Yan
 * @date 2023/02/14 9:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestResource {

    @Test
    public void t1() {
        List<Student> studentList = new ArrayList();
        studentList.add(new Student("a1","am1",1L,2,new BigDecimal(3)));
        studentList.add(new Student("a","am1",1L,2,new BigDecimal(3)));
        studentList.add(new Student("b","bm1",1L,2,new BigDecimal(3)));
        List<Student> collect = studentList.stream().collect(Collectors.groupingBy(Student::getName,
                Collectors.reducing((s, sum) ->
                        new Student(s.getCode(), s.getName(), sum.getChengJi() + sum.getChengJi(),
                                sum.getAge() + s.getAge(), sum.getValue().add(s.getValue())))
        )).entrySet().stream().map(c -> c.getValue().get()).collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    public void t2() {
        DecimalFormat df = new DecimalFormat("0.0%");
        System.out.println(df.format(0*1.0/30));
    }

    @Test
    public void t3() {
//        Map<String, String> map1 = Maps.newHashMap();
        ConcurrentHashMap<String, String> map1 = new ConcurrentHashMap<>();
        map1.put("1", "2");
        map1.put("2", "23");
        System.out.println(map1);
    }
}
