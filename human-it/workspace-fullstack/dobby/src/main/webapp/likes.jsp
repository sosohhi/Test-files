<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.sql.Connection" %>
<%@ page import = "java.sql.DriverManager" %>
<%@ page import = "java.sql.PreparedStatement" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import = "java.sql.SQLException" %>
<%@ page import = "com.dobby.utils.DBManager" %>
<%
    String seq = request.getParameter("seq");

    if (seq == null || seq.isEmpty()) {
        out.println("Invalid video ID.");
        return;
    }

    try (Connection conn = DBManager.getDBConnection()) {
        String updateLikesSql = "UPDATE video SET likes = likes + 1 WHERE seq = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateLikesSql)) {
            pstmt.setString(1, seq);
            pstmt.executeUpdate();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>