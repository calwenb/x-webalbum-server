package com.wen.wrapper;

import com.wen.enums.OperatEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AbstractWrapper {


    //WhereNode的内部类
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Node {
        private String operating;
        private String field;
        private Object value;
    }

    private final ArrayList<Node> whereList;

    public AbstractWrapper() {
        whereList = new ArrayList<>();
    }


    public AbstractWrapper add(String field, Object value) {
        Node node = new Node(OperatEnum.HEAD.getOperat(), field, value);
        whereList.add(node);
        return this;
    }


    public AbstractWrapper and(String field, Object value) {
        Node node = new Node(OperatEnum.AND.getOperat(), field, value);
        whereList.add(node);
        return this;
    }

/*    public AbstractWrapper or(String field, Object value) {
        Node node = new Node(OperatEnum.OR.getOperat(), field, value);
        whereList.add(node);
        return this;
    }

    public AbstractWrapper or() {
        Node node = new Node(OperatEnum.OR.getOperat(),null,null);
        whereList.add(node);
        return this;
    }*/

    public ArrayList<Node> getWhereList() {
        return whereList;
    }

    /**
     * 抽象方法
     * 解析包装器数据结构返回sql语句和值列表
     *
     * @return
     */
    public abstract HashMap<String, Object> getResult();

}
