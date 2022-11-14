package top.yumoyumo.yumobot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yumoyumo.yumobot.annotation.VirtualThread;
import top.yumoyumo.yumobot.common.Result;

import java.util.concurrent.Future;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022 /9/10 0:03
 */
@RestController
@RequestMapping("/人设")
@Slf4j
public class PersonalController {

    @RequestMapping()
    @VirtualThread("人设")
    public Future<Result> log() {
        return new AsyncResult<>(Result.success(
                """
                        本名：芋泥波波
                        别名：芋啵
                        发色：蓝发
                        瞳色：金瞳
                        身高：130cm
                        星座：双鱼座
                        特点：天然呆，吃货，无性，娇憨，中二，腹黑，爱喝芋泥波波奶茶
                        简介：取自其主羽墨之名和自身的bot身份，因其主钟爱芋泥波波奶茶而得名
                        """
        )

        );
    }
}
