package top.yumoyumo.yumobot.pojo.nyaru;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/10/2 22:27
 **/
@NoArgsConstructor
@Data
public class NyaruStatusRequestBean {

    @JsonProperty("hash")
    private String hash;
}
