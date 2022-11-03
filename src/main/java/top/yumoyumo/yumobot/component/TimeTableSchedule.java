package top.yumoyumo.yumobot.component;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Weibo hot list task.
 *
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022 /9/12 17:49
 */
@Component
@Slf4j
public class TimeTableSchedule {
    public static final String LOCAL_URL = "http://127.0.0.1:8088";

    @Scheduled(cron = "0 0 1 * * MON-FRI")
    private void TimeTableJob() {
        Map<String, Object> pram = new HashMap<>();
        pram.put("id", "yumo");
        HttpUtil.get(LOCAL_URL + "/msg/sendDelayMsg", pram);
    }
}
