package top.yumoyumo.yumobot.interceptor;


import org.slf4j.MDC;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import top.yumoyumo.yumobot.pojo.TraceLog;
import top.yumoyumo.yumobot.util.CommonUtil;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/11/2 19:41
 **/
@Component
public class RequestInterceptor implements HandlerInterceptor {
    public static ThreadLocal<TraceLog> requestHolder = new ThreadLocal<>();
    public static String TRACE_ID = "TRACE_ID";

    /**
     * 每次收到请求时，记录该次请求
     *
     * @param request
     * @param response
     * @param handler
     * @return
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //MDC机制https://www.jianshu.com/p/1dea7479eb07
        MDC.put(TRACE_ID, UUID.randomUUID().toString());
        TraceLog preTraceLog = new TraceLog()
                .setEnv(getEnv(request.getCookies()))
                .setSpendTime(System.currentTimeMillis() + "")
                .setUrl(request.getRequestURI())
                .setUserAgent(CommonUtil.getUserAgent(request));
        requestHolder.set(preTraceLog);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        requestHolder.remove();
        MDC.clear();
    }

    /**
     * 由于只做了PC端网页，所以暂时只放PC
     *
     * @param cookies
     * @return
     */
    private String getEnv(Cookie[] cookies) {
        return "PC";
    }
}
