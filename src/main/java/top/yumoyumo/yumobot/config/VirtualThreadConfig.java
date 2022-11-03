package top.yumoyumo.yumobot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * The type Virtual thread config.
 *
 * @Author: yumo
 * @Description: 虚拟线程配置
 * @DateTime: 2022 /9/24 19:11
 */
@Configuration
public class VirtualThreadConfig {
    /**
     * Executor service executor service.
     *
     * @return the executor service
     */
    @Bean("executorService")
    public ExecutorService executorService() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }
}