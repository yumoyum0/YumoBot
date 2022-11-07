package top.yumoyumo.yumobot.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import top.yumoyumo.yumobot.annotation.VirtualThread;
import top.yumoyumo.yumobot.exception.LocalRuntimeException;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * The type Virtual thread aspect.
 *
 * @Author: yumo
 * @Description: 虚拟线程切面 ，被 @VirtualThread 标注的方法会被分配到一个虚拟线程中执行
 * @DateTime: 2022 /9/25 15:24
 */
@Slf4j
@Aspect
@Component
public class VirtualThreadAspect {
    @Resource
    private ExecutorService executorService;

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
    @Order
    public Future<Object> virtualThread(ProceedingJoinPoint proceedingJoinPoint, VirtualThread virtualThread) {
        return executorService.submit(() -> {
            Object result = null;
            try {
                result = proceedingJoinPoint.proceed();
            } catch (Throwable e) {
                e.printStackTrace();
                throw new LocalRuntimeException(e);
            }
            if (result instanceof Future) {
                return ((Future<?>) result).get();
            }
            return result;
        });
    }
}
