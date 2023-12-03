package com.atlisheng.servlet;
/**
 * 表单提交测试重写和不重写对应请求处理方法的报错信息，继承HttpServlet不会要求重写任何方法，重写需要手动重写
 *
 *
 *
 * */
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class Loginservlet extends HttpServlet {
    //5.不写method，只打开get请求处理方法，结果  get请求受理了!
    //6.写method=get，只打开get请求，结果  get请求受理了!
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("get请求受理了!");
    }

    //1.提交post请求，结果：post请求受理了
    //2.post方法注释掉，继续提交post请求，结果  HTTP状态 405 - 方法不允许  此URL不支持Http方法POST，请求行中接收的方法由源服务器知道，但目标资源不支持
    //3.不写method，不重写任何请求处理方法，结果  HTTP状态 405 - 方法不允许
    //4.不写method，打开post请求处理方法，结果   HTTP状态 405 - 方法不允许
    /*@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("post请求受理了");
    }*/
}
