package com.example.testmd.module.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

/**
 * @author limengsheng
 * @date 2021/11/4
 */
public final class DateUtil {
    public final static ZoneOffset CHINA = ZoneOffset.of("+8");
    /**
     * 时间基准值
     */
    private final static Instant BASE_TIME_LINE = LocalDateTime.of(2021,
            11, 5, 0, 0, 0, 0).toInstant(CHINA);

    /**
     * 以2021-11-1 00:00:00作为基准值计算时间的int数值
     *
     * @param localDateTime 当地日期时间
     * @return {@link Integer}
     */
    public static int getIntOfDate(LocalDateTime localDateTime) {
        long secondsFromBase = BASE_TIME_LINE.until(localDateTime.toInstant(CHINA), ChronoUnit.SECONDS);
        return (int)secondsFromBase;
    }

    public static LocalDateTime getDateFromInt(int secondsFromBase) {
        return LocalDateTime.ofInstant(BASE_TIME_LINE.plusSeconds(secondsFromBase), CHINA);
    }

}
