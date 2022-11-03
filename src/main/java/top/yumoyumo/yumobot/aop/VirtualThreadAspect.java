package top.yumoyumo.yumobot.aop;

import top.yumoyumo.yumobot.annotation.VirtualThread;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.*;

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
     * @param virtualThread       注解，未来可能用其传参0
     * @return Future future
     * @throws Throwable the throwable
     */
    @Around("start()&&@annotation(virtualThread)")
    public Future<Object> virtualThread(ProceedingJoinPoint proceedingJoinPoint, VirtualThread virtualThread) throws Throwable {
        return executorService.submit(() -> {
            Object result = null;
            try {
                result = proceedingJoinPoint.proceed();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
            if (result instanceof Future) {
                return ((Future<?>) result).get();
            }
            return result;
        });
    }
}
