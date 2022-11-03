package top.yumoyumo.yumobot.enums;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/10/31 17:50
 **/
public enum TimeTableEnum {
    ONE("08:00", "08:45"),
    TWO("08:50", "09:35"),
    THREE("09:50", "10:35"),
    FOUR("10:40", "11:25"),
    FIVE("11:30", "12:15"),
    SIX("13:45", "14:30"),
    SEVEN("14:35", "15:20"),
    EIGHT("15:35", "16:20"),
    NINE("10:25", "17:10"),
    TEN("18:30", "19:15"),
    ELEVEN("19:25", "20:10"),
    TWELVE("20:20", "21:05");

    private String start;
    private String end;

    TimeTableEnum(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
