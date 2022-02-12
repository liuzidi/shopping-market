package com.lzd.config;

import com.lzd.interceptor.CheckTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author:liuzidi
 * @Description:
 */
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private CheckTokenInterceptor checkTokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(checkTokenInterceptor)
                .addPathPatterns("/shopcart/**")
                .addPathPatterns("/orders/**")
                .addPathPatterns("/useraddr/**")
                .addPathPatterns("/user/check");
    }


}
