package top.yumoyumo.yumobot.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yumoyumo.yumobot.annotation.VirtualThread;
import top.yumoyumo.yumobot.common.Result;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/9/8 13:12
 **/
@RestController
@RequestMapping("/")
@Slf4j
public class HelpController {

    @VirtualThread("help")
    @RequestMapping(value = {"", "/", "/help"})
    public Result help() {
        return Result.success(
                """
                        YumoBot
                        --------------------
                        0. /版本\t查看版本更新日志
                        1. /人设\t查看bot人设
                        2. /城市\t查询城市信息
                        3. /天气\t查询天气信息
                        4. /一言\t一言
                        5. 闲聊模式：关闭:<雪豹闭嘴>;开启<雪豹张嘴>
                        6. /图片\t获取图片
                        """);
    }


}
