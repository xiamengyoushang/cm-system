package com.excelsecu.cmsystem.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderTimeOutTask {

    /**
     * cron表达式：
     * Seconds Minutes Hours DayofMonth Month DayofWeek [Year]
     * 秒 分 时 天(月) 月 天(周) 年份(一般省略)
     * https://www.jianshu.com/p/b4b8950fb987
     * */
    @Scheduled(cron = "0 0/3 * ? * ?")
    private void orderTimeOutCheck(){
        log.info("每3分钟扫描一次，扫描设定超时时间之前下的订单");
    }

}
