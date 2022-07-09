package com.wen;

import com.wen.utils.LoggerUtil;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
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
