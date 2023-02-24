package com.example.testmd.module.util.CsvExport;

import com.example.testmd.module.util.CsvExport.bo.AppStatisticsVO;
import com.example.testmd.module.util.CsvExport.bo.CsvService;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author PeiDong Yan
 * @date 2023/02/23 16:44
 */
@Service
public class CsvServiceImpl implements CsvService {

    @Override
    public void export(HttpServletResponse response) {
        String title = null;
        String keys = null;
        List<Map<String, Object>> datas = new ArrayList<>();
        List<AppStatisticsVO> statisticsList = Lists.newArrayList();
        Map<String, Object> map;
        title = "日期,业务账号,机构,发送数,成功数,失败数,异常数,未反馈数,反馈率,成功率";
        keys = "statisticDate,saName,companyName,sendNum,successNum,failNum,exceptionNum,noFeedbackNum,feedbackRate,successRate";
        AppStatisticsVO appStatisticsVO1 = new AppStatisticsVO()
                .setStatisticDate(LocalDate.now().minusDays(1))
                .setSaName("1")
                .setCompanyName("11")
                .setSendNum(10)
                .setSuccessNum(1)
                .setFailNum(4)
                .setExceptionNum(5)
                .setNoFeedbackNum(6)
                .setFeedbackRate("30%")
                .setSuccessRate("80%");
        AppStatisticsVO appStatisticsVO2 = new AppStatisticsVO()
                .setStatisticDate(LocalDate.now())
                .setSaName("2")
                .setCompanyName("13")
                .setSendNum(10)
                .setSuccessNum(1)
                .setFailNum(4)
                .setExceptionNum(5)
                .setNoFeedbackNum(6)
                .setFeedbackRate("30%")
                .setSuccessRate("80%");
        AppStatisticsVO appStatisticsVO3 = new AppStatisticsVO()
                .setStatisticDate(LocalDate.now().plusDays(1))
                .setSaName("3")
                .setCompanyName("11")
                .setSendNum(10)
                .setSuccessNum(1)
                .setFailNum(4)
                .setExceptionNum(5)
                .setNoFeedbackNum(6)
                .setFeedbackRate("30%")
                .setSuccessRate("80%");
        Collections.addAll(statisticsList, appStatisticsVO1, appStatisticsVO2, appStatisticsVO3);
        if (null != statisticsList && statisticsList.size() > 0) {
            for (AppStatisticsVO detail : statisticsList) {
                map = new HashMap<>(10);
                map.put("saName,", detail.getSaName());
                map.put("companyName", detail.getCompanyId());
                map.put("statisticDate", detail.getStatisticDate());
                map.put("sendNum", detail.getSendNum());
                map.put("successNum,", detail.getSuccessNum());
                map.put("failNum", detail.getFailNum());
                map.put("exceptionNum", detail.getExceptionNum());
                map.put("noFeedbackNum", detail.getNoFeedbackNum());
                map.put("feedbackRate", detail.getFeedbackRate());
                map.put("successRate", detail.getSuccessRate());
//                extracted(map, detail);
                datas.add(map);
            }
        }

        String fileFullPath = "export";
        CsvExportUtil.export(response, fileFullPath, datas, title, keys);
    }

    private Map<String, Object> extracted(Map<String, Object> map, AppStatisticsVO detail) {
        map.put("sendNum", detail.getSendNum());
        map.put("successNum,", detail.getSuccessNum());
        map.put("failNum", detail.getFailNum());
        map.put("exceptionNum", detail.getExceptionNum());
        map.put("noFeedbackNum", detail.getNoFeedbackNum());
        map.put("feedbackRate", detail.getFeedbackRate());
        map.put("successRate", detail.getSuccessRate());
        return map;
    }
}
