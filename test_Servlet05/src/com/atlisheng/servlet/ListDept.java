package com.atlisheng.servlet;
/**
 * 丑陋版的OA系统，web文件下为网页原型，使用纯Servlet对象实现单表CURD后静态页面几乎没用，只是用来参考，唯二两个页面一个是欢迎页面index.html，另一个是出错返回页面error.html
 *
 * 没有实现前后端分离，后端既要写前端，又要写后端
 *
 * dept.sql是初始表的创建sql语句
 *
 * 实现进入欢迎页面——进入所有列表列表页面——在列表页面选择增删查改——且单独页面都可以返回列表页面；最后列表页面可以退出系统
 *
 * 删除一定要客户确认，有可能是误点，这里没有实现，要反省一下，改进：删除注册事件句柄，调用JS函数确认然后再执行
 *               JS代码中的函数的if前面一句不加分号会报错，我特么服了，不好找错误，不是浏览器发过去的情况下代码是可以执行的，以后JS代码该加分号加分号
 *
 * 优化：转发只用于数据绑定请求域，其余的全用重定向避免刷新问题，保存、修改、删除都有转发，全部改重重定向
 *
 * */
import com.atlisheng.servlet.jdbcutil.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class ListDept extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                out.print("					<a href='"+request.getContextPath()+"/detailDept?deptno="+rs.getString("deptno")+"'>修改</a>");
                out.print("					<a href='"+request.getContextPath()+"/detailDept?deptno="+rs.getString("deptno")+"'>详情</a>");
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

            /**JS代码中的函数的if前面一句不加分号会报错，我特么服了，不好找错误，不是浏览器发过去的情况下代码是可以执行的，以后JS代码该加分号加分号*/

            out.print("                        var ok=window.confirm('亲，删除不可恢复哦!');");
            out.print("                        if (ok) {");
            out.print("                            window.location.href='/ser05/deleteDept?deptno='+dno");
            out.print("                        }");
            out.print("                    }");
            out.print("            </script>	");
            out.print("            <hr >	");
            out.print("            <a href='/ser05/addDept'>新增部门</a>	<a href='/ser05/index.html'>退出");
            out.print("        </body>");
            out.print("</html>");
            DBUtil.closeAll(rs,pstmt,conn);
        }
    }
}
