package com.atlisheng.servlet;
/**
 * 1.测试局部欢迎页面和全局欢迎页面，测试包括静态资源和动态资源
 *
 * 2.测试HttpServlet接口的相关方法
 *
 * */
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class WelcomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //测试欢迎页面
        response.setContentType("text/html");//不设置输出中文字符串到浏览器是乱码，不需要天机utf-8，直接"text/html"即可
        System.out.println("是的，我又出来了，欢迎你个老六!");
        PrintWriter out= response.getWriter();
        out.println("是的，我又出来了，欢迎你个老六!");
        System.out.println(request);//org.apache.catalina.connector.RequestFacade@602e59f3

        //测试HttpServlet接口的相关方法
        //获取客户端的IP地址
        System.out.println(request.getRemoteAddr());//127.0.0.1

        //获取应用的根路径
        ServletConfig config=this.getServletConfig();
        ServletContext application=config.getServletContext();
        System.out.println(application.getContextPath());//     /ser04
        System.out.println(request.getContextPath());//   /ser04

        //获取请求方式
        System.out.println(request.getMethod());//GET

        //获取请求的URI,直接显示地址栏上的，比如web站点，不一定是资源的url-pattern
        System.out.println(request.getRequestURI());//   /ser04/

        //获取servlet path，即url-pattern
        System.out.println(request.getServletPath());//   /welcome/myFriend

        //尝试通过名字移除用户提交的数据,事实证明删不掉，且无法用request.getAttribute("username")读取用户输入的数据
        System.out.println(request.getParameter("username"));//zhangsan
        request.removeAttribute("username");
        System.out.println(request.getAttribute("username"));//null
        System.out.println(request.getParameter("username"));//zhangsan

        //转发到静态资源效果展示
        request.getRequestDispatcher("/aidPage.html").forward(request,response);
        //转发后继续执行当前页面代码
        out.print("where can i go");//这一行没了，不知道输出到哪里去了
        System.out.println("执行了吗");//执行了

    }
}
