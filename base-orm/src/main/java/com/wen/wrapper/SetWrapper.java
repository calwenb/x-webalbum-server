package com.wen.wrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class SetWrapper extends AbstractWrapper implements Wrapper {


    @Override
    public HashMap<String, Object> getResult() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        ArrayList<Node> whereList = getWhereList();
        ArrayList<Object> setList = new ArrayList<>();
        StringBuffer whereSQL = new StringBuffer();
        for (Node node : whereList) {
            String operating = node.getOperating();
            String field = node.getField();
            Object value = node.getValue();
            switch (operating) {
                case "HEAD":
                    whereSQL.append(" SET ")
                            .append(" `").append(field).append("` ").append("= ? ");
                    break;
                default:
                    whereSQL.append(operating).append(" `").append(field).append("` ").append("= ? ");
            }
            setList.add(value);
        }
        map.put("sql", whereSQL);
        map.put("setSQL", setList);
        return map;
    }
}
