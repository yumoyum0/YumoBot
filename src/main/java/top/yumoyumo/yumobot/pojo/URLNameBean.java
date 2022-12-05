package top.yumoyumo.yumobot.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/11/22 15:55
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class URLNameBean {

    @JsonProperty("formToken")
    private String formToken;
    @JsonProperty("details")
    private List<DetailDTO> details;

    @NoArgsConstructor
    @Data
    public static class DetailDTO {
        @JsonProperty("name")
        private String name;
        @JsonProperty("urls")
        private List<String> urls;
    }
}
