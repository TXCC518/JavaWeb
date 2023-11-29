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
@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String username = "";
        String password = "";
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if("username".equals(cookie.getName())){
                    username = cookie.getValue();
                }else if("password".equals(cookie.getName())){
                    password = cookie.getValue();
                }
            }
        }
        // 判断当前cookie中保存的账户名和密码是否正确，有没有发生修改
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
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                response.sendRedirect(request.getContextPath() + "/dept/list");
            }else{
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
