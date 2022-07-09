package com.wen.xwebalbum;

import com.wen.xwebalbum.utils.LoggerUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class WebalbumApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebalbumApplication.class, args);
        LoggerUtil.info("start ok ~", WebalbumApplication.class);
    }

}
