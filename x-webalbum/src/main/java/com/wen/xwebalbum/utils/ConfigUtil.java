package com.wen.xwebalbum.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigUtil {

    public static String superAdminName;

    public static String superAdminLoginName;
    public static String superAdminPassword;


    @Value("${x-webalbum.user.super-admin.userName}")
    public void setA(String superAdminName) {
        ConfigUtil.superAdminName = superAdminName;
    }

    @Value("${x-webalbum.user.super-admin.login-name}")
    public void setSuperAdminLoginName(String superAdminLoginName) {
        ConfigUtil.superAdminLoginName = superAdminLoginName;
    }


    @Value("${x-webalbum.user.super-admin.password}")
    public void setSuperAdminPassword(String superAdminPassword) {
        ConfigUtil.superAdminPassword = superAdminPassword;
    }

    public static String getSuperAdminName() {
        return superAdminName;
    }

    public static String getSuperAdminLoginName() {
        return superAdminLoginName;
    }

    public static String getSuperAdminPassword() {
        return superAdminPassword;
    }
}
