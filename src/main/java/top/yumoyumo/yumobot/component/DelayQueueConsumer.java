package top.yumoyumo.yumobot.component;

import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.message.data.SimpleServiceMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.yumoyumo.yumobot.annotation.VirtualThread;

import javax.annotation.Resource;
import java.util.Date;

import static top.yumoyumo.yumobot.config.MqConfig.CUSTOM_QUEUE_NAME;
import static top.yumoyumo.yumobot.config.MqConfig.TIMETABLE_QUEUE_NAME;

/**
 * @Author: yumo
 * @Description: 延迟队列消费者
 * @DateTime: 2022/10/30 20:03
 **/
@Slf4j
@Component
public class DelayQueueConsumer {
    @Resource
    Bot bot;

    @Value("${master.id}")
    public Integer masterId;

    @VirtualThread("接收custom队列的消息")
    @RabbitListener(queues = CUSTOM_QUEUE_NAME)
    public void receiveDelayedQueue(Message message) {
        bot.getFriend(masterId).sendMessage(new String(message.getBody()));
        log.info("当前时间：{},收到custom队列的消息：{}", new Date(), new String(message.getBody()));
    }

    @VirtualThread("接收timetable队列的消息")
    @RabbitListener(queues = TIMETABLE_QUEUE_NAME)
    public void receiveTimetableQueue(Message message) {
        SimpleServiceMessage simpleServiceMessage = new SimpleServiceMessage(1, new String(message.getBody()));
        bot.getFriend(masterId).sendMessage(simpleServiceMessage);
        log.info("当前时间：{},收到timetable队列的消息：{}", new Date(), new String(message.getBody()));
    }
}
