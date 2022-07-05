package com.wen.mapper;

import com.wen.wrapper.SetWrapper;
import com.wen.wrapper.WhereWrapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

public interface BaseMapper {
    <T> ArrayList<T> selectTargets(Class<T> targetClass, WhereWrapper... whereWrapper);

    <T> T selectTarget(Class<T> targetClass, WhereWrapper... whereWrapper);

    <T> int insertTarget(T target);

    <T> int replaceTarget(T target);

    <T> int deleteTarget(Class<T> targetClass, WhereWrapper whereWrapper);

    <T> int updateTarget(Class<T> targetClass, SetWrapper setWrapper, WhereWrapper whereWrapper);
}
