package com.wen.baseorm.annotation;

import com.wen.baseorm.enums.IdTypeEnum;

import java.lang.annotation.*;

/**
 * 为实体类属性指定数据库字段名
 *
 * @author calwen
 * @date 2022/7/9
 */

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldName {
    String value();
}
