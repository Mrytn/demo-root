package com.demo.test.module.util.CsvExport;

import com.demo.test.module.util.CsvExport.bo.CsvService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author PeiDong Yan
 * @date 2023/02/07 14:46
 */
@RestController
@RequestMapping("csv")
public class CsvController {

    @Resource
    private CsvService csvService;

    @GetMapping("export")
    public void push(HttpServletResponse response) {
        csvService.export(response);
    }
}
