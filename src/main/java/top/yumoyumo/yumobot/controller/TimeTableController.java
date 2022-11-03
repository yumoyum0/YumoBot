package top.yumoyumo.yumobot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yumoyumo.yumobot.annotation.OperateLog;
import top.yumoyumo.yumobot.annotation.VirtualThread;
import top.yumoyumo.yumobot.service.TimeTableService;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Calendar;
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

    @OperateLog(operDesc = "课表help")
    @RequestMapping("/help")
    @VirtualThread
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

    @OperateLog(operDesc = "查询今日课表")
    @GetMapping(value = {"", "/", "/today"})
    @VirtualThread
    public Future<String> today() {
        return new AsyncResult<>(timeTableService.getTimeTableByDay(LocalDateTime.now()));
    }


    @OperateLog(operDesc = "查询未来课表")
    @GetMapping("/plus/{num}")
    public Future<String> plus(@PathVariable Integer num) {
        return new AsyncResult<>(timeTableService.getTimeTableByDay(LocalDateTime.now().plusDays(num)));
    }

    @OperateLog(operDesc = "查询过去课表")
    @GetMapping("/minus/{num}")
    public Future<String> minus(@PathVariable Integer num) {
        return new AsyncResult<>(timeTableService.getTimeTableByDay(LocalDateTime.now().minusDays(num)));
    }
}
