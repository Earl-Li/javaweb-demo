package com.atlisheng.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 在这个Servlet类上使用WebServlet注解，在ReflectAnnotation类中使用反射机制获取这个Servlet类的注解信息
 * */
@WebServlet(name = "testWS",urlPatterns = {"/testWebServlet","/testWS","/testwebservlet"},
        initParams = {@WebInitParam(name="url",value="jdbc:mysql://127.0.0.1:3306/bjpowernode"),
                @WebInitParam(name="username",value = "root"),@WebInitParam(name="password",value="Haworthia0715")},
loadOnStartup = 0)
public class TestWebServlet extends HttpServlet {
    public TestWebServlet() {
        System.out.println("TestWebServlet无参构造方法执行了!");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("请求收到了!");
    }
}
