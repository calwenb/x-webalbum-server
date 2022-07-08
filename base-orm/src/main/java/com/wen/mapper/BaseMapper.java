package com.wen.mapper;

import com.wen.wrapper.SetWrapper;
import com.wen.wrapper.WhereWrapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * 封装基本CRUD
 *
 * @author calwen
 * @date 2022/7/9
 */
public interface BaseMapper {
    <T> Integer selectCount(Class<T> targetClass, WhereWrapper whereWrapper);

    <T> Integer selectCount(Class<T> targetClass);


    <T> ArrayList<T> selectTargets(Class<T> targetClass, WhereWrapper whereWrapper);

    <T> ArrayList<T> selectTargets(Class<T> targetClass);

    <T> T selectTarget(Class<T> targetClass, WhereWrapper wrapper);

    <T> T selectTarget(Class<T> targetClass);

    <T> int insertTarget(T target);

    <T> int replaceTarget(T target);

    <T> int deleteTarget(Class<T> targetClass, WhereWrapper whereWrapper);

    <T> int updateTarget(Class<T> targetClass, SetWrapper setWrapper, WhereWrapper whereWrapper);
}
