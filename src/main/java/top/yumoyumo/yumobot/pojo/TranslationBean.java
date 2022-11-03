package top.yumoyumo.yumobot.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The type Translation bean.
 *
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022 /10/3 13:35
 */
@NoArgsConstructor
@Data
public class TranslationBean {

    @JsonProperty("from")
    private String from;
    @JsonProperty("to")
    private String to;
    @JsonProperty("trans_result")
    private List<TransResultDTO> transResult;

    /**
     * The type Trans result dto.
     */
    @NoArgsConstructor
    @Data
    public static class TransResultDTO {
        @JsonProperty("src")
        private String src;
        @JsonProperty("dst")
        private String dst;
    }
}
