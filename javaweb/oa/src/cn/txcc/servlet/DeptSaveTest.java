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
public class DeptSaveTest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
}
