package com.atlisheng.servlet;

import com.atlisheng.servlet.jdbcutil.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DetailDept extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取用户提交数据
        String deptno=request.getParameter("deptno");

        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        out.print("<!DOCTYPE html>");
        out.print("<html>");
	    out.print("<head>");
		out.print("<meta charset='utf-8' />");
		out.print("<title>部门详情</title>");
	    out.print("</head>");
	    out.print("<body>");
        out.print("       部门详情");
        out.print("       <hr>");
        out.print("<form action='/ser05/modifyDept' >");

        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            //获取数据库链接对象
            conn= DBUtil.getConnection();
            //获取预编译的数据库操作对象
            String selectSQl="select deptno,dname,loc from t_Dept where deptno=?";
            pstmt=conn.prepareStatement(selectSQl);
            pstmt.setString(1,deptno);
            //处理查询结果集
            rs= pstmt.executeQuery();
            while (rs.next()) {
                out.print("    部门编号<input type='text' name='deptno' value='"+rs.getString("deptno")+"' readonly />");
                out.print("       <br>");
                out.print("    部门名称<input type='text' name='dname'  value='"+rs.getString("dname")+"'/>");
                out.print("       <br>");
                out.print("    部门位置<input type='text' name='loc' value='"+rs.getString("loc")+"'/>");
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            out.print("       <br>");
            out.print("<input type='submit' value='修改'/>");
            out.print("</form>");
            out.print("       <hr>");
            out.print("<a href='"+request.getContextPath()+"/listDept'>返回</a>");
            out.print("</body>");
            out.print("</html>");
            DBUtil.closeAll(rs,pstmt,conn);
        }
    }
}
