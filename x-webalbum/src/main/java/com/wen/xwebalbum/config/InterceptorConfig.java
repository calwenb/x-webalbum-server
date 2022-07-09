package com.wen.xwebalbum.config;

import com.wen.xwebalbum.interceptor.AuthenticationInterceptor;
import com.wen.xwebalbum.interceptor.NormInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Token验证拦截器规则配置类
 *
 * @author Mr.文
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public NormInterceptor normInterceptor() {
        return new NormInterceptor();
    }

    @Bean
    AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截不符合规范的请求
        registry.addInterceptor(normInterceptor());
        //验证拦截
        registry.addInterceptor(authenticationInterceptor());
    }


}