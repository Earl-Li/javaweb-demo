package com.atlisheng.servlet;

import com.atlisheng.servlet.bean.Dept;
import com.atlisheng.servlet.jdbcutil.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 核心业务类
 * */
@WebServlet({"/dept/list","/dept/detail","/dept/modify","/dept/delete","/dept/save","/dept/logout"})
public class DeptServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            //有了过滤器，删掉验证已登录的部分，经测试，不登录无法直接访问
            if ("/dept/list".equals(request.getServletPath())) {
                doList(request, response);
            } else if ("/dept/detail".equals(request.getServletPath())) {
                doDetail(request, response);
            } else if ("/dept/modify".equals(request.getServletPath())) {
                doModify(request, response);
            } else if ("/dept/delete".equals(request.getServletPath())) {
                doDel(request, response);
            } else if ("/dept/save".equals(request.getServletPath())) {
                doSave(request, response);
            } else if ("/dept/logout".equals(request.getServletPath())) {
                doLogOut(request, response);
            }else{
                response.sendRedirect(request.getContextPath());
            }
    }

    private void doLogOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session=request.getSession(false);
        if (session!=null){
            session.invalidate();
            Cookie[] cookies=request.getCookies();
            if (cookies!=null){
                for (Cookie cookie:cookies) {
                    if ("username".equals(cookie.getName())||"password".equals(cookie.getName())){
                        cookie.setPath(request.getContextPath());
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                }
            }
        }
        response.sendRedirect(request.getContextPath());
    }

    private void doSave(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String deptno=request.getParameter("deptno");
        String dname=request.getParameter("dname");
        String loc=request.getParameter("loc");
        System.out.println(deptno+""+dname);
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
            response.sendRedirect(request.getContextPath()+"/dept/list");
        }else{
            response.sendRedirect(request.getContextPath()+"/error.jsp");
        }
    }

    private void doDel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String deptno=request.getParameter("deptno");
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int count=0;
        try {
            conn= DBUtil.getConnection();
            String insertSQl="delete from t_Dept where deptno=?";
            pstmt=conn.prepareStatement(insertSQl);
            pstmt.setString(1,deptno);
            count=pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtil.closeAll(rs,pstmt,conn);
        }
        if (count==1){
            response.sendRedirect(request.getContextPath()+"/dept/list");
        }else{
            response.sendRedirect(request.getContextPath()+"/error.jsp");
        }
    }

    private void doModify(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
            response.sendRedirect(request.getContextPath()+"/dept/list");
        }else{
            response.sendRedirect(request.getContextPath()+"/error.jsp");
        }
    }

    private void doDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String deptno=request.getParameter("deptno");
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        Dept dept=new Dept();
        try {
            conn= DBUtil.getConnection();
            String selectSQl="select deptno,dname,loc from t_Dept where deptno=?";
            pstmt=conn.prepareStatement(selectSQl);
            pstmt.setString(1,deptno);
            rs= pstmt.executeQuery();
            while (rs.next()) {
                dept.setDeptno(rs.getString("deptno"));
                dept.setDname(rs.getString("dname"));
                dept.setLoc(rs.getString("loc"));
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtil.closeAll(rs,pstmt,conn);
        }
        request.setAttribute("dept",dept);
        request.getRequestDispatcher("/detail.jsp").forward(request,response);
    }

    private void doList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //准备一个List容器，专门用来存储查询到的部门
        List<Dept> depts=new ArrayList<>();
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn= DBUtil.getConnection();
            String selectSQl="select deptno,dname,loc from t_Dept";
            pstmt=conn.prepareStatement(selectSQl);
            rs= pstmt.executeQuery();
            while (rs.next()) {
                String deptno=rs.getString("deptno");
                String dname=rs.getString("dname");
                String loc=rs.getString("loc");
                Dept dept=new Dept();
                dept.setDeptno(deptno);
                dept.setDname(dname);
                dept.setLoc(loc);
                depts.add(dept);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtil.closeAll(rs,pstmt,conn);
        }
        request.setAttribute("deptList",depts);
        request.getRequestDispatcher("/list.jsp").forward(request,response);
    }
}
