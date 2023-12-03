package com.atlisheng.servlet;

import com.atlisheng.servlet.bean.User;
import com.atlisheng.servlet.jdbcutil.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 用户登录信息验证类
 * */
@WebServlet("/user/login")
public class UserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user=request.getParameter("username");
        String password=request.getParameter("password");
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        boolean state=false;

        try {
            conn= DBUtil.getConnection();
            String selectSQL="select * from t_user where user=? and password=?";
            pstmt= conn.prepareStatement(selectSQL);
            pstmt.setString(1,user);
            pstmt.setString(2,password);
            rs=pstmt.executeQuery();
            if (rs.next()){
                state=true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (state){
            HttpSession session=request.getSession();
            //封装user数据
            User user1=new User(user,password);
            session.setAttribute("user",user1);
            String key=request.getParameter("key");
            if ("1".equals(key)){
                Cookie cookie1=new Cookie("username",user);
                Cookie cookie2=new Cookie("password",password);
                cookie1.setMaxAge(60*60*24*10);
                cookie2.setMaxAge(60*60*24*10);
                cookie1.setPath(request.getContextPath());
                cookie2.setPath(request.getContextPath());
                response.addCookie(cookie1);
                response.addCookie(cookie2);
            }
            response.sendRedirect(request.getContextPath()+"/dept/list");
        }else {
            response.sendRedirect(request.getContextPath()+"/loginFalse.jsp");
        }
    }
}
