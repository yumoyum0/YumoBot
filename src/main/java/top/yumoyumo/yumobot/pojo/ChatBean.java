package top.yumoyumo.yumobot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/11/3 14:48
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatBean {
    private String content;
    private Integer type;
    private Long from;
    private String fromName;
    private Long to;
    private String toName;
}
