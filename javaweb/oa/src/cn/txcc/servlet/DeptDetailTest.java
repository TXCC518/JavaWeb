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
public class DeptDetailTest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
}
