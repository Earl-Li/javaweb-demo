package com.atlisheng.servlet.bean;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionBindingListener;

import java.util.Objects;

/**
 * 普通javabean，实现了HttpSessionBindingListener接口，可以被监听放入session和移出session
 * */
public class User implements HttpSessionBindingListener {
    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        //用户登录了
        //使用ServletContext是因为所有用户都可以获取这个数据
        ServletContext application=event.getSession().getServletContext();
        //在别的Servlet类中存入
        Object onlineCount=application.getAttribute("onlineCount");
        //首位用户放入session对应的onlineCount此时为null，需要判定为null，然后改成1
        if (onlineCount == null) {
            application.setAttribute("onlineCount",1);
        }else{
            //不是先取出，操作以后存入并覆盖
            int count=((Integer)onlineCount);
            count++;
            application.setAttribute("onlineCount",count);
        }
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        //用户退出了
        //user对象从session中移除
        //取出然后--，再放入
        ServletContext application=event.getSession().getServletContext();
        Object onlineCount=application.getAttribute("onlineCount");
        int count=((Integer)onlineCount);
        count--;
        application.setAttribute("onlineCount",count);
    }

    private String username;
    private String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "User{" +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
