package top.yumoyumo.yumobot.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Hitikoto bean.
 *
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022 /9/8 17:53
 */
@NoArgsConstructor
@Data
public class HitikotoBean {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("uuid")
    private String uuid;
    @JsonProperty("hitokoto")
    private String hitokoto;
    @JsonProperty("type")
    private String type;
    @JsonProperty("from")
    private String from;
    @JsonProperty("from_who")
    private String fromWho;
    @JsonProperty("creator")
    private String creator;
    @JsonProperty("creator_uid")
    private Integer creatorUid;
    @JsonProperty("reviewer")
    private Integer reviewer;
    @JsonProperty("commit_from")
    private String commitFrom;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("length")
    private Integer length;
}
