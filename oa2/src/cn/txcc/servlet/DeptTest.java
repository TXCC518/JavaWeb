package cn.txcc.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.UDtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author May
 * @version 1.0
 */
@WebServlet({"/dept/list", "/dept/detail", "/dept/remove", "/dept/add", "/dept/save", "/dept/query"})
public class DeptTest extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String servletPath = request.getServletPath();
        if("/dept/list".equals(servletPath)){
            doList(request, response);
        }else if("/dept/detail".equals(servletPath)){
            doDetail(request, response);
        }else if("/dept/remove".equals(servletPath)){
            doRemove(request, response);
        }else if("/dept/add".equals(servletPath)){
            doAdd(request, response);
        }else if("/dept/save".equals(servletPath)){
            doSave(request, response);
        }else if("/dept/query".equals(servletPath)){
            doQuery(request, response);
        }
    }

    private void doQuery(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        String deptno = request.getParameter("deptno");
        String dname = request.getParameter("dname");
        String loc = request.getParameter("loc");
        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;
        try {
            conn = UDtils.getConnection();
            String sql = "update dept set dname=?, loc=? where deptno=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, dname);
            ps.setString(2, loc);
            ps.setString(3, deptno);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            UDtils.close(conn, ps, null);
        }
        if(count == 1){
            //request.getRequestDispatcher("/dept/list").forward(request, response);
            response.sendRedirect("/dept/list");
        }else{
            //request.getRequestDispatcher("/error.html").forward(request, response);
            response.sendRedirect("/error.html");
        }
    }

    private void doSave(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        String contentPath = request.getContextPath();
        String deptno = request.getParameter("deptno");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String dname = "";
        String loc = "";
        try {
            conn = UDtils.getConnection();
            String sql = "select * from dept where deptno=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, deptno);
            rs = ps.executeQuery();
            while(rs.next())
            {
                dname = rs.getString("dname");
                loc = rs.getString("loc");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            UDtils.close(conn, ps, rs);
        }
        out.print("        <!DOCTYPE html>");
        out.print("<html lang='en'>");
        out.print("<head>");
        out.print("    <meta charset='UTF-8'>");
        out.print("    <title>修改部门</title>");
        out.print("</head>");
        out.print("<body>");
        out.print("<form action='"+contentPath+"/dept/query' method='post'>");
        out.print("                部门编号：<input type='text' name='deptno' readonly value="+deptno+"><br>");
        out.print("                部门名称：<input type='text' name='dname' value="+dname+"><br>");
        out.print("                部门地址：<input type='text' name='loc' value="+loc+"><br>");
        out.print("  <input type='submit' value='修改'>");
        out.print("</form>");
        out.print("</body>");
        out.print("</html>");
    }

    private void doAdd(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("html/text;charset=utf-8");
        String deptno = request.getParameter("deptno");
        String dname = request.getParameter("dname");
        String loc = request.getParameter("loc");
        Connection conn = null;
        PreparedStatement psmt = null;
        int count = 0;
        try {
            conn = UDtils.getConnection();
            String sql = "insert into dept(deptno, dname, loc) values(?, ?, ?)";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, deptno);
            psmt.setString(2, dname);
            psmt.setString(3, loc);
            count = psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            UDtils.close(conn, psmt, null);
        }
        if(count == 1){
            request.getRequestDispatcher("/dept/list").forward(request, response);
        }else {
            request.getRequestDispatcher("/error.html").forward(request, response);
        }
    }

    private void doRemove(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String deptno = request.getParameter("deptno");
        PrintWriter out = response.getWriter();
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = UDtils.getConnection();
            String sql = "delete from dept where deptno=?";
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, deptno);
            int res = pstm.executeUpdate();
            if(res == 1){
                request.getRequestDispatcher("/dept/list").forward(request, response);
            }else{
                request.getRequestDispatcher("/error.html").forward(request, response);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            UDtils.close(conn, pstm, null);
        }
    }

    private void doDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        String deptno = request.getParameter("deptno");
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            conn = UDtils.getConnection();
            String sql = "select dname, loc from dept where deptno=?";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, deptno);
            rs = psmt.executeQuery();
            while(rs.next())
            {
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");
                out.println("<!DOCTYPE html>");
                out.println("<html lang='en'>");
                out.println("<head>");
                out.println("    <meta charset='UTF-8'>");
                out.println("    <title>部门详情</title>");
                out.println("</head>");
                out.println("<body>");
                out.println(" 部门编号："+deptno+"<br>");
                out.println(" 部门名称："+dname+"<br>");
                out.println(" 部门地址："+loc+"<br>");
                out.println("</body>");
                out.println("</html>");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            UDtils.close(conn, psmt, rs);
        }
    }

    private void doList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        String path = request.getContextPath();
        out.print("<!DOCTYPE html>");
        out.print("<html lang='en'>");
        out.print("<head>");
        out.print("    <meta charset='UTF-8'>");
        out.print("    <title>部门信息</title>");
        out.print("    <style>");
        out.print("                h1 {");
        out.print("            text-align: center;");
        out.print("        }");
        out.print("        table, th, td {");
        out.print("            border: 1px solid #000;");
        out.print("        }");
        out.print("        th, td{");
        out.print("            width: 200px;");
        out.print("            height: 50px;");
        out.print("        }");
        out.print("        table {");
        out.print("            text-align: center;");
        out.print("            margin: 50px auto;");
        out.print("            border-collapse: collapse;");
        out.print("        }");
        out.print("    </style>");
        out.print("</head>");
        out.print("<script type='text/javascript'>");
        out.print("        function del(deptno) {");
        out.print("              if(confirm('您确定要删除这条数据吗？')) {");
        out.print("                    document.location.href=`" + path + "/dept/remove?deptno=${deptno}`");
        out.print("              }");
        out.print("        }");
        out.print("</script>");
        out.print("<body>");
        out.print("  <h1>部门信息表</h1>");
        out.print("  <hr>");
        out.print("  <table>");
        out.print("      <tr>");
        out.print("          <th>序号</th>");
        out.print("          <th>部门编号</th>");
        out.print("          <th>部门名称</th>");
        out.print("          <th>部门地址</th>");
        out.print("          <th>操作</th>");
        out.print("      </tr>");
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = UDtils.getConnection();
            String sql = "select * from dept";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            int i = 1;
            while(rs.next())
            {
                String deptno = rs.getString("deptno");
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");
                out.print("            <tr>");
                out.print("      <td>"+(i++)+"</td>");
                out.print("      <td>"+deptno+"</td>");
                out.print("      <td>"+dname+"</td>");
                out.print("      <td>"+loc+"</td>");
                out.print("      <th>");
                out.print("          <a href='javascript:void(0)' onclick='del("+deptno+")'>删除</a>");
                out.print("          <a href='"+path+"/dept/save?deptno="+deptno+"'>修改</a>");
                out.print("          <a href='"+path+"/dept/detail?deptno="+deptno+"'>详情</a>");
                out.print("      </th>");
                out.print("  </tr>");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            UDtils.close(conn, pstmt, rs);
        }
        out.print("          </table>");
        out.print("<hr>");
        out.print("<a href='"+path+"/add.html'>新增部门</a>");
        out.print("</body>");
        out.print("</html>");
    }
}
