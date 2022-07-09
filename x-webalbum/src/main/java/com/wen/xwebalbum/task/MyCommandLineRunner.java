package com.wen.xwebalbum.task;

import com.wen.xwebalbum.pojo.User;
import com.wen.xwebalbum.servcie.UserService;
import com.wen.xwebalbum.utils.ConfigUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
//@Component
public class MyCommandLineRunner implements CommandLineRunner {
    @Resource
    UserService userService;

    @Override
    public void run(String... args) {
        String superAdminName = ConfigUtil.superAdminName;
        String superAdminLoginName = ConfigUtil.getSuperAdminLoginName();
        String superAdminPassword = ConfigUtil.getSuperAdminPassword();
        if (userService.initAdmin(superAdminName, superAdminLoginName, superAdminPassword) > 0) {
            log.info("超级管理添加成功");
        } else {
            log.warn("超级管理失败");

        }
    }
}