package top.yumoyumo.yumobot.controller;

import top.yumoyumo.yumobot.annotation.VirtualThread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.concurrent.ExecutorService;

/**
 * The type Test controller.
 *
 * @Author: yumo
 * @Description: 测试Controller
 * @DateTime: 2022 /9/25 21:11
 */
@RestController
@Slf4j
@RequestMapping("/test")
public class TestController {

    /**
     * Sync.
     *
     * @throws InterruptedException the interrupted exception
     */
    @RequestMapping("/sync")
    public void sync() throws InterruptedException {
        Thread.sleep(Duration.ofSeconds(1));
    }

    /**
     * Async.
     *
     * @throws InterruptedException the interrupted exception
     */
    @Async
    @RequestMapping("/async")
    public void async() throws InterruptedException {
        Thread.sleep(Duration.ofSeconds(1));
    }

    /**
     * Virtual thread.
     *
     * @throws InterruptedException the interrupted exception
     */
    @VirtualThread
    @RequestMapping("/virtualThread")
    public void virtualThread() throws InterruptedException {
        Thread.sleep(Duration.ofSeconds(1));
    }

}
