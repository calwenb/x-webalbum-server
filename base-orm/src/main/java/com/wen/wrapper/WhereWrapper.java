package com.wen.wrapper;

import com.wen.enums.OperatEnum;

import java.util.*;

/**
 * WhereWrapper类
 * 构建查询sql
 *
 * @author calwen
 * @date 2022/7/9
 */

public class WhereWrapper extends AbstractWrapper implements Wrapper {
    private static HashSet<OperatEnum> needWhereSet;
    private String selectSQL;

    public String getSelectSQL() {
        return selectSQL;
    }

    static {
        needWhereSet = new HashSet<>();
        needWhereSet.add(OperatEnum.EQ);
        needWhereSet.add(OperatEnum.EQS);
        needWhereSet.add(OperatEnum.NOT_EQ);
        needWhereSet.add(OperatEnum.GREATER);
        needWhereSet.add(OperatEnum.LESS);
        needWhereSet.add(OperatEnum.G_EQ);
        needWhereSet.add(OperatEnum.L_EQ);
        needWhereSet.add(OperatEnum.LIKE);
        needWhereSet.add(OperatEnum.LIKE_LEFT);
        needWhereSet.add(OperatEnum.LIKE_RIGHT);
        needWhereSet.add(OperatEnum.IN);

    }

    @Override
    public HashMap<String, Object> getResult() {


        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        ArrayList<Node> whereList = getWhereList();
        ArrayList<Object> setList = new ArrayList<>();
        StringBuffer whereSQL = new StringBuffer();

        for (Node node : whereList) {
            OperatEnum operating = node.getOperating();
            System.out.println("operating " + operating);
            String field = node.getField();
            Object value = node.getValue();

            if (needWhereSet.contains(operating)) {
                int len = whereSQL.length();
                if (len == 0) {
                    whereSQL.append(" WHERE ");
                } else {
                    if (!"OR".equals(whereSQL.substring(len - 3, len - 1))) {
                        whereSQL.append(" AND ");
                    }
                }

            }
            switch (operating) {
                case EQ:
                    whereSQL.append(" `").append(field).append("` ").append(" = ? ");
                    setList.add(value);
                    break;
              /*  case "EQS":
                    whereSQL.append(" `").append(field).append("` ").append("<> ? ");
                    break;
               */
                case IN:
                    whereSQL.append(" `").append(field).append("` ").append(" IN ( ");
                    Object[] inValues = (Object[]) value;
                    for (int j = 0; j < inValues.length; j++) {
                        if (j != 0) {
                            whereSQL.append(" , ");
                        }
                        whereSQL.append(" ? ");
                        setList.add(inValues[j]);
                    }
                    whereSQL.append(" ) ");
                    break;
                case NOT_EQ:
                    whereSQL.append(" `").append(field).append("` ").append(" <> ? ");
                    setList.add(value);
                    break;
                case GREATER:
                    whereSQL.append(" `").append(field).append("` ").append(" > ? ");
                    setList.add(value);
                    break;
                case LESS:
                    whereSQL.append(" `").append(field).append("` ").append(" < ? ");
                    setList.add(value);
                    break;
                case G_EQ:
                    whereSQL.append(" `").append(field).append("` ").append(" >=? ");
                    setList.add(value);
                    break;
                case L_EQ:
                    whereSQL.append(" `").append(field).append("` ").append(" <= ? ");
                    setList.add(value);
                    break;

                case OR:
                    whereSQL.append(" OR ");
                    break;
                case HEAD:
                    whereSQL.append(" WHERE ").append(" `").append(field).append("` ").append(" = ? ");
                    setList.add(value);
                    break;
                case LIKE:
                    whereSQL.append(" CONCAT( ").append(field).append(" ) ").append(" LIKE '%").append(value).append("%' ");
                    break;
                case LIKE_LEFT:

                    whereSQL.append(" CONCAT( ").append(field).append(" ) ").append(" LIKE '").append(value).append("%' ");
                    break;
                case LIKE_RIGHT:

                    whereSQL.append(" CONCAT( ").append(field).append(" ) ").append(" LIKE '%").append(value).append("' ");
                    break;
                case ORDER:
                    whereSQL.append(" ORDER BY ").append(field);
                    break;
                case ORDER_DESC:
                    whereSQL.append(" ORDER BY ").append(field).append(" DESC ");
                    break;
                case LIMIT:
                    whereSQL.append(" LIMIT ").append(field);
                    break;
                default:
                    whereSQL.append(operating).append(" `").append(field).append("` ").append("= ? ");
                    setList.add(value);

            }

        }
        map.put("sql", whereSQL);
        map.put("setSQL", setList);
        return map;
    }


    public WhereWrapper or() {
        Node node = new Node(OperatEnum.OR, null, null);
        super.getWhereList().add(node);
        return this;
    }

    public WhereWrapper eq(String field, Object value) {
        Node node = new Node(OperatEnum.EQ, field, value);
        super.getWhereList().add(node);
        return this;
    }

    public WhereWrapper in(String field, Object... inValues) {
        Node node = new Node(OperatEnum.IN, field, inValues);
        super.getWhereList().add(node);
        return this;
    }

    public WhereWrapper notEq(String field, Object value) {
        Node node = new Node(OperatEnum.NOT_EQ, field, value);
        super.getWhereList().add(node);
        return this;
    }

    public WhereWrapper greater(String field, Object value) {
        Node node = new Node(OperatEnum.GREATER, field, value);
        super.getWhereList().add(node);
        return this;
    }

    public WhereWrapper less(String field, Object value) {
        Node node = new Node(OperatEnum.LESS, field, value);
        super.getWhereList().add(node);
        return this;
    }

    public WhereWrapper gEq(String field, Object value) {
        Node node = new Node(OperatEnum.G_EQ, field, value);
        super.getWhereList().add(node);
        return this;
    }

    public WhereWrapper lEq(String field, Object value) {
        Node node = new Node(OperatEnum.L_EQ, field, value);
        super.getWhereList().add(node);
        return this;
    }


    public WhereWrapper like(String fields, Object value) {
        Node node = new Node(OperatEnum.LIKE, fields, value);
        super.getWhereList().add(node);
        return this;
    }

    public WhereWrapper likeLeft(String fields, Object value) {
        Node node = new Node(OperatEnum.LIKE_LEFT, fields, value);
        super.getWhereList().add(node);
        return this;
    }

    public WhereWrapper likeRight(String fields, Object value) {
        Node node = new Node(OperatEnum.LIKE_RIGHT, fields, value);
        super.getWhereList().add(node);
        return this;
    }

    public WhereWrapper limit(int offset, int rows) {
        Node node = new Node(OperatEnum.LIMIT, offset + "," + rows, null);
        super.getWhereList().add(node);
        return this;
    }

    public WhereWrapper limit(int rows) {
        Node node = new Node(OperatEnum.LIMIT, String.valueOf(rows), null);
        super.getWhereList().add(node);
        return this;
    }

    public WhereWrapper orderDesc(String fields) {
        Node node = new Node(OperatEnum.ORDER_DESC, fields, null);
        super.getWhereList().add(node);
        return this;
    }

    public WhereWrapper order(String fields) {
        Node node = new Node(OperatEnum.ORDER, fields, null);
        super.getWhereList().add(node);
        return this;
    }


    public WhereWrapper select(String selectSQL) {
        this.selectSQL = selectSQL;
        return this;
    }


}
