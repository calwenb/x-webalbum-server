package com.wen.baseorm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * wrapper操作枚举类
 *
 * @author calwen
 * @date 2022/7/9
 */

public enum OperatEnum {
    /**
     *
     */
    HEAD,
    /**
     *
     */
    AND,
    /**
     *
     */
    OR,

    /**
     *
     */
    ORDER,
    /**
     *
     */
    ORDER_DESC,
    /**
     *
     */
    LIMIT,

    EQ, EQS, IN,
    /**
     *
     */
    NOT_EQ,
    GREATER,
    LESS,
    G_EQ,
    L_EQ,

    LIKE,
    LIKE_LEFT,
    LIKE_RIGHT,

/*    SELECT("SELECT"),

    SELECT_COUNT("SELECT_COUNT")*/;


}
