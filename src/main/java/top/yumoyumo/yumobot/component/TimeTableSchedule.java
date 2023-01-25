package top.yumoyumo.yumobot.component;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: yumo
 * @Description: 课表提醒定时任务
 * @DateTime: 2022 /9/12 17:49
 */
@Component
@Slf4j
public class TimeTableSchedule {
    public static final String DISPATCH_URL = "http://127.0.0.1:8088";

    @Scheduled(cron = "0 0 1 * * MON-FRI")
    private void TimeTableJob() {
        Map<String, Object> pram = Map.of("id", "yumo");
        HttpUtil.get(DISPATCH_URL + "/msg/sendTimeTableMsg", pram);
    }
}
