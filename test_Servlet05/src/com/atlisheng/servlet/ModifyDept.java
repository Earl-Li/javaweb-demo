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

public class ModifyDept extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String deptno=request.getParameter("deptno");
        String dname=request.getParameter("dname");
        String loc=request.getParameter("loc");
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int count=0;
        try {
            conn= DBUtil.getConnection();
            String insertSQl="update t_Dept set dname=?,loc=? where deptno=?";
            pstmt=conn.prepareStatement(insertSQl);
            pstmt.setString(1,dname);
            pstmt.setString(2,loc);
            pstmt.setString(3,deptno);
            count=pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtil.closeAll(rs,pstmt,conn);
        }
        if (count==1){
            //request.getRequestDispatcher("/listDept").forward(request,response);
            response.sendRedirect(request.getContextPath()+"/listDept");
        }else{
            //request.getRequestDispatcher("/error.html").forward(request,response);
            response.sendRedirect(request.getContextPath()+"/error.html");
        }
    }
}
