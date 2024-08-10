<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import ="java.sql.ResultSet" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="com.dobby.utils.DBManager" %>

<%
    request.setCharacterEncoding("UTF-8");

    HttpSession currentsession = request.getSession(false);
    if (session == null || session.getAttribute("memberID") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String title = request.getParameter("title");
    String description = request.getParameter("description");
    String duration = request.getParameter("duration");
    String uploader_id = (String) session.getAttribute("memberID");
    String category = request.getParameter("category");
    String tags = request.getParameter("tags");
    String status = request.getParameter("status");
    String thumbnail_url = request.getParameter("thumbnail_url");
    String video_url = request.getParameter("video_url");
    String views = "0";
    String likes = "0";

    String message = "";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs =null;
    if (title != null && description != null && duration != null && category != null && tags != null && status != null && thumbnail_url != null && video_url != null) {
        try {
        	conn = DBManager.getDBConnection();
            String Insertsql = "INSERT INTO video (title, description, duration, uploader_id, category, tags, status, thumbnail_url, video_url, views, likes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(Insertsql);
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setString(3, duration);
            pstmt.setString(4, uploader_id);
            pstmt.setString(5, category);
            pstmt.setString(6, tags);
            pstmt.setString(7, status);
            pstmt.setString(8, thumbnail_url);
            pstmt.setString(9, video_url);
            pstmt.setString(10, views);
            pstmt.setString(11, likes);
            pstmt.executeUpdate();

            message = "Video uploaded successfully!";
            response.sendRedirect("main.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            message = "Error uploading video.";
        } finally {
        	DBManager.dbClose(conn, pstmt, rs);
        }
    } else {
        message = "Please fill in all fields.";
    }
%>