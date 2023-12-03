package com.atlisheng.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/test/cookie")
public class Testcookie extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取同一个浏览器对应的唯一session对象，该session对象可以手动销毁，还可以用下面方式再重新创建
        HttpSession session=request.getSession();
        PrintWriter out=response.getWriter();
        out.print(session);

        //创建cookie对象，cookie对象创建必须传入数据,且两个数据都必须是字符串，没有无参构造方法
        Cookie cookie=new Cookie("productId","1234567890");

        //设置cookie有效时间一小时，该cookie在一小时内会自动提交给关联路径
        cookie.setMaxAge(60*60);

        //设置有效时间为0，来删除cookie,主要用于删除浏览器上的名字叫productId的cookie
        cookie.setMaxAge(0);

        //设置cookie的有效时间<0，该cookie不会被存储在硬盘中,效果与不设置有效时间完全相同
        cookie.setMaxAge(-1);

        //将cookie响应到浏览器
        response.addCookie(cookie);

        //可以发送多个cookie到浏览器,Chrome是可以显示请求头和响应头里面的cookie的
        Cookie cookie1=new Cookie("username","zhangsan");
        response.addCookie(cookie1);

        //重定向到/test/get测试接收cookie
        response.sendRedirect(request.getContextPath()+"/test/get");

    }
}
