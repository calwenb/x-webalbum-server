package com.wen;

import com.wen.utils.LoggerUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebalbumApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebalbumApplication.class, args);
        LoggerUtil.info("start ok ~", WebalbumApplication.class);
    }

}
