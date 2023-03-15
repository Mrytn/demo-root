package com.example.springbootmd.test;

import com.example.springbootmd.PersonService;
import com.example.springbootmd.StudentService;
import com.example.springbootmd.entity.Person;
import com.example.springbootmd.entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author PeiDong Yan
 * @date 2023/02/14 9:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestResource {

    @Resource
    private StudentService studentService;

    @Resource
    private PersonService personService;

    @Test
    public void t1() {
        Student student = new Student()
                .setName("张三")
                .setAge(12)
                .setDate(LocalDate.now());
        studentService.save(student);
    }

    @Test
    public void insertPerson() {
        Person person = new Person()
                .setName("张三3")
                .setAge(13)
                .setDate(LocalDate.now());
        personService.save(person);
    }

    @Test
    public void t2() {
        Student info = studentService.getInfo(1);
        Person person = new Person().setName("33").setAge(11).setC(3);
        String s = "33";
        Integer f=5;
        int cd=5;
        get1(person,s,cd,f);
        System.out.println("c");
    }

    void get1(Person person, String s, int cd,Integer f) {
        person.setName("22").setAge(22).setC(2);
        s = "22";
        cd=2;
        f=6;
    }
    @Test
    public void t3() {
        List<String> stringList = new ArrayList<>();
        stringList.add("a");
        stringList.add("b");
        stringList.add("c");
        stringList.add("i");
        stringList.add("j");
        stringList.add("a");

        //一、求交集
        //方法1：直接通过retainAll直接过滤
        List<String> stringList1 =  new ArrayList<>(Arrays.asList("a,b,c,d,e,f,g,h".split(",")));
        stringList1.retainAll(stringList);
        System.out.println("交集1: " + stringList1);

        //方法2：通过过滤掉存在于stringList的数据
        List<String> stringList1_2 = new ArrayList<>(Arrays.asList("a,b,c,d,e,f,g,h".split(",")));
        List<String> strings = stringList1_2.stream()
                .filter(item -> stringList.contains(item))
                .collect(Collectors.toList());
        System.out.println("交2：" + strings);

        //二、并集
        //有重并集
        List<String> stringList2 =  new ArrayList<>(Arrays.asList("a,b,c,d,e,f,g,h".split(",")));
        stringList2.addAll(stringList);
        System.out.println("并集: " + stringList2);

        //无重并集
        List<String> stringList2_2 =  new ArrayList<>(Arrays.asList("a,b,c,d,e,f,g,h".split(",")));
        List<String> stringList_1 =  new ArrayList<>(Arrays.asList("a,b,c,i,j,a".split(",")));
        stringList2_2.removeAll(stringList_1);
        stringList_1.addAll(stringList2_2);

        System.out.println("无重并集: " + stringList_1);

        //三、求差集
        //方法1：直接使用removeAll()方法
        List<String> stringList3 =  new ArrayList<>(Arrays.asList("a,b,c,d,e,f,g,h".split(",")));
        stringList3.removeAll(stringList);
        System.out.println("差集1: " + stringList3);

        //方法2：通过过滤掉不存在于stringList的数据，然后和本数组进行交集处理
        List<String> stringList3_2 = new ArrayList<>(Arrays.asList("a,b,c,d,e,f,g,h".split(",")));
//        stringList3_2.retainAll(stringList3_2.stream()
//                .filter(item -> !stringList.contains(item))
//                .collect(toList()));
//        System.out.println("差集2：" + stringList3_2);
//
//        SpringApplication.run(DemoApplication.class, args);
    }

    @Test()
    public void  t13() {
        List<Person> list = null;
        List<Person> list2 = new ArrayList<>();
//        for (int i = 0; i < list.size(); i++) {
//
//        }
        for (Person person : list) {
            person.getAge();
        }
        for (Person person : list2) {
            person.getAge();
        }

        List<Student> list1 = studentService.lambdaQuery().eq(Student::getAge, 111).list();
        System.out.println(33);
    }
}

