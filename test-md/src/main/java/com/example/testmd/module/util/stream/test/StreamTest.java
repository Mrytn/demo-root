package com.example.testmd.module.util.stream.test;

import com.example.testmd.module.util.DateUtil;
import com.example.testmd.module.util.stream.bo.Person;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.alibaba.fastjson.JSONPatch.OperationType.add;

/**
 * @author PeiDong Yan
 * @date 2023/02/14 9:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StreamTest {

    @Test
    public void t1() {
        Person p1=new Person().setName("张三").setCode("11").setAge(1).setChengJi(1);
        Person p2=new Person().setName("张三").setCode("11").setAge(2).setChengJi(2);
        Person p3=new Person().setName("张三").setCode("11").setAge(3).setChengJi(3);
        Person p4=new Person().setName("李四").setCode("12").setAge(3).setChengJi(1);
        Person p5=new Person().setName("李四").setCode("12").setAge(2).setChengJi(1);
        Person p6=new Person().setName("王五").setCode("13").setAge(4).setChengJi(1);
        List<Person> list = Lists.list(p1,p2,p3,p4,p5,p6);
        List<Person> result = list.stream().collect(Collectors.groupingBy(m -> m.getName() + m.getCode(),
                Collectors.reducing((sum, s) ->
                new Person().setName(addNameStr(s.getName()))
                        .setCode(s.getCode())
                        .setChengJi(sum.getChengJi() + s.getChengJi())
        ))).entrySet().stream().map(m -> m.getValue().get()).collect(Collectors.toList());
        System.out.println(result);
    }

    String addNameStr(String str) {
        return str + "rr";
    }

    @Test
    public void t2() {
        DecimalFormat df = new DecimalFormat("00%");
        System.out.println(df.format(1-20.0/30));
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
