package com.atlisheng.servlet;

/**
 * 采用模板方法设计模式避免类爆炸
 *
 * 主要是修改路径，包括方法内部跳转的路径，路径不要修改错了，还有一些写死的路径修改，方法可以原封不动的复制粘贴
 *
 * 项目以优化
 * */
import com.atlisheng.servlet.jdbcutil.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(urlPatterns ={"/dept/list","/dept/add","/dept/delete","/dept/detail","/dept/modify","/dept/save"} )
public class DeptServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //模板方法
        String servletPath=request.getServletPath();
        if ("/dept/list".equals(servletPath)){
            doList(request,response);
        }else if ("/dept/add".equals(servletPath)){
            doAdd(request,response);
        }else if ("/dept/delete".equals(servletPath)){
            doDel(request,response);
        }else if ("/dept/detail".equals(servletPath)){
            doDetail(request,response);
        }else if ("/dept/modify".equals(servletPath)){
            doModify(request,response);
        }else if ("/dept/save".equals(servletPath)){
            doSave(request,response);
        }else {
            response.sendRedirect(request.getContextPath()+"/error.html");
        }
    }

    private void doList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //有了这个不要html的格式直接输出body也行
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        //不添加text/html声明，又不写完整格式，打到浏览器上显示乱码，反正就是会出问题
        out.print("<!DOCTYPE html>");
        out.print("<html>");
        out.print("<head>");
        out.print("<meta charset='utf-8'>");
        out.print("<title>部门列表</title>");
        out.print("</head>");
        out.print("<body>");
        out.print("<h1 align='center'>部门列表</h1>	<hr>	");
        out.print("<table border='2px' align='center' width='50%'>		");
        out.print("	<thead>			");
        out.print("		<tr>				");
        out.print("			<th>序号</th>				");
        out.print("			<th>部门编号</th>				");
        out.print("			<th>部门名称</th>				");
        out.print("			<th>操作</th>			");
        out.print("		</tr>		");
        out.print("	</thead>		");
        out.print("	<tbody align='center'>");
        //链接数据库查询数据
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            //获取数据库链接对象
            conn= DBUtil.getConnection();
            //获取预编译的数据库操作对象
            String selectSQl="select deptno,dname,loc from t_Dept";
            pstmt=conn.prepareStatement(selectSQl);
            //处理查询结果集
            rs= pstmt.executeQuery();
            int i=1;
            while (rs.next()) {
                out.print("			<tr>");
                out.print("				<td>"+i+"</td>");
                out.print("				<td>"+rs.getString("deptno")+"</td>");
                out.print("				<td>"+rs.getString("dname")+"</td>");
                out.print("				<td>");
                out.print("					<a href='javascript:void()' onclick='cof("+rs.getString("deptno")+")' />删除</a>");
                out.print("					<a href='"+request.getContextPath()+"/dept/detail?deptno="+rs.getString("deptno")+"'>修改</a>");
                out.print("					<a href='"+request.getContextPath()+"/dept/detail?deptno="+rs.getString("deptno")+"'>详情</a>");
                out.print("				</td>");
                out.print("			</tr>");
                i++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            out.print("            </tbody>	");
            out.print("            </table>");
            out.print("            <script type='text/javascript'>");
            out.print("                    function cof(dno) {");
            out.print("                        var ok=window.confirm('亲，删除不可恢复哦!');");
            out.print("                        if (ok) {");
            out.print("                            window.location.href='/ser08/dept/delete?deptno='+dno");
            out.print("                        }");
            out.print("                    }");
            out.print("            </script>	");
            out.print("            <hr >	");
            out.print("            <a href='/ser08/dept/add'>新增部门</a>	<a href='/ser08/index.html'>退出");
            out.print("        </body>");
            out.print("</html>");
            DBUtil.closeAll(rs,pstmt,conn);
        }
    }

    private void doAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        out.print("<!DOCTYPE html>");
        out.print("<html>");
        out.print("<head>");
        out.print("        <meta charset='utf-8' />");
        out.print("         <title>新增部门</title>");
        out.print("</head>");
        out.print("    <body>");
        out.print("        <form action='/ser08/dept/save' >");
        out.print("                部门编号<input type='text' name='deptno'/><br>");
        out.print("                部门名称<input type='text' name='dname'/><br>");
        out.print("                部门位置<input type='text' name='loc'/><br>");
        out.print("        	<input type='submit' value='点击添加'/>");
        out.print("        </form>");
        out.print("    <hr>");
        out.print("    <a href='/ser08/dept/list'>返回</a>");
        out.print("    </body>");
        out.print("</html>");
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
            response.sendRedirect(request.getContextPath()+"/error.html");
        }
    }

    private void doDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String deptno=request.getParameter("deptno");
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        out.print("<!DOCTYPE html>");
        out.print("<html>");
        out.print("<head>");
        out.print("<meta charset='utf-8' />");
        out.print("<title>部门详情</title>");
        out.print("</head>");
        out.print("<body>");
        out.print("       部门详情");
        out.print("       <hr>");
        out.print("<form action='/ser08/dept/modify' >");

        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            //获取数据库链接对象
            conn= DBUtil.getConnection();
            //获取预编译的数据库操作对象
            String selectSQl="select deptno,dname,loc from t_Dept where deptno=?";
            pstmt=conn.prepareStatement(selectSQl);
            pstmt.setString(1,deptno);
            //处理查询结果集
            rs= pstmt.executeQuery();
            while (rs.next()) {
                out.print("    部门编号<input type='text' name='deptno' value='"+rs.getString("deptno")+"' readonly />");
                out.print("       <br>");
                out.print("    部门名称<input type='text' name='dname'  value='"+rs.getString("dname")+"'/>");
                out.print("       <br>");
                out.print("    部门位置<input type='text' name='loc' value='"+rs.getString("loc")+"'/>");
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            out.print("       <br>");
            out.print("<input type='submit' value='修改'/>");
            out.print("</form>");
            out.print("       <hr>");
            out.print("<a href='"+request.getContextPath()+"/dept/list'>返回</a>");
            out.print("</body>");
            out.print("</html>");
            DBUtil.closeAll(rs,pstmt,conn);
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
            response.sendRedirect(request.getContextPath()+"/error.html");
        }
    }

    private void doSave(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String deptno=request.getParameter("deptno");
        String dname=request.getParameter("dname");
        String loc=request.getParameter("loc");
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
            response.sendRedirect(request.getContextPath()+"/error.html");
        }
    }
}
