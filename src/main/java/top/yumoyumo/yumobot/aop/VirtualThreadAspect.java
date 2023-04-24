package top.yumoyumo.yumobot.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.yumoyumo.yumobot.annotation.VirtualThread;
import top.yumoyumo.yumobot.exception.LocalRuntimeException;
import top.yumoyumo.yumobot.pojo.TraceLog;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * The type Virtual thread aspect.
 *
 * @Author: yumo
 * @Description: 虚拟线程切面 ，被 @VirtualThread 标注的方法会被分配到一个虚拟线程中执行
 * @DateTime: 2022 /9/25 15:24
 */
@Aspect
@Component
public class VirtualThreadAspect {

    private final static Logger logg = LoggerFactory.getLogger(VirtualThreadAspect.class);
    @Resource
    private ExecutorService executorService;

    private final static Set<String> EXCLUDE_SET;

    static {
        EXCLUDE_SET = new HashSet<>();
        EXCLUDE_SET.add("password");
    }

    /**
     * 所有被标注了@VirtualThread的方法都是切点
     */
    @Pointcut("@annotation(top.yumoyumo.yumobot.annotation.VirtualThread)")
    public void start() {
    }

    /**
     * 使用环绕通知，对切点新建一个虚拟线程来执行
     *
     * @param proceedingJoinPoint 执行连接点
     */
    @Around("start()&&@annotation(virtualThread)")
    public Object virtualThread(ProceedingJoinPoint proceedingJoinPoint, VirtualThread virtualThread) {
        CompletableFuture<String> paramFuture = CompletableFuture.supplyAsync(() -> {
                    Map<String, Object> param = new HashMap<>();
                    Object[] paramValues = proceedingJoinPoint.getArgs();
                    String[] paramNames = ((CodeSignature) proceedingJoinPoint.getSignature()).getParameterNames();
                    for (int i = 0; i < paramNames.length; i++) {
                        if (!EXCLUDE_SET.contains(paramNames[i])) {
                            param.put(paramNames[i], paramValues[i]);
                        }
                    }
                    return String.valueOf(param);
                }
                , executorService);
        CompletableFuture<Object> resultFuture = CompletableFuture.supplyAsync(() -> {
                    try {
                        return proceedingJoinPoint.proceed();
                    } catch (Throwable e) {
                        throw new LocalRuntimeException(e);
                    }
                }
                , executorService);
        CompletableFuture.allOf(paramFuture, resultFuture).thenAcceptAsync((res) -> {
            TraceLog traceLog = new TraceLog(virtualThread.value(),
                    String.valueOf(proceedingJoinPoint.getSignature()),
                    paramFuture.join(),
                    resultFuture.join());
            logg.info(traceLog.toLogFormat(true));
        }, executorService);
        return resultFuture.join();
    }


}
