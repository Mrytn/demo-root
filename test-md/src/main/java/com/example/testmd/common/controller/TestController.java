package com.example.testmd.common.controller;

import com.example.testmd.module.util.CsvExport.bo.CsvService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author PeiDong Yan
 * @date 2023/02/07 14:46
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Resource
    private CsvService csvService;

    @GetMapping("t1")
    public void push() {
        String c = "cc_我的_33_hh";
        String str = "33";
        System.out.println("push");
        System.out.println(c);
    }

    @GetMapping("export")
    public void push(HttpServletResponse response) {
        csvService.export(response);
    }
}
