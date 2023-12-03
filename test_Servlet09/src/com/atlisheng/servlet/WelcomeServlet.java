package com.atlisheng.servlet;

import com.atlisheng.servlet.jdbcutil.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取cookie并判定有无username和password，并验证是否正确
        Cookie[] cookies=request.getCookies();
        String username=null;
        String password=null;
        if (cookies!=null){
            //取出需要的用户名和密码
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
            //验证用户名和密码是否正确，正确跳转登录页面，错误和其他情况一样，跳转登录页面
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
                /**当前的问题，退出不了了，因为重定向登录界面时又提交了cookie，所以退出时需要把相关两个cookie销毁*/
                //如果登录成功，把用户名放入session对象，供其他路径判断是否有登录行为，这里如果没有放用户到session中，
                //关掉浏览器再打开dept/list页面会出错，因为/dept/list首先检查session有没有username，没有就跳欢迎页面
                //如果不放进session就会一直在两个页面之间跳转,经过测试跳转20次就会报错
                HttpSession session = request.getSession();//都到这儿了肯定存在session对象
                session.setAttribute("user", username);
                System.out.print(1);
                /**
                 * 11111111111111111111
                 * */
                response.sendRedirect(request.getContextPath()+"/dept/list");
            }else{
                //登录失败跳转登录页面
                response.sendRedirect(request.getContextPath()+"/index.jsp");
            }
        }else{
            //没有相应的cookie等其他情况一律跳转登录页面
            response.sendRedirect(request.getContextPath()+"/index.jsp");
        }
    }
}
