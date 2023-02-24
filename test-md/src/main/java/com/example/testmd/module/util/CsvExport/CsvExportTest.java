package com.example.testmd.module.util.CsvExport;

import com.example.testmd.module.util.CsvExport.bo.AppStatisticsVO;
import com.example.testmd.module.util.stream.bo.Person;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author PeiDong Yan
 * @date 2023/02/14 9:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CsvExportTest {

    @Test
    public void t1() {
        String title = null;
        String keys = null;
        List<Map<String, Object>> datas = new ArrayList<>();
        List<AppStatisticsVO> statisticsList = Lists.newArrayList();
        List<AppStatisticsVO> channelPush =Lists.newArrayList();
        Map<String, Object> map;
            title = "日期，业务账号，机构，发送数，成功数，失败数，异常数，未反馈数，反馈率，成功率";
            keys = "statisticDate,saName,companyName,sendNum,successNum,failNum,exceptionNum,noFeedbackNum,feedbackRate," +
                    "successRate";
            statisticsList =
                    channelPush.stream().collect(Collectors.groupingBy(m -> m.getCompanyId() + "-" + m.getAccountId(),
                            Collectors.reducing((sum, s) ->
                                    new AppStatisticsVO()
                                            .setCompanyId(s.getCompanyId())
                                            .setAccountId(s.getAccountId())
                                            .setCompanyName(s.getCompanyName())
                                            .setSaName(s.getSaName())
                                            .setSendNum(sum.getSendNum() + s.getSendNum())
                                            .setSuccessNum(sum.getSuccessNum() + s.getSuccessNum())
                                            .setFailNum(sum.getFailNum() + s.getFailNum())
                                            .setExceptionNum(sum.getExceptionNum() + s.getExceptionNum())
                                            .setNoFeedbackNum(sum.getNoFeedbackNum() + s.getNoFeedbackNum())

                            ))).entrySet().stream().map(m -> m.getValue().get()).collect(Collectors.toList());
            if (null != statisticsList && statisticsList.size() > 0) {
                for (AppStatisticsVO detail : statisticsList) {
                    map = new HashMap<>(10);
                    map.put("saName,", detail.getSaName());
                    map.put("companyName", detail.getCompanyId());
                    extracted(map, detail);
                    datas.add(map);
                }
            }
            if (null != statisticsList && statisticsList.size() > 0) {
                for (AppStatisticsVO detail : statisticsList) {
                    map = new HashMap<>(9);
                    map.put("appName,", detail.getAppName());
                    map.put("statisticDate", detail.getStatisticDate());
                    extracted(map, detail);
                    datas.add(map);
                }
            }
        //反馈、成功率
        statisticsList.addAll(channelPush);

        String fileFullPath = "appExport";
//        CsvExportUtil.export(response, fileFullPath, datas, title, keys);
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
