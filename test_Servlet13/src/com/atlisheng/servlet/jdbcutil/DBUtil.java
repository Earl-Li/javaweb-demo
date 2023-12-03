package com.atlisheng.servlet.jdbcutil;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * 数据库封装工具类
 * */
public class DBUtil {
    public static ResourceBundle bundle=ResourceBundle.getBundle("resources.dbinfo");
    static {
        try {
            Class.forName(bundle.getString("driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private DBUtil(){

    }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(bundle.getString("url"),bundle.getString("user"),bundle.getString("password"));
    }
    public static void closeAll(ResultSet rs, PreparedStatement pstmt,Connection conn){
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
