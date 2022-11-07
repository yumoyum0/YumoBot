package top.yumoyumo.yumobot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yumoyumo.yumobot.annotation.VirtualThread;
import top.yumoyumo.yumobot.service.TimeTableService;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.Future;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/10/30 21:34
 **/
@Slf4j
@RestController
@RequestMapping("/课表")
public class TimeTableController {

    @Resource
    private TimeTableService timeTableService;

    @RequestMapping("/help")
    @VirtualThread("课表help")
    public Future<String> help() {
        return new AsyncResult<>(
                """
                        课表查询help:
                        --------------------
                        [/课表 [today [plus,minus]] [dayNum]
                        例:
                        /课表
                        /课表 plus 1
                        """);
    }

    @GetMapping(value = {"", "/", "/today"})
    @VirtualThread("查询今日课表")
    public Future<String> today() {
        return new AsyncResult<>(timeTableService.getTimeTableByDay(LocalDateTime.now()));
    }


    @GetMapping("/plus/{num}")
    @VirtualThread("查询未来课表")
    public Future<String> plus(@PathVariable Integer num) {
        return new AsyncResult<>(timeTableService.getTimeTableByDay(LocalDateTime.now().plusDays(num)));
    }

    @GetMapping("/minus/{num}")
    @VirtualThread("查询过去课表")
    public Future<String> minus(@PathVariable Integer num) {
        return new AsyncResult<>(timeTableService.getTimeTableByDay(LocalDateTime.now().minusDays(num)));
    }
}
