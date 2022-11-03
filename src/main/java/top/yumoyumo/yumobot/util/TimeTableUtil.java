package top.yumoyumo.yumobot.util;

import top.yumoyumo.yumobot.enums.TimeTableEnum;

import java.time.LocalTime;

/**
 * @Author: yumo
 * @Description: 时间表Util
 * @DateTime: 2022/10/31 18:11
 **/
public class TimeTableUtil {
    static public LocalTime getStart(int i) {
        return LocalTime.parse(getTime(i).getStart());
    }

    static public LocalTime getEnd(int i) {
        return LocalTime.parse(getTime(i).getEnd());
    }

    private static TimeTableEnum getTime(int i) {
        return switch (i) {
            case 1 -> TimeTableEnum.ONE;
            case 2 -> TimeTableEnum.TWO;
            case 3 -> TimeTableEnum.THREE;
            case 4 -> TimeTableEnum.FOUR;
            case 5 -> TimeTableEnum.FIVE;
            case 6 -> TimeTableEnum.SIX;
            case 7 -> TimeTableEnum.SEVEN;
            case 8 -> TimeTableEnum.EIGHT;
            case 9 -> TimeTableEnum.NINE;
            case 10 -> TimeTableEnum.TEN;
            case 11 -> TimeTableEnum.ELEVEN;
            case 12 -> TimeTableEnum.TWELVE;
            default -> null;
        };
    }
}
