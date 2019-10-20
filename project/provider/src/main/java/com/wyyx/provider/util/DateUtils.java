package com.wyyx.provider.util;

import org.springframework.stereotype.Component;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


/**
 * @author ltl
 * @date 2019/10/20 21:09
 */
@Component
public class DateUtils {



    //获取当天剩余的秒数
    public static long getLeftSecond() {
        LocalDateTime midnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        // 毫秒
        // long millSeconds = ChronoUnit.MILLIS.between(LocalDateTime.now(), midnight);
        //秒
        long seconds = ChronoUnit.SECONDS.between(LocalDateTime.now(), midnight);
        return seconds;
    }


}