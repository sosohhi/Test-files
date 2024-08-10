package com.dobby.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String memberID = request.getParameter("memberID");
        String memberPW = request.getParameter("memberPW");

        try (Connection conn = DBManager.getDBConnection()) {
            String sql = "SELECT * FROM members WHERE memberID = ? AND memberPW = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberID);
            pstmt.setString(2, memberPW);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("memberID", memberID);
                response.sendRedirect("upload.jsp");
            } else {
                response.sendRedirect("login.jsp?error=1");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
