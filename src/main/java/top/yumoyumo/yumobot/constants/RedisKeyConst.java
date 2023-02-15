package top.yumoyumo.yumobot.constants;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2023/2/15 12:13
 **/
public class RedisKeyConst {
    public static String getLimitKey(String key) {
        return "LIMIT:" + key;
    }

    public static String getTimeTableKey(String key) {
        return "TIMETABLE:" + key;
    }
}
