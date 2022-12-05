package top.yumoyumo.yumobot.config;

import com.yomahub.liteflow.thread.ExecutorBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/12/5 17:51
 **/
@Component
public class CustomThreadBuilder implements ExecutorBuilder {
    @Resource
    private ExecutorService executorService;

    @Override
    public ExecutorService buildExecutor() {
        return executorService;
    }
}
