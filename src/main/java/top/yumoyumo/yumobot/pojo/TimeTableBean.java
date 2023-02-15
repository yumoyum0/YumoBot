package top.yumoyumo.yumobot.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: yumo
 * @Description: 课表bean
 * @DateTime: 2022/10/30 15:25
 **/
@NoArgsConstructor
@Data
public class TimeTableBean {


    @JsonProperty("day")
    private Integer day;
    @JsonProperty("name")
    private String name;
    @JsonProperty("week")
    private String week;
    @JsonProperty("weekStart")
    private Integer weekStart;
    @JsonProperty("weekEnd")
    private Integer weekEnd;
    @JsonProperty("weekArray")
    private List<Integer> weekArray;
    @JsonProperty("sectionStart")
    private Integer sectionStart;
    @JsonProperty("sectionEnd")
    private Integer sectionEnd;
    @JsonProperty("teacher")
    private String teacher;
    @JsonProperty("locale")
    private String locale;
}
