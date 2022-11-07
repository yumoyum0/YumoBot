package top.yumoyumo.yumobot.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.yumoyumo.yumobot.common.Result;
import top.yumoyumo.yumobot.exception.LocalRuntimeException;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/11/7 10:42
 **/
@RestControllerAdvice
public class LocalRuntimeExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result dealThrowable(Exception e) {
        e.printStackTrace();
        return Result.failure(e.getMessage());
    }

}
