package com.wen.baseorm.mapper;

import com.wen.baseorm.wrapper.SetWrapper;
import com.wen.baseorm.wrapper.QueryWrapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * 封装基本CRUD
 *
 * @author calwen
 * @date 2022/7/9
 */
public interface BaseMapper {

    <T> Integer selectCount(Class<T> targetClass, QueryWrapper queryWrapper);

    <T> Integer selectCount(Class<T> targetClass);

    <T> ArrayList<T> selectSQL(String sql, Object[] setSqls, Class<T> targetClass);

    <T> ArrayList<T> selectTargets(Class<T> targetClass, QueryWrapper queryWrapper);

    <T> ArrayList<T> selectTargets(Class<T> targetClass);

    <T> T selectTarget(Class<T> targetClass, QueryWrapper wrapper);

    <T> T selectTarget(Class<T> targetClass);

    <T> int insertTarget(T target);

    <T> int replaceTarget(T target);

    <T> int deleteTarget(Class<T> targetClass, QueryWrapper queryWrapper);

    <T> int updateTarget(Class<T> targetClass, SetWrapper setWrapper, QueryWrapper queryWrapper);
}
