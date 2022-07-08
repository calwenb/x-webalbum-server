package com.wen.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 为实体类属性指定数据库字段名
 *
 * @author calwen
 * @date 2022/7/9
 */

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldName {
    String value();
}
