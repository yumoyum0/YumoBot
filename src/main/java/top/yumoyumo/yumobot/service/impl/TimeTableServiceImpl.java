package top.yumoyumo.yumobot.service.impl;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import top.yumoyumo.yumobot.pojo.TimeTableBean;
import top.yumoyumo.yumobot.service.RedisService;
import top.yumoyumo.yumobot.service.TimeTableService;
import top.yumoyumo.yumobot.util.TimeTableUtil;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import static top.yumoyumo.yumobot.service.impl.ProducerServiceImpl.TIMETABLE;


/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/10/31 20:55
 **/
@Service
@PropertySource("classpath:yumobot.properties")
@Slf4j
public class TimeTableServiceImpl implements TimeTableService {


    @Value("${timetable.id}")
    public String timeTableId;
    @Resource
    private RedisService redisService;

    @Override
    public String getTimeTableByDay(LocalDateTime localDateTime) {
        Calendar calendar = new Calendar.Builder().setDate(localDateTime.getYear(), localDateTime.getMonth().getValue(), localDateTime.getDayOfMonth()).build();
        String s = redisService.get(TIMETABLE + timeTableId);
        List<TimeTableBean> timeTableBeanList = new Gson().fromJson(s, new TypeToken<List<TimeTableBean>>() {
        }.getType());
        StringBuilder builder = new StringBuilder();
        builder.append("""
                课表
                -----------------
                """);
        if (!timeTableBeanList.isEmpty())

            timeTableBeanList.sort(Comparator.comparingInt(TimeTableBean::getSectionstart));
        for (TimeTableBean timeTableBean : timeTableBeanList) {
            if (timeTableBean.getWeekarr().contains(calendar.get(Calendar.WEEK_OF_YEAR) - 35) && localDateTime.getDayOfWeek().getValue() == timeTableBean.getDay()) {
                builder.append(
                        "课程：" + timeTableBean.getName() + "\n" +
                                "地点：" + timeTableBean.getLocale() + "\n" +
                                "起始：" + TimeTableUtil.getStart(timeTableBean.getSectionstart()) + "-" + TimeTableUtil.getEnd(timeTableBean.getSectionend()) + "\n" +
                                "-----------------\n");
            }
        }
        return builder.toString().equals("") ? "今日无课喵" : builder.append(localDateTime.toLocalDate()).toString();
    }
}
