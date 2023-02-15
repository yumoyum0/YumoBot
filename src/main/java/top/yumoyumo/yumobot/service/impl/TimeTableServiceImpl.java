package top.yumoyumo.yumobot.service.impl;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.yumoyumo.yumobot.constants.RedisKeyConst;
import top.yumoyumo.yumobot.pojo.TimeTableBean;
import top.yumoyumo.yumobot.service.RedisService;
import top.yumoyumo.yumobot.service.TimeTableService;
import top.yumoyumo.yumobot.util.TimeTableUtil;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;


/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/10/31 20:55
 **/
@Service
@Slf4j
public class TimeTableServiceImpl implements TimeTableService {


    @Value("${timetable.id}")
    public String timeTableId;
    @Resource
    private RedisService redisService;

    private static final String timetableFormat =
            """
                    课程：%s
                    地点：%s
                    起始：%s-%s
                    -----------------
                    """;

    @Override
    public String getTimeTableByDay(LocalDateTime localDateTime) {
        Calendar calendar = new Calendar.Builder().setDate(localDateTime.getYear(), localDateTime.getMonth().getValue(), localDateTime.getDayOfMonth()).build();
        String s = redisService.getString(RedisKeyConst.getTimeTableKey(timeTableId));
        List<TimeTableBean> timeTableBeanList = new Gson().fromJson(s, new TypeToken<List<TimeTableBean>>() {
        }.getType());
        StringBuilder builder = new StringBuilder();
        builder.append(
                """
                        课表
                        -----------------
                        """);
        if (!timeTableBeanList.isEmpty())
            timeTableBeanList.sort(Comparator.comparingInt(TimeTableBean::getSectionStart));
        for (TimeTableBean timeTableBean : timeTableBeanList) {
            if (timeTableBean.getWeekArray().contains(calendar.get(Calendar.WEEK_OF_YEAR) - 35) && localDateTime.getDayOfWeek().getValue() == timeTableBean.getDay()) {
                builder.append(String.format(timetableFormat,
                        timeTableBean.getName(),
                        timeTableBean.getLocale(),
                        TimeTableUtil.getStart(timeTableBean.getSectionStart()), TimeTableUtil.getEnd(timeTableBean.getSectionEnd())));
            }
        }
        return builder.toString().equals("") ?
                "今日无课喵" :
                builder.append(localDateTime.toLocalDate()).toString();
    }
}
