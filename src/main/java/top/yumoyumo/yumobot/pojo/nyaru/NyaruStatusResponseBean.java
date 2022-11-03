package top.yumoyumo.yumobot.pojo.nyaru;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/10/2 21:09
 **/
@NoArgsConstructor
@Data
public class NyaruStatusResponseBean {

    @JsonProperty("status")
    private String status;
    @JsonProperty("data")
    private DataDTO data;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("data")
        private List<String> data;
        @JsonProperty("duration")
        private Double duration;
        @JsonProperty("average_duration")
        private Double averageDuration;
    }
}
