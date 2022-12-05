package top.yumoyumo.yumobot.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.io.Serializable;

/**
 * @Author: yumo
 * @Description: 前端统一返回类型
 * @DateTime: 2022/5/9 18:04
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Result implements Serializable {
    /**
     * 成功与否
     */
    private Boolean success;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 错误消息
     */
    private String errMsg;

    /**
     * 数据对象
     */
    private String data;

    public static Result success(String data) {
        return new Result(true, 200, null, data);
    }

    public static Result failure(String errMsg) {
        return new Result(false, 500, errMsg, null);
    }

}
