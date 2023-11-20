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
import java.sql.SQLException;

/**
 * @author May
 * @version 1.0
 */
public class DeptAddTest extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
}
