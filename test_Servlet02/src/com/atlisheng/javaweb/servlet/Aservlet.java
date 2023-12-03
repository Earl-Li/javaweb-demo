package com.atlisheng.javaweb.servlet;
/**
 * test_Servlet02主要讨论Servlet对象的生命周期
 *
 *      1.Servlet对象响应第一次请求后不会被销毁，此后每次收到请求都只会去执行service方法，该Servlet对象会在服务器关闭的时候才被销毁
 *      2.Servlet对象整个生命周期中，无参构造、init方法、destory方法只被调用一次；service方法收到一次请求就会调用一次
 *      3.Servlet类中没有无参构造无法实例化Servlet对象，浏览器报500错误，表示服务器内部错误，Servlet开发中，不建议定义构造方法，构造不当一不小心就会导致Servlet对象无法实例化
 *      4.在web.xml文件中的servlet标签中添加<load-on-startup>整数</load-on-startup>子标签能让服务器在启动的时候就创建Servlet对象
 *
 * */
import jakarta.servlet.*;

import java.io.IOException;

public class Aservlet implements Servlet {
    //无参构造方法验证服务器何时创建servlet对象
    public Aservlet(){
        System.out.println("Aservlet对象的无参构造方法执行了");
    }
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("Aservlet的init方法执行了");
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("Aservlet执行了");
    }

    @Override
    public void destroy() {
        System.out.println("Aservlet的destroy方法执行了");
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public String getServletInfo() {
        return null;
    }
}
