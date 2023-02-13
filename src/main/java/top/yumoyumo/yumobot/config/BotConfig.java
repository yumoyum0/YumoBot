package top.yumoyumo.yumobot.config;

import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.yumoyumo.yumobot.listener.ChatListener;
import top.yumoyumo.yumobot.listener.CloseListener;
import top.yumoyumo.yumobot.listener.DispatcherListener;
import top.yumoyumo.yumobot.listener.MemberListener;


/**
 * The type Bot config.
 *
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022 /9/25 13:49
 */
@Configuration
@Slf4j
public class BotConfig {

    @Value("${bot.account}")
    public Long account;

    @Value("${bot.pwd}")
    public String pwd;

    @Value("${bot.version}")
    public String version;

    @Value(("${bot.mode}"))
    public String mode;

    //设备认证信息文件
    private static final String deviceInfo = "device.json";

    @Bean
    public Bot bot() {
        if (null == account || null == pwd) {
            System.err.println("*****未配置账号或密码*****");
            log.warn("*****未配置账号或密码*****");
        }
        Bot bot = BotFactory.INSTANCE.newBot(account, pwd, new BotConfiguration() {
            {
                //保存设备信息到文件deviceInfo.json文件里相当于是个设备认证信息
                fileBasedDeviceInfo(deviceInfo);
                setProtocol(MiraiProtocol.ANDROID_PHONE); // 切换协议
                setHeartbeatStrategy(HeartbeatStrategy.STAT_HB); // 切换心跳策略
                redirectBotLogToDirectory();
            }
        });
        bot.login();
        EventChannel<BotEvent> eventChannel = bot.getEventChannel();
        eventChannel.registerListenerHost(new ChatListener());
        eventChannel.registerListenerHost(new CloseListener());
        eventChannel.registerListenerHost(new DispatcherListener());
        eventChannel.registerListenerHost(new MemberListener());
        return bot;
    }

}
