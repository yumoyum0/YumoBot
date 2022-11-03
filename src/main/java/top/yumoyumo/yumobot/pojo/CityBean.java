package top.yumoyumo.yumobot.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The type City bean.
 *
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022 /9/8 16:11
 */
@NoArgsConstructor
@Data
public class CityBean {

    @JsonProperty("code")
    private String code;
    @JsonProperty("location")
    private List<LocationDTO> location;
    @JsonProperty("refer")
    private ReferDTO refer;

    /**
     * The type Refer dto.
     */
    @NoArgsConstructor
    @Data
    public static class ReferDTO {
        @JsonProperty("sources")
        private List<String> sources;
        @JsonProperty("license")
        private List<String> license;
    }

    /**
     * The type Location dto.
     */
    @NoArgsConstructor
    @Data
    public static class LocationDTO {
        @JsonProperty("name")
        private String name;
        @JsonProperty("id")
        private String id;
        @JsonProperty("lat")
        private String lat;
        @JsonProperty("lon")
        private String lon;
        @JsonProperty("adm2")
        private String adm2;
        @JsonProperty("adm1")
        private String adm1;
        @JsonProperty("country")
        private String country;
        @JsonProperty("tz")
        private String tz;
        @JsonProperty("utcOffset")
        private String utcOffset;
        @JsonProperty("isDst")
        private String isDst;
        @JsonProperty("type")
        private String type;
        @JsonProperty("rank")
        private String rank;
        @JsonProperty("fxLink")
        private String fxLink;
    }
}
