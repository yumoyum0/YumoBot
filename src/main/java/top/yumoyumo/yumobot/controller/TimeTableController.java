package top.yumoyumo.yumobot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yumoyumo.yumobot.annotation.VirtualThread;
import top.yumoyumo.yumobot.common.Result;
import top.yumoyumo.yumobot.service.TimeTableService;

import javax.annotation.Resource;
import java.time.LocalDateTime;

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
    public Result help() {
        return Result.success(
                """
                        课表查询help:
                        --------------------
                        [/课表 [today [plus,minus]] [dayNum]
                        例:
                        /课表
                        /课表 plus 1
                        """
        );
    }

    @GetMapping(value = {"", "/", "/today"})
    @VirtualThread("查询今日课表")
    public Result today() {
        return Result.success(timeTableService.getTimeTableByDay(LocalDateTime.now()));
    }


    @GetMapping("/plus/{num}")
    @VirtualThread("查询未来课表")
    public Result plus(@PathVariable Integer num) {
        return Result.success(timeTableService.getTimeTableByDay(LocalDateTime.now().plusDays(num)));
    }

    @GetMapping("/minus/{num}")
    @VirtualThread("查询过去课表")
    public Result minus(@PathVariable Integer num) {
        return Result.success(timeTableService.getTimeTableByDay(LocalDateTime.now().minusDays(num)));
    }
}
