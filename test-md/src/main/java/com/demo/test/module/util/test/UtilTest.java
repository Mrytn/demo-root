package com.demo.test.module.util.test;

import com.demo.test.module.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author PeiDong Yan
 * @date 2023/02/14 9:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UtilTest {

    @Test
    public void t1() {
        int startNum = DateUtil.getIntOfDate(LocalDate.of(2023, 2, 16).atTime(LocalTime.MIN));
        int endNum = DateUtil.getIntOfDate(LocalDate.of(2023, 2, 16).atTime(LocalTime.MAX));
        LocalDateTime time = DateUtil.getDateFromInt(37191975);
        System.out.println("startNum:"+startNum);
        System.out.println("endNum:"+endNum);
        String v=null;
        System.out.println("33"+v);
        System.out.println(time);
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
