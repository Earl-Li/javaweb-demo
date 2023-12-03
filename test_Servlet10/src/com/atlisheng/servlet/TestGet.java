package com.atlisheng.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/test/get")
public class TestGet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //通过request对象接收浏览器发送过来的cookie,包括sessionId，返回Cookie的数组
        Cookie[] cookies=request.getCookies();
        //遍历cookie数组，注意，如果浏览器没有提交任何cookie，这个方法返回的不是一个长度为0的数组，而是返回null，所以遍历之前需先判定是否为null
        if (cookies != null) {
            for (Cookie cookie:cookies) {
                //获取cookie的name
                String name=cookie.getName();
                //获取cookie的value
                String value=cookie.getValue();
                System.out.println(name + " | " + value);
                /**
                 * 测试结果：
                 *      productId | 1234567890
                 *      username | zhangsan
                 *      JSESSIONID | 2BAEA594291EE6197E3B0F87509EDC6E
                 * */
            }
        }

    }
}
