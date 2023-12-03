package com.atlisheng.servlet;

import com.atlisheng.servlet.jdbcutil.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SaveDept extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取用户提交数据
        String deptno=request.getParameter("deptno");
        String dname=request.getParameter("dname");
        String loc=request.getParameter("loc");
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int count=0;
        try {
            conn= DBUtil.getConnection();
            String insertSQl="insert into t_Dept(deptno, dname, loc) values(?, ?, ?)";
            pstmt=conn.prepareStatement(insertSQl);
            pstmt.setString(1,deptno);
            pstmt.setString(2,dname);
            pstmt.setString(3,loc);
            count=pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtil.closeAll(rs,pstmt,conn);
        }
        if (count==1){
            //这儿把表单提交的方式改成了get请求，所以才方便使用转发，应该使用post请求，然后用重定向
            //这儿有主键，刷新不会重复提交数据，只会报错，但是没有主键很难说
            //request.getRequestDispatcher("/listDept").forward(request,response);
            //重定向，测试成功
            response.sendRedirect(request.getContextPath()+"/listDept");
        }else{
            //request.getRequestDispatcher("/error.html").forward(request,response);
            response.sendRedirect(request.getContextPath()+"/error.html");
        }
    }
}
