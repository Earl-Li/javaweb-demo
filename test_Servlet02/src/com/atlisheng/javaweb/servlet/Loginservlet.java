package com.atlisheng.javaweb.servlet;
/**
 * 1.程序员的Servlet类只需要去继承适配器GenericServlet必须重写最常用的service方法，其他方法按需重写，代码很简洁
 *
 * 2.GenericServlet中有两个init方法，即init方法的重载，一个含参，一个无参，Tomcat会调用含参init方法时会传入一个
 *      主方法创建的ServletConfig对象，在含参init方法中调用无参init方法，一般情况下，程序员在Servlet对象中只需要重写无参init方法即可，效果和直接实现Servlet接口效果相同
 *
 * 3.GenericServlet类实现了Servlet、Servletconfig和java.io.Serializable接口，
 *
 *  4.Servlet实现类继承了GenericServlet，Servletconfig的方法Servlet实现类可以通过this去调用，Servletconfig中只有四个方法，且由于GenericServlet实现了Servletconfig接口，方法也调用的是config对应类的方法得到的结果，
 *                  Servlet对象可以直接用this调用，结果都是一样的
 *
 *              // 通过<servlet>标签中子标签<init-param>的初始化参数的param-name获取value
 *              public String getInitParameter(String name);
 *
 *              //获取<servlet>标签中子标签<init-param>的所有初始化参数的param-name
 *              //这个方法实际上是在ServletConfig的另一个实现类StandardWrapper中实现的
 *              //在StandardWrapperFacade(实际的配置信息对象)中引入StandardWrapper对象并调用其getInitParameterNames()获得的集合
 *              public Enumeration<String> getInitParameterNames();
 *
 *              // 获取ServletContext对象
 *              public ServletContext getServletContext();
 *
 *              // 获取ServletConfig所在Servlet类的web.xml文件中的servlet-name
 *              public String getServletName();
 *
*    5.Enumeration<String>接口中总共就这两个方法
 *              public boolean hasMoreElements()
 *              public String nextElement()
 * */
import jakarta.servlet.*;

import java.io.IOException;
import java.util.Enumeration;

public class Loginservlet extends GenericServlet {
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        //获取ServletConfig对象
        ServletConfig config=this.getServletConfig();
        System.out.println(config);//org.apache.catalina.core.StandardWrapperFacade@192a15cb

        //获取xml文件的Servlet-name
        String servletName= config.getServletName();
        System.out.println(servletName);//logservlet
        System.out.println(this.getServletName());//logservlet

        //根据名字获取Servlet对象初始化信息
        System.out.println(config.getInitParameter("url"));//jdbc:mysql://127.0.0.1:3306/bjpowernode
        System.out.println(this.getInitParameter("driver"));//com.mysql.cj.jdbc.Driver

        //一次获取所有Servlet对象初始化信息
        Enumeration<String> initParameterNames=config.getInitParameterNames();
        while(initParameterNames.hasMoreElements()){//Enumeration<String>中的实例方法hasMoreElements()，有就返回true
            String parameterName=initParameterNames.nextElement();//集合实例方法nextElement()获取当前元素
            System.out.println(parameterName+" | "+this.getInitParameter(parameterName));
            /**
             * 结果：
             *          password | Haworthia715
             *          driver | com.mysql.cj.jdbc.Driver
             *          user | root
             *          url | jdbc:mysql://127.0.0.1:3306/bjpowernode
             * */
        }

        //获取ServletContext对象
        ServletContext application=getServletContext();
        System.out.println(application);//org.apache.catalina.core.ApplicationContextFacade@1d2cf30b

        //根据名字获取上下文初始化信息
        String pageSize=application.getInitParameter("pageSize");
        System.out.println(pageSize);//12

        //一次获取所有上下文初始化信息
        Enumeration<String> initParameterNames1=application.getInitParameterNames();
        while(initParameterNames1.hasMoreElements()){
            String parameterName=initParameterNames1.nextElement();
            System.out.println(parameterName+" | "+application.getInitParameter(parameterName));
            /**
             * 结果：
             *      startIndeex | 0
             *       pageSize | 12
             * */
        }

        //获取应用的根路径
        String contextPath = application.getContextPath();
        System.out.println(contextPath);//  /ser02

        //获取文件的绝对路径
        String realPath = application.getRealPath("welcome.html");
        System.out.println(realPath);//C:\Users\Earl\IdeaProjects\javaweb\out\artifacts\test_Servlet02_war_exploded\welcome.html

        //应用域存、取、移除数据
        application.setAttribute("key","123456");
        System.out.println(application.getAttribute("key"));//123456，添加未移除，所以能取出来，说明三个方法全部起作用
        application.removeAttribute("key");
        System.out.println(application.getAttribute("key"));//null,已移除所以是null
        //检验上下文初始化参数是否在应用域中？
        System.out.println(application.getAttribute("pageSize"));//null,不能用名字在应用域中取上下文初始化参数


        application.log("超速了大哥!");
        application.log("未成年出去!",new RuntimeException("那个小孩干啥呢!"));
        /**
         * 两条日志都存在正确的文件中，日志信息分别如下所示
         *          06-Mar-2023 00:30:10.256 信息 [http-nio-8080-exec-5] org.apache.catalina.core.ApplicationContext.log 超速了大哥!
         *          06-Mar-2023 00:30:10.256 严重 [http-nio-8080-exec-5] org.apache.catalina.core.ApplicationContext.log 未成年出去!
         * 	        java.lang.RuntimeException: 那个小孩干啥呢!
         * 	        以下是打印异常信息
         * */

    }

}
