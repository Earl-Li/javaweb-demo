package com.atlisheng.servlet.jdbcutil;
/**
 * Javaweb项目一定要把属性配置文件放到resources目录下，不放一旦出错极难排查，原因不清楚
 *
 * */
import java.sql.*;
import java.util.ResourceBundle;

public class DBUtil {
    //静态变量类加载绑定属性配置资源，这么配置是把资源绑定器的执行放在静态代码块以前，是注册成功的前提
    /**草特么的，这个属性配置文件所处的位置最多三层目录，再多一层都找不到，这个错误极难纠正
     //多一层都不行，离谱。就这儿不行，ser06四层竟然可以

     别管那么多，没搞出来具体原因，结合老杜视频做法和网上的一些博文，认为服务器打包方式的问题会导致不在src下的resources中的properties文件可能存在找不到的问题
     ，以后在服务器上部署，所有属性配置文件一律放到src/resources下，提前规避所有可能的问题，md折磨死我了

     ResourceBundle.getBundle("resources.dbinfo");仍然要带包名resources，就使用点连接，跟老杜一样，避免不必要的错误
     */
    private static ResourceBundle bundle=ResourceBundle.getBundle("resources.dbinfo");
    //静态代码块注册驱动
    static{
        try {
            Class.forName(bundle.getString("driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //私有化无参构造方法，工具类标配
    private DBUtil(){

    }
    //获取连接器对象
    public static Connection getConnection() throws SQLException {
        Connection conn=DriverManager.getConnection(bundle.getString("url"),bundle.getString("user"),bundle.getString("password"));
        return conn;
    }
    //释放资源
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
