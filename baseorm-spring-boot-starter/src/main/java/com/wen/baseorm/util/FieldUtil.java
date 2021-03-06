package com.wen.baseorm.util;

/**
 * 字段工具类
 *
 * @author calwen
 * @date 2022/7/9
 */

public class FieldUtil {
    public static String fieldSqlToJava(String field) {
        StringBuffer sb = new StringBuffer(field);
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == '_') {
                sb.replace(i, i + 2, String.valueOf((char) (sb.charAt(i + 1) - 32)));
            }
        }
        return sb.toString();
    }

    public static String fieldJavaToSql(String field) {
        StringBuffer sb = new StringBuffer(field);
        for (int i = 0; i < sb.length(); i++) {
            if (Character.isUpperCase(sb.charAt(i))) {
                sb.replace(i, i + 1, "_" + (char) (sb.charAt(i) + 32));
            }
        }
        return sb.toString();
    }
}
