package top.yumoyumo.yumobot.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The type Now weather bean.
 *
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022 /9/8 16:35
 */
@NoArgsConstructor
@Data
public class NowWeatherBean {


    @JsonProperty("code")
    private String code;
    @JsonProperty("updateTime")
    private String updateTime;
    @JsonProperty("fxLink")
    private String fxLink;
    @JsonProperty("now")
    private NowDTO now;
    @JsonProperty("refer")
    private ReferDTO refer;

    @NoArgsConstructor
    @Data
    public static class NowDTO {
        @JsonProperty("obsTime")
        private String obsTime;
        @JsonProperty("temp")
        private String temp;
        @JsonProperty("feelsLike")
        private String feelsLike;
        @JsonProperty("icon")
        private String icon;
        @JsonProperty("text")
        private String text;
        @JsonProperty("wind360")
        private String wind360;
        @JsonProperty("windDir")
        private String windDir;
        @JsonProperty("windScale")
        private String windScale;
        @JsonProperty("windSpeed")
        private String windSpeed;
        @JsonProperty("humidity")
        private String humidity;
        @JsonProperty("precip")
        private String precip;
        @JsonProperty("pressure")
        private String pressure;
        @JsonProperty("vis")
        private String vis;
        @JsonProperty("cloud")
        private String cloud;
        @JsonProperty("dew")
        private String dew;
    }

    @NoArgsConstructor
    @Data
    public static class ReferDTO {
        @JsonProperty("sources")
        private List<String> sources;
        @JsonProperty("license")
        private List<String> license;
    }
}
