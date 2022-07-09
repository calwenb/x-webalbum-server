package com.wen.baseorm.mapper.impl;

import com.mysql.cj.util.StringUtils;
import com.wen.baseorm.annotation.FieldName;
import com.wen.baseorm.enums.SelectTypeEnum;
import com.wen.baseorm.mapper.BaseMapper;
import com.wen.baseorm.util.CastUtil;
import com.wen.baseorm.util.FieldUtil;
import com.wen.baseorm.util.MapperUtil;
import com.wen.baseorm.wrapper.QueryWrapper;
import com.wen.baseorm.wrapper.SetWrapper;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


/**
 * BaseMapper实现类
 *
 * @author calwen
 * @date 2022/7/9
 */
public class BaseMapperImpl implements BaseMapper {

    @Resource
    DataSource dataSource;
    Connection conn;
    /**
     * PreparedStatement sql 日志
     * AOP输出日志
     */
    public String pstLog;


    private <T> Object baseSelect(Class<T> targetClass, QueryWrapper wrapper, SelectTypeEnum type) {
        try {
            conn = dataSource.getConnection();
            ArrayList<T> targets = new ArrayList<>();

            //反射获取目标信息
            Field[] fields = targetClass.getDeclaredFields();
            //确定表名
            String tableName = MapperUtil.parseTableName(targetClass);

            //sql拼接
            StringBuilder sql = new StringBuilder();

            sql.append("SELECT ");

            if (Objects.equals(type, SelectTypeEnum.COUNT)) {
                sql.append(" COUNT(*) ");
            } else {
                if (wrapper != null) {
                    String selectSQL = wrapper.getSelectSQL();
                    if (!StringUtils.isNullOrEmpty(selectSQL)) {
                        sql.append(selectSQL);
                    } else {
                        sql.append(" * ");
                    }
                }
            }

            sql.append(" FROM ").append(tableName).append(" ");

            List<Object> setFields = null;
            //有Wrapper时
            if (wrapper != null) {
                //条件查询
                HashMap<String, Object> wrapperResult = wrapper.getResult();
                String whereSQL = String.valueOf(wrapperResult.get("sql"));
                setFields = CastUtil.castList(wrapperResult.get("setSQL"), Object.class);
                if (!"".equals(whereSQL)) {
                    sql.append(whereSQL);
                }
            }
            //执行查询
            PreparedStatement pst = conn.prepareStatement(String.valueOf(sql));

            //需要设值时
            for (int i = 0; setFields != null && i < setFields.size(); i++) {
                pst.setObject(i + 1, setFields.get(i));
            }
            pstLog = String.valueOf(pst);
            ResultSet rs = pst.executeQuery();

            //获得类构造器
            Constructor<T> classCon = MapperUtil.getConstructor(targetClass, fields);

            //返回数据解析实体
            while (rs.next()) {
                //count(*)
                if (Objects.equals(type, SelectTypeEnum.COUNT)) {
                    return rs.getInt(1);
                }

                T target = MapperUtil.parseTarget(rs, fields, classCon);
                if (type == SelectTypeEnum.ONE) {
                    return target;
                }
                targets.add(target);
            }
            return targets;
        } catch (SQLException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public <T> Integer selectCount(Class<T> targetClass, QueryWrapper wrapper) {
        return (Integer) baseSelect(targetClass, wrapper, SelectTypeEnum.COUNT);
    }

    @Override
    public <T> Integer selectCount(Class<T> targetClass) {
        return (Integer) baseSelect(targetClass, null, SelectTypeEnum.COUNT);
    }

    /**
     * <T> 规定泛型化
     */
    @Override
    public <T> ArrayList<T> selectTargets(Class<T> targetClass, QueryWrapper wrapper) {
        return (ArrayList<T>) baseSelect(targetClass, wrapper, SelectTypeEnum.ALL);

    }

    @Override
    public <T> ArrayList<T> selectTargets(Class<T> targetClass) {
        return (ArrayList<T>) baseSelect(targetClass, null, SelectTypeEnum.ALL);
    }


    public <T> T selectTarget(Class<T> targetClass, QueryWrapper wrapper) {
        return (T) baseSelect(targetClass, wrapper, SelectTypeEnum.ONE);
    }

    @Override
    public <T> T selectTarget(Class<T> targetClass) {
        return (T) baseSelect(targetClass, null, SelectTypeEnum.ONE);
    }


    /**
     * 自定义 sql语句
     *
     * @param sql
     * @param setSqls
     * @param targetClass
     * @param <T>
     * @return
     */
    public <T> ArrayList<T> selectSQL(String sql, Object[] setSqls, Class<T> targetClass) {

        try {
            conn = dataSource.getConnection();
            ArrayList<T> targets = new ArrayList<>();

            //反射获取目标信息
            Field[] fields = targetClass.getDeclaredFields();

            //执行查询
            PreparedStatement pst = conn.prepareStatement(sql);

            //需要设值时
            for (int i = 0; setSqls != null && i < setSqls.length; i++) {
                pst.setObject(i + 1, setSqls[i]);
            }
            pstLog = String.valueOf(pst);
            ResultSet rs = pst.executeQuery();

            Constructor<T> classCon = MapperUtil.getConstructor(targetClass, fields);
            //返回数据解析实体
            while (rs.next()) {
                T target = MapperUtil.parseTarget(rs, fields, classCon);
                targets.add(target);
            }
            return targets;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }


    public <T> int insertTarget(T target) {
        try {
            conn = dataSource.getConnection();
            Class<?> targetClass = target.getClass();

            //反射获取目标信息
            Field[] fields = targetClass.getDeclaredFields();

            //确定表名
            String tableName = MapperUtil.parseTableName(targetClass);

            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO ").append(tableName);
            sql.append(" ( ");
            for (int i = 1; i < fields.length; i++) {
                String sqlField = "";
                Field field = fields[i];
                field.setAccessible(true);
                if (field.getType().equals(List.class) || field.getType().equals(Map.class)) {
                    continue;
                }
                FieldName fieldNameAnno = field.getDeclaredAnnotation(FieldName.class);
                if (fieldNameAnno != null) {
                    sqlField = fieldNameAnno.value();
                } else {
                    sqlField = FieldUtil.fieldJavaToSql(field.getName());
                }
                if (i == fields.length - 1) {
                    sql.append(sqlField);
                    break;
                }
                sql.append(sqlField).append(",");

            }
            sql.append(" ) ");
            sql.append(" VALUES( ");
            //插入值
            for (int i = 1; i < fields.length; i++) {
                if (i == fields.length - 1) {
                    sql.append(" ? ");
                    break;
                }
                sql.append(" ?, ");
            }

            sql.append(" ) ");

            PreparedStatement pst = conn.prepareStatement(String.valueOf(sql));
            for (int i = 1; i < fields.length; i++) {
                fields[i].setAccessible(true);
                if (fields[i].getType().equals(List.class) || fields[i].getType().equals(Map.class)) {
                    continue;
                }
                pst.setObject(i, fields[i].get(target));
            }
            pstLog = String.valueOf(pst);
            return pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public <T> int replaceTarget(T target) {
        try {
            conn = dataSource.getConnection();
            Class<?> targetClass = target.getClass();

            //反射获取目标信息
            Field[] fields = targetClass.getDeclaredFields();

            //确定表名
            String tableName = MapperUtil.parseTableName(targetClass);

            StringBuilder sql = new StringBuilder();
            sql.append("REPLACE INTO ").append(tableName);
            sql.append(" VALUES( ");
            //插入值
            for (int i = 0; i < fields.length; i++) {
                if (i == fields.length - 1) {
                    sql.append(" ? ");
                    break;
                }
                sql.append(" ?, ");
            }

            sql.append(" ) ");

            PreparedStatement pst = conn.prepareStatement(String.valueOf(sql));

            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                if (fields[i].getType().equals(List.class) || fields[i].getType().equals(Map.class)) {
                    continue;
                }
                pst.setObject(i + 1, fields[i].get(target));
            }
            pstLog = String.valueOf(pst);
            return pst.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public <T> int deleteTarget(Class<T> targetClass, QueryWrapper queryWrapper) {

        try {
            conn = dataSource.getConnection();
            //删除必须指定条件，否则会全表删除
            if (queryWrapper == null) {
                System.out.println("删除必须指定条件，否则会全表删除!!!");
                return 0;
            }

            //条件查询，解析where sql
            HashMap<String, Object> wrapperResult = queryWrapper.getResult();
            String whereSQL = String.valueOf(wrapperResult.get("sql"));
            List<Object> setFields = CastUtil.castList(wrapperResult.get("setSQL"), Object.class);
            if ("".equals(whereSQL)) {
                System.out.println("删除必须指定条件，否则会全表删除!!!");
                return 0;
            }


            String tableName = MapperUtil.parseTableName(targetClass);

            StringBuilder sql = new StringBuilder();
            sql.append("DELETE FROM ").append(tableName);
            sql.append(whereSQL);

            //执行查询
            PreparedStatement pst = conn.prepareStatement(String.valueOf(sql));

            // 占位符设值
            for (int i = 0; setFields != null && i < setFields.size(); i++) {
                pst.setObject(i + 1, setFields.get(i));
            }
            pstLog = String.valueOf(pst);
            return pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public <T> int updateTarget(Class<T> targetClass, SetWrapper setWrapper, QueryWrapper queryWrapper) {

        try {
            conn = dataSource.getConnection();
            //更新必须指定条件
            if (setWrapper == null || queryWrapper == null) {
                System.out.println("更新必须指定set,where");
                return 0;
            }
            //条件查询，解析where sql
            HashMap<String, Object> wrapperResult = queryWrapper.getResult();
            String whereSQL = String.valueOf(wrapperResult.get("sql"));
            List<Object> setWhereSQL = CastUtil.castList(wrapperResult.get("setSQL"), Object.class);

            //条件查询，解析set sql
            wrapperResult = setWrapper.getResult();
            String setSQL = String.valueOf(wrapperResult.get("sql"));
            List<Object> setSetSQL = CastUtil.castList(wrapperResult.get("setSQL"), Object.class);

            if ("".equals(setSQL) || "".equals(whereSQL)) {
                System.out.println("更新必须指定set,where");
                return 0;
            }
            //确定表名
            String tableName = MapperUtil.parseTableName(targetClass);

            //拼接sql
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE ").append(tableName);
            sql.append(setSQL);
            sql.append(whereSQL);

            //执行查询
            PreparedStatement pst = conn.prepareStatement(String.valueOf(sql));

            // 占位符设值
            List<Object> setFields = new ArrayList<>();
            setFields.addAll(setSetSQL);
            setFields.addAll(setWhereSQL);
            for (int i = 0; i < setFields.size(); i++) {
                pst.setObject(i + 1, setFields.get(i));
            }
            pstLog = String.valueOf(pst);
            return pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
