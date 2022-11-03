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
     * 请求平台
     */
    private String env;
    /**
     * UA
     */
    private String userAgent;

    /**
     * 消耗时间
     */
    private String spendTime;

    /**
     * URL
     */
    private String url;

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
        return String.format(format, description, env, URLUtil.decode(url), strParam, strResult, spendTime, userAgent);
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
            请求平台：%s
            请求地址：%s
            请求参数：%s
            请求返回：%s
            请求耗时：%s
            请求UA：%s
            ===========释放响应===========""";
    private final static String errorFormat = """

            ===========捕获异常===========
            操作描述：%s
            请求平台：%s
            请求地址：%s
            请求参数：%s
            请求异常：%s
            请求耗时：%s
            请求UA：%s
            ===========释放异常===========""";

}