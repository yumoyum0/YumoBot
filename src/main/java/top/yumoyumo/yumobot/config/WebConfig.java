package top.yumoyumo.yumobot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The type Web config.
 *
 * @Author: yumo
 * @Description: 拦截器配置类
 * @DateTime: 2022 /9/9 21:24
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new RequestInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/", "/static/**");
//    }
}
