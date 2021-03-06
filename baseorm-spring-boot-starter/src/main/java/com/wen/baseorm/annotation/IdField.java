package com.wen.baseorm.annotation;

import com.wen.baseorm.enums.IdTypeEnum;

import java.lang.annotation.*;

/**
 * 为实体类属性指定数据库主键
 *
 * @author calwen
 * @date 2022/7/9
 */

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IdField {

    String value();

    IdTypeEnum idType() default IdTypeEnum.AUTO;

}
