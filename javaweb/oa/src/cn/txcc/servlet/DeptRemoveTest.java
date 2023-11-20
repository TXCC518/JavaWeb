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
public class DeptRemoveTest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
}
