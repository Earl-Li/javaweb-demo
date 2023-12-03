package com.atlisheng.servlet;

import com.atlisheng.servlet.bean.Dept;
import com.atlisheng.servlet.jdbcutil.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet({"/dept/list","/dept/detail","/dept/modify","/dept/delete","/dept/save","/dept/logout"})
public class DeptServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //如果已经登陆过，则可以执行其他操作
        //获取session，这个session最好使用不新建的方式
        HttpSession session=request.getSession(false);
        //session必须有，且session中被存入过登录状态，否则就重定向到登录页面
        if(session!=null&&session.getAttribute("user")!=null) {
            if ("/dept/list".equals(request.getServletPath())) {
                doList(request, response);
            } else if ("/dept/detail".equals(request.getServletPath())) {
                doDetail(request, response);
            } else if ("/dept/modify".equals(request.getServletPath())) {
                doModify(request, response);
            } else if ("/dept/delete".equals(request.getServletPath())) {
                doDel(request, response);
            } else if ("/dept/save".equals(request.getServletPath())) {
                doSave(request, response);
            } else if ("/dept/logout".equals(request.getServletPath())) {
                doLogOut(request, response);
            }
        }else{
            //重定向到登录页面，这里最好写根，即欢迎页面，因为欢迎页面一般自动定向登陆页面。避免出现改业务，如添加十天免登录要改一堆的问题，
            response.sendRedirect(request.getContextPath());
        }
    }

    private void doLogOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session=request.getSession(false);
        if (session!=null){
            //手动销毁session对象
            session.invalidate();
            //手动删除cookie对象

            /**
             * //方式一
            //获取cookie并设置与原cookie相同的关联路径
            Cookie[] cookies=request.getCookies();
            //注意，这里设置为有效时间为0的cookie不是原cookie，而是数组中的cookie，不过可以把数组中的cookie关联路径设置为原路径相同，效果相同
            if (cookies!=null){
                for (Cookie cookie:cookies) {
                    //先把满足条件的cookie关联路径设置为站点
                    if ("username".equals(cookie.getName())||"password".equals(cookie.getName())){
                        //cookie的销毁是根据名字和关联路径确定的，只要名字和路径相同，cookie对象不同，设置其中一个cookie对象的有效时间为0，所有的同名同关联路径cookie全部失效
                        cookie.setPath(request.getContextPath());
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                }
            }*/

            //方式二
            //设置同名同关联路径cookie，value随意，并设置有效时间为0，发给浏览器,经测试，没问题
            Cookie cookie1=new Cookie("usename","");
            Cookie cookie2=new Cookie("password","");
            cookie1.setMaxAge(0);
            cookie2.setMaxAge(0);
            cookie1.setPath(request.getContextPath());
            cookie2.setPath(request.getContextPath());
            response.addCookie(cookie1);
            response.addCookie(cookie2);

            //并重定向到登录界面
            response.sendRedirect(request.getContextPath());
        }
    }

    private void doSave(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String deptno=request.getParameter("deptno");
        String dname=request.getParameter("dname");
        String loc=request.getParameter("loc");
        System.out.println(deptno+""+dname);
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int count=0;
        try {
            conn= DBUtil.getConnection();
            String insertSQl="insert into t_Dept(deptno, dname, loc) values(?, ?, ?)";
            pstmt=conn.prepareStatement(insertSQl);
            pstmt.setString(1,deptno);
            pstmt.setString(2,dname);
            pstmt.setString(3,loc);
            count=pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtil.closeAll(rs,pstmt,conn);
        }
        if (count==1){
            response.sendRedirect(request.getContextPath()+"/dept/list");
        }else{
            response.sendRedirect(request.getContextPath()+"/error.jsp");
        }
    }

    private void doDel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String deptno=request.getParameter("deptno");
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int count=0;
        try {
            conn= DBUtil.getConnection();
            String insertSQl="delete from t_Dept where deptno=?";
            pstmt=conn.prepareStatement(insertSQl);
            pstmt.setString(1,deptno);
            count=pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtil.closeAll(rs,pstmt,conn);
        }
        if (count==1){
            response.sendRedirect(request.getContextPath()+"/dept/list");
        }else{
            response.sendRedirect(request.getContextPath()+"/error.jsp");
        }
    }

    private void doModify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String deptno=request.getParameter("deptno");
        String dname=request.getParameter("dname");
        String loc=request.getParameter("loc");
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int count=0;
        try {
            conn= DBUtil.getConnection();
            String insertSQl="update t_Dept set dname=?,loc=? where deptno=?";
            pstmt=conn.prepareStatement(insertSQl);
            pstmt.setString(1,dname);
            pstmt.setString(2,loc);
            pstmt.setString(3,deptno);
            count=pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtil.closeAll(rs,pstmt,conn);
        }
        if (count==1){
            response.sendRedirect(request.getContextPath()+"/dept/list");
        }else{
            response.sendRedirect(request.getContextPath()+"/error.jsp");
        }
    }

    private void doDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String deptno=request.getParameter("deptno");
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        Dept dept=new Dept();
        try {
            conn= DBUtil.getConnection();
            String selectSQl="select deptno,dname,loc from t_Dept where deptno=?";
            pstmt=conn.prepareStatement(selectSQl);
            pstmt.setString(1,deptno);
            rs= pstmt.executeQuery();
            while (rs.next()) {
                dept.setDeptno(rs.getString("deptno"));
                dept.setDname(rs.getString("dname"));
                dept.setLoc(rs.getString("loc"));
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtil.closeAll(rs,pstmt,conn);
        }
        request.setAttribute("dept",dept);
        request.getRequestDispatcher("/detail.jsp").forward(request,response);
    }

    private void doList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //准备一个List容器，专门用来存储查询到的部门
        List<Dept> depts=new ArrayList<>();
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn= DBUtil.getConnection();
            String selectSQl="select deptno,dname,loc from t_Dept";
            pstmt=conn.prepareStatement(selectSQl);
            rs= pstmt.executeQuery();
            while (rs.next()) {
                //取出零散数据
                String deptno=rs.getString("deptno");
                String dname=rs.getString("dname");
                String loc=rs.getString("loc");
                //将零散数据封装成java对象并存入List容器
                Dept dept=new Dept();
                dept.setDeptno(deptno);
                dept.setDname(dname);
                dept.setLoc(loc);
                depts.add(dept);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtil.closeAll(rs,pstmt,conn);
        }
        //将List集合放入请求域
        request.setAttribute("deptList",depts);
        //把请求转发给list.jsp，一定注意，转发不要带项目名
        request.getRequestDispatcher("/list.jsp").forward(request,response);
    }
}
