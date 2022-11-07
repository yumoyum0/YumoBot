package top.yumoyumo.yumobot.pojo;

import cn.hutool.core.util.URLUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/11/2 19:42
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TraceLog {
    /**
     * 操作描述
     */
    private String description;

    /**
     * signature
     */
    private String signature;

    /**
     * 请求参数
     */
    private Object params;

    /**
     * 请求返回的结果
     */
    private Object result;


    public String toLogFormat(Boolean requestStatus) {
        String strResult = getResult(200);
        String strParam = String.valueOf(params);
        String format = requestStatus ? commonFormat : errorFormat;
        return String.format(format, description, signature, strParam, strResult);
    }

    /**
     * 截取指定长度的返回值
     *
     * @param factor
     * @return
     */
    public String getResult(int factor) {
        String result = String.valueOf(this.result);
        if (factor == 0 || result.length() < factor) {
            return result;
        }
        return result.substring(0, factor - 1) + "...}";
    }

    private final static String commonFormat = """

            ===========捕获响应===========
            操作描述：%s
            请求签名：%s
            请求参数：%s
            请求返回：%s
            ===========释放响应===========""";
    private final static String errorFormat = """

            ===========捕获异常===========
            操作描述：%s
            请求签名：%s
            请求参数：%s
            请求异常：%s
            ===========释放异常===========""";

}