package com.example.demoroot.controller;

import com.example.demoroot.pojo.DemoTask;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author PeiDong Yan
 * @date 2023/01/16 10:23
 */
@Log4j2
@RestController
@RequestMapping("demo1")
public class DemoController {

    @GetMapping("t1")
    public String t1(){
        String result = "t1";
        return "t1";
    }

    @GetMapping("t2")
    public String t2(){
        DemoTask d1 = new DemoTask().setName("1");
        DemoTask d2 = new DemoTask().setName("2");
        DemoTask d3 = new DemoTask().setName("3");
        DemoTask d4 = new DemoTask().setName("4");
//        messageProduceDynamicExecutor.execute(d3);
//        messageProduceDynamicExecutor.execute(d4);

        return "t2";
    }
}
