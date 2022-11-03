package top.yumoyumo.yumobot.annotation;

import java.lang.annotation.*;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/11/2 19:39
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperateLog {
    String operDesc() default "";
}