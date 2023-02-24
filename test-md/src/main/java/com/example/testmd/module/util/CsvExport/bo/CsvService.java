package com.example.testmd.module.util.CsvExport.bo;

import javax.servlet.http.HttpServletResponse;

/**
 * @author PeiDong Yan
 * @date 2023/02/23 16:44
 */
public interface CsvService {

    void export(HttpServletResponse response);
}
