package com.wen.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SelectTypeEnum {
    ALL("ALL"),
    ONE("ONE"),
    COUNT("COUNT");
    private String selectType;
}
