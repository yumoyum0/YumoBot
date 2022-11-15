package top.yumoyumo.yumobot.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: yumo
 * @Description: 延迟队列配置
 * @DateTime: 2022/10/30 20:02
 **/
@Configuration
public class MqConfig {
    public static final String DELAYED_EXCHANGE_NAME = "delayed.exchange";
    public static final String CUSTOM_QUEUE_NAME = "custom.queue";
    public static final String CUSTOM_ROUTING_KEY = "custom.routingkey";
    public static final String TIMETABLE_QUEUE_NAME = "timetable.queue";
    public static final String TIMETABLE_ROUTING_KEY = "timetable.routingkey";

    /**
     * 声明延迟交换机
     */
    @Bean
    public CustomExchange delayedExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        args.put("x-queue-mode", "lazy"); // 设置惰性队列
        // 设置持久化
        return new CustomExchange(DELAYED_EXCHANGE_NAME, "x-delayed-message", true, false, args);
    }

    /**
     * 声明自定义消息队列
     */
    @Bean
    public Queue customQueue() {
        return new Queue(CUSTOM_QUEUE_NAME);
    }

    /**
     * 绑定课表队列和延迟交换机
     */
    @Bean
    public Binding bindingCustomQueue(@Qualifier("customQueue") Queue queue,
                                      @Qualifier("delayedExchange") CustomExchange delayedExchange) {
        return BindingBuilder.bind(queue).to(delayedExchange).with(CUSTOM_ROUTING_KEY).noargs();
    }

    /**
     * 声明课表队列
     */
    @Bean
    public Queue timetableQueue() {
        return new Queue(TIMETABLE_QUEUE_NAME);
    }

    /**
     * 绑定课表队列和延迟交换机
     */
    @Bean
    public Binding bindingTimetableQueue(@Qualifier("timetableQueue") Queue queue,
                                         @Qualifier("delayedExchange") CustomExchange delayedExchange) {
        return BindingBuilder.bind(queue).to(delayedExchange).with(TIMETABLE_ROUTING_KEY).noargs();
    }

}
