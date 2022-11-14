package top.yumoyumo.yumobot.service;

import top.yumoyumo.yumobot.common.Result;

import java.time.LocalDateTime;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/10/31 20:54
 **/
public interface TimeTableService {

    String getTimeTableByDay(LocalDateTime localDateTime);
}
