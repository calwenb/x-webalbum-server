package com.wen.baseorm.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wen.baseorm.annotation.FieldName;
import com.wen.baseorm.annotation.TableName;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Mapper工具类
 * 为BaseMapper提供支持
 *
 * @author calwen
 * @date 2022/7/9
 */

public class MapperUtil {

    /**
     * 解析表名
     *
     * @author calwen
     * @date 2022/7/9
     */

    public static <T> String parseTableName(Class<T> targetClass) {
        //反射获取目标信息
        TableName tableNameAnno = targetClass.getDeclaredAnnotation(TableName.class);
        String className = targetClass.getSimpleName();

        //确定表名
        String tableName;
        if (tableNameAnno != null) {
            tableName = tableNameAnno.value();
        } else {
            tableName = className;
        }
        return tableName;
    }

    /**
     * 解析对象
     *
     * @author calwen
     * @date 2022/7/9
     */
    public static <T> T parseTarget(ResultSet rs, Field[] fields, Constructor<T> classCon) throws SQLException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Object[] fieldsVal = new Object[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            //尝试获取属性上注解
            FieldName fieldName = fields[i].getDeclaredAnnotation(FieldName.class);
            if (fieldName != null) {
                fieldsVal[i] = rs.getObject(String.valueOf(fieldName.value()));
            } else {
                fieldsVal[i] = rs.getObject(FieldUtil.fieldJavaToSql(fields[i].getName()));
            }
            //LocalDateTime转Date
            if (fieldsVal[i] != null && fieldsVal[i].getClass().equals(LocalDateTime.class)) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                fieldsVal[i] = Date.from(objectMapper.convertValue(fieldsVal[i], LocalDateTime.class).atZone(ZoneId.systemDefault()).toInstant());
            }

        }
        T target = classCon.newInstance(fieldsVal);
        return target;
    }

    /**
     * 获取类构造器
     *
     * @author calwen
     * @date 2022/7/9
     */
    public static <T> Constructor<T> getConstructor(Class<T> targetClass, Field[] fields) throws NoSuchMethodException {
        //获取全部属性的类
        Class<?>[] classes = new Class[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            classes[i] = fields[i].getType();
        }
        return targetClass.getDeclaredConstructor(classes);
    }
}
