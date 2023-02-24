package com.example.testmd.module.util.CsvExport.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Accessors(chain = true)
public class AppStatisticsVO implements Serializable {

    private static final long serialVersionUID = -3105122594032355650L;
    /**
     * 统计时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate statisticDate;

    /**
     *业务账号id
     */
    private Integer accountId;

    /**
     * 业务账号
     */
    private String saName;

    /**
     * 业务类型
     */
    private Integer saTypeId;

    /**
     * 业务类型名称
     */
    private String saTypeName;

    /**
     * appId
     */
    private Integer appId;

    /**
     * app名称
     */
    private String appName;

    /**
     * companyId
     */
    private Integer companyId;

    /**
     * company名称
     */
    private String companyName;

    /**
     *总发送量
     */
    private Integer sendNum;

    /**
     *成功量
     */
    private Integer successNum;

    /**
     *失败量
     */
    private Integer failNum;

    /**
     *异常量
     */
    private Integer exceptionNum;

    /**
     *未反馈量
     */
    private Integer noFeedbackNum;

    /**
     *反馈率
     */
    private String feedbackRate;

    /**
     *成功率
     */
    private String successRate;
}
