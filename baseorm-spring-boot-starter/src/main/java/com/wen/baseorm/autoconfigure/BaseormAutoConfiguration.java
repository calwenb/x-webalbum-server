package com.wen.baseorm.autoconfigure;

import com.wen.baseorm.aop.MapperLogAop;
import com.wen.baseorm.mapper.BaseMapper;
import com.wen.baseorm.mapper.impl.BaseMapperImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * BaseormAutoConfiguration类
 * 自动装配需要的bean。
 * 意义：
 * 1.AutoConfiguration类，@bean，@ConditionalOnProperty
 * 2.在spring.factories中指定AutoConfiguration类
 * 3.由springboot自动装配机制将第三方需要的bean放入IOC容器中
 *
 * @author calwen
 * @date 2022/7/9
 */

@Configuration
public class BaseormAutoConfiguration {

    @Bean
    public BaseMapper baseMapper() {
        return new BaseMapperImpl();
    }

    @Bean
    @ConditionalOnProperty(prefix = "base-orm", name = "logger", havingValue = "true")
    public MapperLogAop mapperLogAop() {
        return new MapperLogAop();
    }


}
