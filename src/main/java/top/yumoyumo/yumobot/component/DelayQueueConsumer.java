package top.yumoyumo.yumobot.component;

import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.message.data.SimpleServiceMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import top.yumoyumo.yumobot.annotation.VirtualThread;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: yumo
 * @Description: 延迟队列消费者
 * @DateTime: 2022/10/30 20:03
 **/
@Slf4j
@PropertySource("classpath:yumobot.properties")
@Component
public class DelayQueueConsumer {
    @Resource
    Bot bot;

    @Value("${master.id}")
    public Integer masterId;

    public static final String DELAYED_QUEUE_NAME = "delayed.queue";

    @VirtualThread
    @RabbitListener(queues = DELAYED_QUEUE_NAME)
    public void receiveDelayedQueue(Message message) {
        SimpleServiceMessage simpleServiceMessage = new SimpleServiceMessage(1, new String(message.getBody()));
        bot.getFriend(masterId).sendMessage(simpleServiceMessage);
        log.info("当前时间：{},收到延时队列的消息：{}", new Date().toString(), new String(message.getBody()));
    }
}
