package com.atlisheng.servlet;
/**
 * 第一个Servlet程序，通过静态资源login.html的超链接访问一个动态资源EmpServlet，实现在浏览器访问数据库的职员数据
 *
 * 1. 该EmpServlet直接实现的jakarta.servlet.Servlet接口，需要全部重写接口中的五个抽象方法：
 *          public void init(ServletConfig servletConfig) throws ServletException {}
 *          public ServletConfig getServletConfig(){ return null;}
 *          public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {}
 *          public String getServletInfo() {return null;}
 *          public void destroy() {
 * 2.   mysql8的Driver完整类名：com.mysql.cj.jdbc.Driver
 *      mysql5的Driver完整类名：com.mysql.jdbc.Driver
 * 3.JavaEE8以后即Tomcat10以后的Servlet完整类名：jakarta.servlet.Servlet
 *              Tomcat10以前的Servlet完整类名：javax.servlet.Servlet
 * */
import jakarta.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class EmpServlet implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest request, ServletResponse response)
            throws ServletException, IOException {
        //设置servlet响应的内容类型
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        //连接数据库
        Connection conn=null;//这个类在JDK中有，只有注册用的Driver是在Mysql驱动中
        PreparedStatement pstmt=null;
        ResultSet res=null;
        //注册驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//这一步需要导入mysql驱动，没有虽然不会报错，但是执行过程中会出现类找不到异常
            //获取连接
            String url="jdbc:mysql://127.0.0.1:3306/bjpowernode";
            String user="root";
            String password="Haworthia0715";
            conn= DriverManager.getConnection(url,user,password);
            //获取预编译前的数据库操作对象
            pstmt=conn.prepareStatement("select ename,sal,empno from emp order by sal");
            //执行sql
            res=pstmt.executeQuery();
            //处理查询结果集
            while(res.next()){
                String ename=res.getString("ename");
                String empno=res.getString("empno");
                String sal=res.getString("sal");
                out.print(empno+" | "+ename+" | "+sal+"<br>");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            //资源释放
            if (res!=null){
                try {
                    res.close();
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
    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
