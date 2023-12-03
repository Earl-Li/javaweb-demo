package com.atlisheng.servlet.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/*已经在web.xml中对LoginCheckFilter进行了配置，还可以调整过滤器的顺序，没必要使用注解*/
public class LoginCheckFilter implements Filter {
    //有default关键字修饰的方法不是必须要重写，没有default修饰的必须重写，如doFilter
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        //登录检查代码
        /**
         * 注意有几种情况不能拦截；
         *      已经登录不能拦截
         *      用户去登录不能拦截index.jsp，与登录相关的验证账号密码不能拦截
         *      验证用户是否点了十天不用登录也不能拦截/welcome
         * */
        HttpServletRequest request=(HttpServletRequest)req ;
        HttpServletResponse response=(HttpServletResponse)resp;
        HttpSession session=request.getSession(false);
        String servletPath=request.getServletPath();
        if("/index.jsp".equals(servletPath)||"/user/login".equals(servletPath)||
                "/welcome".equals(servletPath)||
                session!=null&&session.getAttribute("user")!=null) {
            //登录就继续往下走,过滤器然后对应的Servlet对象
            chain.doFilter(request,response);
        }else{
            response.sendRedirect(request.getContextPath()+"/index.jsp");
        }
    }
}
