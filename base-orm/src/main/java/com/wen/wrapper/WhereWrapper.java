package com.wen.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

public class WhereWrapper extends AbstractWrapper implements Wrapper {
    private static HashSet<String> set;

    static {
        set = new HashSet<>();
        set.add(OperatEnum.EQ.getOperat());
        set.add(OperatEnum.EQS.getOperat());
        set.add(OperatEnum.NOT_EQ.getOperat());
        set.add(OperatEnum.GREATER.getOperat());
        set.add(OperatEnum.LESS.getOperat());
        set.add(OperatEnum.G_EQ.getOperat());
        set.add(OperatEnum.L_EQ.getOperat());
        set.add(OperatEnum.LIKE.getOperat());
        set.add(OperatEnum.LIKE_LEFT.getOperat());
        set.add(OperatEnum.LIKE_RIGHT.getOperat());

    }

    @Override
    public HashMap<String, Object> getResult() {


        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        ArrayList<Node> whereList = getWhereList();
        ArrayList<Object> setList = new ArrayList<>();
        StringBuffer whereSQL = new StringBuffer();

        for (int i = 0; i < whereList.size(); i++) {
            Node node = whereList.get(i);
            String operating = node.getOperating();
            String field = node.getField();
            Object value = node.getValue();

            if (set.contains(operating)) {
                int len = whereSQL.length();
                if (len == 0) {
                    whereSQL.append(" WHERE ");
                } else {
                    if (!OperatEnum.OR.getOperat().equals(whereSQL.substring(len - 3, len - 1))) {
                        whereSQL.append(" AND ");
                    }
                }

            }
            switch (operating) {

                case "EQ":
                    whereSQL.append(" `").append(field).append("` ").append(" = ? ");
                    setList.add(value);
                    break;
              /*  case "EQS":
                    whereSQL.append(" `").append(field).append("` ").append("<> ? ");
                    break;
/*                case "IN":
                    whereSQL.append(" `").append(field).append("` ").append("IN ()");
                    break;*/
                case "NOT_EQ":
                    whereSQL.append(" `").append(field).append("` ").append(" <> ? ");
                    setList.add(value);
                    break;
                case "GREATER":
                    whereSQL.append(" `").append(field).append("` ").append(" > ? ");
                    setList.add(value);
                    break;
                case "LESS":
                    whereSQL.append(" `").append(field).append("` ").append(" < ? ");
                    setList.add(value);
                    break;
                case "G_EQ":
                    whereSQL.append(" `").append(field).append("` ").append(" >=? ");
                    setList.add(value);
                    break;
                case "L_EQ":
                    whereSQL.append(" `").append(field).append("` ").append(" <= ? ");
                    setList.add(value);
                    break;

                case "OR":
                    whereSQL.append(" OR ");
                    break;
                case "HEAD":
                    whereSQL.append(" WHERE ").append(" `").append(field).append("` ").append(" = ? ");
                    setList.add(value);
                    break;
                case "LIKE":
                    whereSQL.append(" CONCAT( ").append(field).append(" ) ").append(" LIKE '%").append(value).append("%' ");
                    break;
                case "LIKE_LEFT":

                    whereSQL.append(" CONCAT( ").append(field).append(" ) ").append(" LIKE '").append(value).append("%' ");
                    break;
                case "LIKE_RIGHT":

                    whereSQL.append(" CONCAT( ").append(field).append(" ) ").append(" LIKE '%").append(value).append("' ");
                    break;
                case "ORDER":
                    whereSQL.append(" ORDER BY ").append(field);
                    break;
                case "ORDER_DESC":
                    whereSQL.append(" ORDER BY ").append(field).append(" DESC ");
                    break;
                case "LIMIT":
                    whereSQL.append(" LIMIT ").append(field);
//                    setList.add(value);
                    break;
                default:
                    whereSQL.append(operating).append(" `").append(field).append("` ").append("= ? ");
                    setList.add(value);

            }
           /* if (value != null) {
                setList.add(value);
            }*/

        }
        map.put("sql", whereSQL);
        map.put("setSQL", setList);
        return map;
    }


    public WhereWrapper or() {
        Node node = new Node(OperatEnum.OR.getOperat(), null, null);
        super.getWhereList().add(node);
        return this;
    }

    public WhereWrapper eq(String field, Object value) {
        Node node = new Node(OperatEnum.EQ.getOperat(), field, value);
        super.getWhereList().add(node);
        return this;
    }

    public WhereWrapper notEq(String field, Object value) {
        Node node = new Node(OperatEnum.NOT_EQ.getOperat(), field, value);
        super.getWhereList().add(node);
        return this;
    }

    public WhereWrapper greater(String field, Object value) {
        Node node = new Node(OperatEnum.GREATER.getOperat(), field, value);
        super.getWhereList().add(node);
        return this;
    }

    public WhereWrapper less(String field, Object value) {
        Node node = new Node(OperatEnum.LESS.getOperat(), field, value);
        super.getWhereList().add(node);
        return this;
    }

    public WhereWrapper gEq(String field, Object value) {
        Node node = new Node(OperatEnum.G_EQ.getOperat(), field, value);
        super.getWhereList().add(node);
        return this;
    }

    public WhereWrapper lEq(String field, Object value) {
        Node node = new Node(OperatEnum.NOT_EQ.getOperat(), field, value);
        super.getWhereList().add(node);
        return this;
    }


    public WhereWrapper like(String fields, Object value) {
        Node node = new Node(OperatEnum.LIKE.getOperat(), fields, value);
        super.getWhereList().add(node);
        return this;
    }

    public WhereWrapper likeLeft(String fields, Object value) {
        Node node = new Node(OperatEnum.LIKE_LEFT.getOperat(), fields, value);
        super.getWhereList().add(node);
        return this;
    }

    public WhereWrapper likeRight(String fields, Object value) {
        Node node = new Node(OperatEnum.LIKE_RIGHT.getOperat(), fields, value);
        super.getWhereList().add(node);
        return this;
    }

    public WhereWrapper limit(int offset, int rows) {
        Node node = new Node(OperatEnum.LIMIT.getOperat(), offset + "," + rows, null);
        super.getWhereList().add(node);
        return this;
    }

    public WhereWrapper limit(int rows) {
        Node node = new Node(OperatEnum.LIMIT.getOperat(), String.valueOf(rows), null);
        super.getWhereList().add(node);
        return this;
    }

    public WhereWrapper orderDesc(String fields) {
        Node node = new Node(OperatEnum.ORDER_DESC.getOperat(), fields, null);
        super.getWhereList().add(node);
        return this;
    }

    public WhereWrapper order(String fields) {
        Node node = new Node(OperatEnum.ORDER.getOperat(), fields, null);
        super.getWhereList().add(node);
        return this;
    }


    
/*    public WhereWrapper notEq(String field, Object value) {
        Node node = new Node(OperatEnum.NOT_EQ.getOperat(), field, value);
        super.getWhereList().add(node);
        return this;
    }
    public WhereWrapper notEq(String field, Object value) {
        Node node = new Node(OperatEnum.NOT_EQ.getOperat(), field, value);
        super.getWhereList().add(node);
        return this;
    }
    public WhereWrapper notEq(String field, Object value) {
        Node node = new Node(OperatEnum.NOT_EQ.getOperat(), field, value);
        super.getWhereList().add(node);
        return this;
    }
    public WhereWrapper notEq(String field, Object value) {
        Node node = new Node(OperatEnum.NOT_EQ.getOperat(), field, value);
        super.getWhereList().add(node);
        return this;
    }*/


}
