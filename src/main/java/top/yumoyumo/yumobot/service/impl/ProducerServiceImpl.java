package top.yumoyumo.yumobot.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yumoyumo.yumobot.pojo.TimeTableBean;
import top.yumoyumo.yumobot.service.ProducerService;
import top.yumoyumo.yumobot.service.RedisService;
import top.yumoyumo.yumobot.util.TimeTableUtil;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static top.yumoyumo.yumobot.config.MqConfig.DELAYED_EXCHANGE_NAME;
import static top.yumoyumo.yumobot.config.MqConfig.DELAYED_ROUTING_KEY;

/**
 * @Author: yumo
 * @Description: 生产者Service实现类
 * @DateTime: 2022/11/3 15:47
 **/
@Service
@Slf4j
public class ProducerServiceImpl implements ProducerService {
    @Resource
    private RabbitTemplate rabbitTemplate;
    public static final String TIMETABLE = "timetable:";

    @Autowired
    private RedisService redisService;

    @Override
    public void sendDelayMsg(String id) {
        String s = redisService.get(TIMETABLE + id);
        List<TimeTableBean> timeTableBeanList = new Gson().fromJson(s, new TypeToken<List<TimeTableBean>>() {
        }.getType());
        int curWeek = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) - 35;
        int curDayOfWeek = LocalDateTime.now().getDayOfWeek().getValue();
        for (TimeTableBean timeTableBean : timeTableBeanList) {
            if (timeTableBean.getWeekarr().contains(curWeek) && curDayOfWeek == timeTableBean.getDay()) {
                String messageBody =
                        "<?xml version='1.0' encoding='UTF-8' standalone='yes' ?>\n" +
                                "<msg serviceID=\"1\" templateID=\"1\" action=\"web\" brief=\"上课提醒\" sourceMsgId=\"0\" url=\"http://yumoyumo.top\" flag=\"37\" adverSign=\"0\" multiMsgFlag=\"0\">\n" +
                                "    <item layout=\"6\" advertiser_id=\"0\" aid=\"0\">\n" +
                                "        <title>上课提醒</title>\n" +
                                "        <summary>课程：" + timeTableBean.getName() + "</summary>\n" +
                                "        <summary>地点：" + timeTableBean.getLocale() + "</summary>\n" +
                                "        <summary>起始: " + TimeTableUtil.getStart(timeTableBean.getSectionstart()) + "-" + TimeTableUtil.getEnd(timeTableBean.getSectionend()) + "</summary>\n" +
                                "    </item>\n" +
                                "    <source name=\"\" icon=\"\" action=\"\" appid=\"-1\" />\n" +
                                "</msg>\n";
                long delayTime = Duration.between(LocalTime.now(), TimeTableUtil.getStart(timeTableBean.getSectionstart())).getSeconds() * 1000;

                rabbitTemplate.convertAndSend(DELAYED_EXCHANGE_NAME, DELAYED_ROUTING_KEY, messageBody,
                        correlationData -> {
                            correlationData.getMessageProperties().setDelay(Math.toIntExact(delayTime - 1000 * 60 * 30));
                            return correlationData;
                        });
                rabbitTemplate.convertAndSend(DELAYED_EXCHANGE_NAME, DELAYED_ROUTING_KEY, messageBody,
                        correlationData -> {
                            correlationData.getMessageProperties().setDelay(Math.toIntExact(delayTime - 1000 * 60 * 15));
                            return correlationData;
                        });
                log.info("当前时间：{},发送一条延迟{}毫秒的信息给队列 delayed.queue:{}", new Date(), delayTime, timeTableBean);
            }
        }
    }
}
