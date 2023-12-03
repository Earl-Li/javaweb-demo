package com.atlisheng.servlet;

import com.atlisheng.servlet.jdbcutil.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;

@WebServlet("/user/login")
public class UserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //验证用户名和密码是否正确
        //获取用户名和密码
        String user=request.getParameter("username");
        String password=request.getParameter("password");
        //连接数据库验证用户名和密码
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
                //成功就给state赋值true
                state=true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //登录成功或失败
        if (state){
            //如果登录成功，把用户名放入session对象，供其他路径判断是否有登录行为
            HttpSession session=request.getSession();//都到这儿了肯定存在session对象
            session.setAttribute("user",user);
            //登录成功后，查看用户是否勾选了十天免登录，如果勾选，要将用户名和密码弄成cookie并设置十天有效时间，返回给浏览器
            //注意为了以防万一，把该站点路径设置为cookie的路径
            /**因为cookie的接收方式和表单数据接收方式不同，这儿需要把全局配置的欢迎页取消，新配置一个局部欢迎页welcome，
             * 如果有携带cookie数据，就验证cookie数据是否满足条件，否则跳转index.jsp重新登录,单凭这个验证登录的服务无法
             * 接收cookie完成登录验证
             */
            String key=request.getParameter("key");
            if ("1".equals(key)){
                Cookie cookie1=new Cookie("username",user);
                Cookie cookie2=new Cookie("password",password);//真实情况下数据是加密的，这儿不用担心暴露数据
                cookie1.setMaxAge(60*60*24*10);
                cookie2.setMaxAge(60*60*24*10);
                cookie1.setPath(request.getContextPath());
                cookie2.setPath(request.getContextPath());
                //cookie发送给浏览器
                response.addCookie(cookie1);
                response.addCookie(cookie2);
            }
            response.sendRedirect(request.getContextPath()+"/dept/list");
        }else {
            response.sendRedirect(request.getContextPath()+"/loginFalse.jsp");
        }
    }
}
