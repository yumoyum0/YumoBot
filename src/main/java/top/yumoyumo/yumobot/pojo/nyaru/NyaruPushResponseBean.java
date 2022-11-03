package top.yumoyumo.yumobot.pojo.nyaru;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/10/2 22:26
 **/
@NoArgsConstructor
@Data
public class NyaruPushResponseBean {

    @JsonProperty("hash")
    private String hash;
    @JsonProperty("queue_position")
    private Integer queuePosition;
}
