package com.atlisheng.servlet;

import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;

/**
 * 在ReflectAnnotation类中使用反射机制获取这个TestWebServlet类的WebServlet注解中的属性值
 *
 * 测试结果：name，urlPattern，initParams，loadOnStartup属性值都可以取出，代码也很简单
 * */
public class ReflectAnnotation {
    public static void main(String[] args) throws ClassNotFoundException {
        //使用反射机制解析TestWebServlet类上面的WebServlet注解
        //获取类Class对象
        Class<?> testWebServlet=Class.forName("com.atlisheng.servlet.TestWebServlet");//有这个？下面不用强转，没有？下面需要强转
        //获取WebServlet注解对象
        if (testWebServlet.isAnnotationPresent(WebServlet.class)){
            WebServlet webServletAnnotation=testWebServlet.getAnnotation(WebServlet.class);//WebServlet竟然不用强转，之前JavaSE需要强转
            //获取name属性值
            System.out.println(webServletAnnotation.name());//testWS
            //获取urlPatterns属性值
            String[] urlPatterns=webServletAnnotation.urlPatterns();
            for (String urlPattern:urlPatterns) {
                System.out.println(urlPattern);
            }
            /**
             * 执行结果：
             *      /testWebServlet
             *      /testWS
             *      /testwebservlet
             * */
            //获取initParams属性值
            WebInitParam[] initParams=webServletAnnotation.initParams();
            for (WebInitParam initParam:initParams) {
                System.out.println(initParam.name()+"="+initParam.value());
            }
            /**
             * 执行结果：
             *      url=jdbc:mysql://127.0.0.1:3306/bjpowernode
             *      username=root
             *      password=Haworthia0715
             * */
            //获取loadOnStartup属性值
            System.out.println(webServletAnnotation.loadOnStartup());//0
        }

    }
}
