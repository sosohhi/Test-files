<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="com.dobby.utils.DBManager" %>

<%
    String message = "";
    if (request.getMethod().equalsIgnoreCase("POST")) {
        String memberID = request.getParameter("memberID");
        String memberPW = request.getParameter("memberPW");
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBManager.getDBConnection();
            String sql = "SELECT * FROM members WHERE memberID = ? AND memberPW = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberID);
            pstmt.setString(2, memberPW);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                HttpSession currentsession = request.getSession();
                session.setAttribute("memberID", memberID);
                response.sendRedirect("main.jsp");
                return;
            } else {
                message = "Invalid username or password";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            message = "Error occurred during login: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            message = "Error occurred during login";
        } finally {
            DBManager.dbClose(conn, pstmt, rs);
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="./css/member.css">
</head>
<body>
    <div class="container">
        <div class="login-box">
            <h2>Login</h2>
            <form method="post" action="login.jsp">
                <div class="textbox">
                    <input type="text" placeholder="Username" name="memberID" required>
                </div>
                <div class="textbox">
                    <input type="password" placeholder="Password" name="memberPW" required>
                </div>
                <button type="submit" class="btn">Login</button>
            </form>
            <p><%= message %></p>
            <p>Don't have an account? <a href="register.jsp">Register here</a></p>
        </div>
    </div>
</body>
</html>
