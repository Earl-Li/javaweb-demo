package com.atlisheng.javaweb.servlet;
/**
 * 执行效果：
 *
         * Aservlet对象的无参构造方法执行了（首次访问http://127.0.0.1:8080/lifecycle/a，创建servlet对象，执行init方法，执行一次service方法）
         * Aservlet的init方法执行了
         * Aservlet执行了（第二次访问http://127.0.0.1:8080/lifecycle/a）
         * Aservlet执行了（第三次访问http://127.0.0.1:8080/lifecycle/a）
         * Aservlet执行了
         * Bservlet对象的无参构造方法执行了（首次访问http://127.0.0.1:8080/lifecycle/b，创建servlet对象，执行init方法，执行一次service方法）
         * Bservlet的init方法执行了
         * Bservlet执行了
         * Aservlet执行了（第二次访问http://127.0.0.1:8080/lifecycle/b）
         * Bservlet执行了（第三次访问http://127.0.0.1:8080/lifecycle/b）
         * Bservlet执行了
         * Bservlet执行了
 * */
import jakarta.servlet.*;

import java.io.IOException;

public class Bservlet implements Servlet {
    //无参构造方法验证服务器何时创建servlet对象
    //单实例化没有学，也称伪实例化，韩顺平有讲
    public Bservlet(){
        System.out.println("Bservlet对象的无参构造方法执行了");
    }
    //如果只有有参构造方法，没有无参构造方法程序服务器可以启动，但是访问会发生错误，iDEA其实会在xml文件中报错，
    // 浏览器错误代码500，java.lang.NoSuchMethodException: com.atlisheng.javaweb.servlet.Bservlet.<init>()
    /*public Bservlet(String s){

    }*/

    @Override
    //init方法通常用来完成初始化操作，init被翻译成初始化，通常在对象第一次被创建之后执行一次init方法，init方法执行在创建对象之后，因为init是实例方法
    //如果在xml中设置了服务器启动就创建对象，创建完以后init方法同样会执行，service方法不会执行
    //这里的代码通常只需要执行一次，且在创建完对像后马上执行
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("Bservlet的init方法执行了");
    }

    @Override
    //处理用户请求的核心方法，用户请求一次执行一次，请求一百次执行一百次
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        System.out.println("Bservlet执行了");
    }

    @Override
    //Tomcat服务器在销毁Bservlet对象前会调用一次destory方法，destory方法也只会被调用一次，destory方法调用时，Bservlet对象即将被销毁，还没有销毁
    //destory方法中可以编写销毁前的准备，比如Bservlet对象开启了一些比如流、数据库连接资源，关闭服务器前，这些资源可能需要关闭，可以写在destory中
    //相当于要挂了赶紧写遗嘱，通常在destory方法中关闭资源、保存资源
    public void destroy() {
        System.out.println("Bservlet的destroy方法执行了");
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
