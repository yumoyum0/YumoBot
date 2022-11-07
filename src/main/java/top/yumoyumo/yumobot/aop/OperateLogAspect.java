package top.yumoyumo.yumobot.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.yumoyumo.yumobot.annotation.OperateLog;
import top.yumoyumo.yumobot.exception.LocalRuntimeException;
import top.yumoyumo.yumobot.interceptor.RequestInterceptor;
import top.yumoyumo.yumobot.util.CommonUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author: yumo
 * @create:
 **/
@Aspect
@Component
public class OperateLogAspect {
    private final static Logger logg = LoggerFactory.getLogger(OperateLogAspect.class);
    private final static Set<String> EXCLUDE_SET;

    static {
        EXCLUDE_SET = new HashSet<>();
        EXCLUDE_SET.add("password");
    }

    @Pointcut("@annotation(top.yumoyumo.yumobot.annotation.OperateLog)")
    public void operateLog() {
    }

    @Around("operateLog()&&@annotation(log)")
    public Object aroundMethod(ProceedingJoinPoint proceedingJoinPoint, OperateLog log) throws Throwable {
        Object result = null;
        try {
            Map<String, Object> paramMap = CommonUtil.getRequestParamMap(proceedingJoinPoint, EXCLUDE_SET);
            result = proceedingJoinPoint.proceed();
            Object finalResult = result;
            Optional.ofNullable(RequestInterceptor.requestHolder.get()).ifPresent((preTrack) -> {
                preTrack.setSpendTime(System.currentTimeMillis() - Long.parseLong(preTrack.getSpendTime()) + "ms")
                        .setDescription(log.operDesc())
                        .setParams(paramMap)
                        .setResult(finalResult);
                logg.info(preTrack.toLogFormat(true));
            });
        } catch (Exception e) {
            throw new LocalRuntimeException(e);
        }
        return result;
    }

    @AfterThrowing(pointcut = "operateLog()&&@annotation(log)", throwing = "exception")
    public void throwHandler(JoinPoint joinPoint, OperateLog log, Throwable exception) {
        Map<String, Object> paramMap = CommonUtil.getRequestParamMap(joinPoint, EXCLUDE_SET);
        try {
            Optional.ofNullable(RequestInterceptor.requestHolder.get()).ifPresent(preTrack -> {
                preTrack.setSpendTime(System.currentTimeMillis() - Long.parseLong(preTrack.getSpendTime()) + "ms")
                        .setDescription(log.operDesc())
                        .setParams(paramMap)
                        .setResult(exception);
                logg.info(preTrack.toLogFormat(false));
            });
        } catch (Exception e) {
            throw new LocalRuntimeException(e);
        }
    }
}
