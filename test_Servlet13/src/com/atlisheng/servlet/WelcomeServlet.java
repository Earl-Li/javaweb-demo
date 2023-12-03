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
 * 究极改良版：结合Servlet、JSP、EL表达式和JSTL标签和过滤器针对OA项目的究极优化版
 * 十天免登录判断类
 * */
@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies=request.getCookies();
        String username=null;
        String password=null;
        if (cookies!=null){
            for (Cookie cookie:cookies) {
                String name=cookie.getName();
                if ("username".equals(name)){
                    username=cookie.getValue();
                }else if ("password".equals(name)){
                    password=cookie.getValue();
                }
            }
        }
        if (username!=null&&password!=null){
            Connection conn=null;
            PreparedStatement pstmt=null;
            ResultSet rs=null;
            boolean state=false;

            try {
                conn= DBUtil.getConnection();
                String selectSQL="select * from t_user where user=? and password=?";
                pstmt= conn.prepareStatement(selectSQL);
                pstmt.setString(1,username);
                pstmt.setString(2,password);
                rs=pstmt.executeQuery();
                if (rs.next()){
                    state=true;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            if (state) {
                HttpSession session = request.getSession();
                session.setAttribute("user", new User(username,password));
                response.sendRedirect(request.getContextPath()+"/dept/list");
            }else{
                response.sendRedirect(request.getContextPath()+"/index.jsp");
            }
        }else{
            response.sendRedirect(request.getContextPath()+"/index.jsp");
        }
    }
}
