package cn.txcc.utils;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * @author May
 * @version 1.0
 */
public class UDtils {
    private static ResourceBundle bundle;
    private static String uname;
    private static String url;
    private static String pwd;
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            bundle = ResourceBundle.getBundle("cn.txcc.resource.news");
            uname = bundle.getString("uname");
            pwd = bundle.getString("pwd");
            url = bundle.getString("url");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, uname, pwd);
    }

    public static void close(Connection conn, Statement stmt, ResultSet rs){
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(stmt != null){
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
