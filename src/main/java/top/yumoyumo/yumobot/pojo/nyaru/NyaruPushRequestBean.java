package top.yumoyumo.yumobot.pojo.nyaru;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/10/2 20:59
 **/
@NoArgsConstructor
@Data
public class NyaruPushRequestBean {

    @JsonProperty("fn_index")
    private Integer fnIndex;
    @JsonProperty("data")
    private List<String> data;
    @JsonProperty("action")
    private String action;
    @JsonProperty("session_hash")
    private String sessionHash;
}
