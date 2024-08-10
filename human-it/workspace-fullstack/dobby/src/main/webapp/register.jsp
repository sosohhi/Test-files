<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="com.dobby.utils.DBManager" %>

<%
    String message = "";
    if (request.getMethod().equalsIgnoreCase("POST")) {
        String memberID = request.getParameter("memberID");
        String memberPW = request.getParameter("memberPW");
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBManager.getDBConnection();

            // Check if memberID already exists
            String checkSql = "SELECT COUNT(*) FROM members WHERE memberID = ?";
            pstmt = conn.prepareStatement(checkSql);
            pstmt.setString(1, memberID);
            rs = pstmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            rs.close();
            pstmt.close();

            if (count > 0) {
                message = "Username already exists. Please choose another.";
            } else {
                String sql = "INSERT INTO members (memberID, memberPW, name, gender, email) VALUES (?, ?, ?, ?, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, memberID);
                pstmt.setString(2, memberPW);
                pstmt.setString(3, name);
                pstmt.setString(4, gender);
                pstmt.setString(5, email);
                pstmt.executeUpdate();

                message = "Registration successful. Please login.";
                response.sendRedirect("login.jsp");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            message = "Error occurred during registration: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            message = "Error occurred during registration: " + e.getMessage();
        } finally {
            DBManager.dbClose(conn, pstmt, rs);
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <link rel="stylesheet" href="css/member.css">
</head>
<body>
    <div class="container">
        <div class="register-box">
            <h2>Register</h2>
            <form method="post" action="register.jsp">
                <div class="textbox">
                    <input type="text" placeholder="Username" name="memberID" required>
                </div>
                <div class="textbox">
                    <input type="password" placeholder="Password" name="memberPW" required>
                </div>
                <div class="textbox">
                    <input type="text" placeholder="Name" name="name" required>
                </div>
                <div class="textbox gender">
                    <label>Gender:</label>
                    <input type="radio" id="male" name="gender" value="남" required>
                    <label for="male">남</label>
                    <input type="radio" id="female" name="gender" value="여" required>
                    <label for="female">여</label>
                </div>
                <div class="textbox">
                    <input type="email" placeholder="Email" name="email" required>
                </div>
                <button type="submit" class="btn">Register</button>
            </form>
            <p><%= message %></p>
            <p>Already have an account? <a href="login.jsp">Login here</a></p>
        </div>
    </div>
</body>
</html>
