package cn.txcc.servlet;

import cn.txcc.utils.UDtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author May
 * @version 1.0
 */
@WebServlet({"/user/login", "/user/logout"})
public class UserServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String servletPath = request.getServletPath();
        if("/user/login".equals(servletPath)){
            doLogin(request, response);
        }else if("/user/logout".equals(servletPath)){
            doLogout(request, response);
        }
    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = UDtils.getConnection();
            String sql = "select * from t_user where username=? and password=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if(rs.next()){
                // 登陆成功，保存session对象，跳转到部门列表页面
                String flag = request.getParameter("flag");
                System.out.println(flag);
                if("1".equals(flag)) {
                    Cookie cookie1 = new Cookie("username", username);
                    Cookie cookie2 = new Cookie("password", password);
                    cookie1.setMaxAge(60 * 60 * 24 * 10);
                    cookie2.setMaxAge(60 * 60 * 24 * 10);
                    cookie1.setPath(request.getContextPath());
                    cookie2.setPath(request.getContextPath());
                    response.addCookie(cookie1);
                    response.addCookie(cookie2);
                }
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                response.sendRedirect(request.getContextPath() + "/dept/list");
            }else{
                // 登陆失败，返回登陆页面
                response.sendRedirect(request.getContextPath() + "/user_error.jsp");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            UDtils.close(conn, ps, rs);
        }
    }
}
