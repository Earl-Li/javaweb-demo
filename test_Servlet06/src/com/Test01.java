package com;
/**
 * 测试webapp中属性配置文件能放的位置
 * 1.根目录src下
 * 2.一级包com下
 * 3.二级包com.atlisheng下
 * 4.三级包com/atlisheng/servlet下
 * 5.四级包com/atlisheng/servlet/jdbcutil/dbinfo下
 * 以上测试竟然都行，而且同样是javaweb项目，探索结束，以后一律所有属性配置文件放到src/resources下，提前规避所有可能的问题，md折磨死我了
 *
 *          草特么的，这个属性配置文件所处的位置最多三层目录，再多一层都找不到，这个错误极难纠正
 *             //多一层都不行，离谱。就这儿不行，ser06四层也可以
 *              别管那么多，没搞出来具体原因，结合老杜视频做法和网上的一些博文，认为服务器打包方式的问题会导致不在src下的resources中的properties文件可能存在找不到的问题
 *              ，以后在服务器上部署，所有属性配置文件一律放到src/resources下，提前规避所有可能的问题，md折磨死我了
 *
 * */
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ResourceBundle;

public class Test01 extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            /**1.经过测试,属性配置文件在根目录下没问题
            ResourceBundle bundle=ResourceBundle.getBundle("dbinfo");//结果正常*/

            /**2.经过测试,属性配置文件在一级包下没问题
            ResourceBundle bundle=ResourceBundle.getBundle("com/dbinfo");//结果正常*/

            /**3.经过测试,属性配置文件在二级包下没问题
            ResourceBundle bundle=ResourceBundle.getBundle("com/atlisheng/dbinfo");//结果正常*/

            /**4.经过测试,属性配置文件在三级包下没问题
            ResourceBundle bundle=ResourceBundle.getBundle("com/atlisheng/servlet/dbinfo");//结果正常*/

            //经过测试,属性配置文件在四级包下没问题
            ResourceBundle bundle=ResourceBundle.getBundle("com/atlisheng/servlet/jdbcutil/dbinfo");//结果正常
            Class.forName(bundle.getString("driver"));
            conn= DriverManager.getConnection(bundle.getString("url"), bundle.getString("user"),bundle.getString("password") );
            String selectSQl="select deptno,dname from t_Dept";
            pstmt=conn.prepareStatement(selectSQl);
            rs= pstmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("deptno")+" | "+rs.getString("dname"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt!=null){
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
