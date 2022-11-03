package top.yumoyumo.yumobot.annotation;

import java.lang.annotation.*;

/**
 * @Author: yumo
 * @Description: 将方法标记为虚拟线程执行候选的注解。
 * <p>也可以在类型级别使用，在这种情况下，所有类型的方法都被认为是虚拟的。
 * 但是请注意， {@code @VirtualThread}不支持在{@link org.springframework.context.annotation.Configuration @Configuration}类中声明的方法。
 * <p>在目标方法签名方面，支持任何参数类型。但是，返回类型被限制为{@code void} 或{@link java.util.concurrent.Future}。
 * <p>在后一种情况下，您可以声明更具体的{@link org.springframework.util.concurrent.ListenableFuture} 或{@link java.util.concurrent.CompletableFuture}类型，
 * 它们允许与虚拟任务进行更丰富的交互并通过进一步的处理步骤进行即时组合。
 * @DateTime: 2022/9/25 14:39
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface VirtualThread {
    /**
     * 指定虚拟操作的限定符值。
     * <p>可用于确定执行虚拟操作时要使用的目标执行器，匹配特定{@link java.util.concurrent.Executor Executor}
     * 或{@link org.springframework.core.task.TaskExecutor TaskExecutor} bean 定义的限定符值（或 bean 名称）。
     * <p>当在类级别的{@code @VirtualThread}注释上指定时，表示给定的执行器应该用于类中的所有方法。
     * {@code VirtualThread#value} 的方法级别使用始终覆盖在类级别设置的任何值。
     *
     * @return
     */
    String value() default "";
}
