package cn.txcc.servlet;

import jakarta.servlet.ServletException;
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
public class DeptListTest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
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
        out.print("");
        out.print("        table {");
        out.print("            text-align: center;");
        out.print("            margin: 50px auto;");
        out.print("            width: 500px;");
        out.print("            height: 200px;");
        out.print("            border-collapse: collapse;");
        out.print("        }");
        out.print("    </style>");
        out.print("</head>");
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
                out.print("          <a href='#'>删除</a>");
                out.print("          <a href='#'>修改</a>");
                out.print("          <a href='#'>详情</a>");
                out.print("      </th>");
                out.print("  </tr>");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            UDtils.close(conn, pstmt, rs);
        }
        out.print("          </table>");
        out.print("</body>");
        out.print("</html>");
    }
}
