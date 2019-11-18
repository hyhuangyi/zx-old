package cn.common.util.comm;

import cn.common.entity.SysTreeDict;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/***
 * 通过java Class 创建 建表语句
 */
public class CreateTableUtil {
    public static Map<String, String> javaProperty2SqlColumnMap = new HashMap<>();

    static {
        javaProperty2SqlColumnMap.put("Integer", "INTEGER");
        javaProperty2SqlColumnMap.put("Short", "tinyint");
        javaProperty2SqlColumnMap.put("Long", "bigint");
        javaProperty2SqlColumnMap.put("BigDecimal", "decimal(19,2)");
        javaProperty2SqlColumnMap.put("Double", "double precision not null");
        javaProperty2SqlColumnMap.put("Float", "float");
        javaProperty2SqlColumnMap.put("Boolean", "bit");
        javaProperty2SqlColumnMap.put("Timestamp", "datetime");
        javaProperty2SqlColumnMap.put("String", "VARCHAR(255)");
        javaProperty2SqlColumnMap.put("Date", "timestamp NULL");
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        createTable(SysTreeDict.class, null);
    }

    public static String createTable(Class obj, String tableName) {


        Field[] fields = null;
        fields = obj.getDeclaredFields();
        String param = null;
        String column = null;
        StringBuilder sb = null;
        sb = new StringBuilder(50);
        if (tableName == null || tableName.equals("")) {
            /*未传表明默认用类名*/
            tableName = obj.getName();
            tableName = tableName.substring(tableName.lastIndexOf(".") + 1);
            tableName = getPropName(tableName).toLowerCase().substring(1);
        }
        sb.append("\r\ndrop table if exists  ").append(tableName).append(";\r\n");
        sb.append("create table ").append(tableName).append(" ( \r\n");
        System.out.println(tableName);
        boolean firstId = true;
        File file = null;
        for (Field f : fields) {
            column = f.getName();
            if (column.equals("serialVersionUID")) {
                continue;
            }
            param = f.getType().getSimpleName();
            sb.append(getPropName(column));//一般第一个是主键
            sb.append(" ").append(javaProperty2SqlColumnMap.get(param)).append(" ");

            if (firstId) {//类型转换
                sb.append(" PRIMARY KEY ");
                firstId = false;
            }
            sb.append(",\n ");
        }
        String sql = null;
        sql = sb.toString();
        //去掉最后一个逗号
        int lastIndex = sql.lastIndexOf(",");
        sql = sql.substring(0, lastIndex) + sql.substring(lastIndex + 1);

        sql = sql.substring(0, sql.length() - 1) + " )ENGINE =INNODB DEFAULT  CHARSET= utf8;\r\n";
        System.out.println("sql :" + sql);
        return sql;
    }

    private static String getPropName(String name) {
        String propName = "";
        for (char c : name.toCharArray()) {
            if (c >= 65 && c <= 90) {
                propName += "_" + c;
            } else {
                propName += c;
            }
        }
        return propName.toUpperCase();
    }
}

