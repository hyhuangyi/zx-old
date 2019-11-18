package cn.common.util.sys;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库连接测试工具
 * Created by huangYi on 2018/3/10
 **/
public class JdbcUtil {

    private static String mysql_url = "jdbc:mysql://rm-bp11200j2s41yt7096o.mysql.rds.aliyuncs.com:3306/mcwp";
    private static String mysql_username = "qdtest";
    private static String mysql_password = "test2018%";
    private static String mysql_driver = "com.mysql.jdbc.Driver";

    private static String db2_url = "jdbc:db2://127.0.0.1:50000/ydwd";
    private static String db2_username = "db2admin";
    private static String db2_password = "123456";
    private static String db2_driver = "com.ibm.db2.jcc.DB2Driver";

    public static Connection getConnection() {

        try {
            Class.forName(db2_driver);
            //Class.forName(mysql_driver);
            //Connection conn = DriverManager.getConnection(mysql_url, mysql_username, mysql_password);
            Connection conn = DriverManager.getConnection(db2_url, db2_username, db2_password);
            return conn;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    /*
    public static void main(String[] args) throws SQLException {
        Connection cc = JdbcUtil.getConnection();
        if (!cc.isClosed()) System.out.println("Succeeded connecting to the Database!");
        Statement statement = cc.createStatement();
        String sql = "select * from CRM_CUSTOMER";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString("CUSTOMER_ID"));
        }
    }*/
}
