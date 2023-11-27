package cn.txcc.servlet;

import cn.txcc.bean.Dept;
import cn.txcc.utils.UDtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author May
 * @version 1.0
 */
@WebServlet({"/dept/list", "/dept/remove", "/dept/add", "/dept/detail", "/dept/query"})
public class DeptServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String servletPath = request.getServletPath();
        if("/dept/list".equals(servletPath)){
            doList(request, response);
        }else if("/dept/remove".equals(servletPath)){
            doRemove(request, response);
        }else if("/dept/add".equals(servletPath)){
            doAdd(request, response);
        }else if("/dept/detail".equals(servletPath)){
            doDetail(request, response);
        }else if("/dept/query".equals(servletPath)){
            doQuery(request, response);
        }
    }

    private void doQuery(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String dname = request.getParameter("dname");
        String loc = request.getParameter("loc");
        String deptno = request.getParameter("deptno");
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
        } finally{
            UDtils.close(conn, ps, null);
        }
        if(count == 1){
            response.sendRedirect(request.getContextPath() + "/dept/list");
        }else{
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }

    private void doDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String deptno = request.getParameter("deptno");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = UDtils.getConnection();
            String sql = "select * from dept where deptno=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, deptno);
            rs = ps.executeQuery();
            if(rs.next()){
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");
                Dept dept = new Dept(deptno, dname, loc);
                request.setAttribute("dept", dept);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            UDtils.close(conn, ps, rs);
        }
        String path = request.getParameter("path");
        System.out.println(path);
        request.getRequestDispatcher("/"+path+".jsp").forward(request, response);
    }

    private void doAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String deptno = request.getParameter("deptno");
        String dname = request.getParameter("dname");
        String loc = request.getParameter("loc");
        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;
        try {
            conn = UDtils.getConnection();
            String sql = "insert into dept(deptno, dname, loc) values(?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, deptno);
            ps.setString(2, dname);
            ps.setString(3, loc);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            UDtils.close(conn, ps, null);
        }
        if(count == 1){
            response.sendRedirect(request.getContextPath()+"/dept/list");
        }else {
            response.sendRedirect(request.getContextPath()+"/error.jsp");
        }
    }

    private void doRemove(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement ps = null;
        String deptno = request.getParameter("deptno");
        int count = 0;
        try {
            conn = UDtils.getConnection();
            String sql = "delete from dept where deptno=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, deptno);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            UDtils.close(conn, ps, null);
        }
        if(count == 1){
            response.sendRedirect(request.getContextPath() + "/dept/list");
        }else{
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }

    private void doList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Dept> depts = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = UDtils.getConnection();
            String sql = "select * from dept";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                String deptno = rs.getString("deptno");
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");
                Dept dept = new Dept(deptno, dname, loc);
                depts.add(dept);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            UDtils.close(conn, ps, rs);
        }
        request.setAttribute("depts", depts);
        // 请求转发
        request.getRequestDispatcher("/list.jsp").forward(request, response);
    }
}
