package com.wen.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mr.wen
 */

@Getter
@AllArgsConstructor
public enum OperatEnum {
    /**
     *
     */
    HEAD("HEAD"),
    /**
     *
     */
    AND("AND"),
    /**
     *
     */
    OR("OR"),

    /**
     *
     */
    ORDER("ORDER"),
    /**
     *
     */
    ORDER_DESC("ORDER_DESC"),
    /**
     *
     */
    LIMIT("LIMIT"),

    EQ("EQ"), EQS("EQS"), IN("IN"),
    /**
     *
     */
    NOT_EQ("NOT_EQ"),
    GREATER("GREATER"),
    LESS("LESS"),
    G_EQ("G_EQ"),
    L_EQ("L_EQ"),

    LIKE("LIKE"),
    LIKE_LEFT("LIKE_LEFT"),
    LIKE_RIGHT("LIKE_RIGHT"),

/*    SELECT("SELECT"),

    SELECT_COUNT("SELECT_COUNT")*/;

    private final String operat;
}
