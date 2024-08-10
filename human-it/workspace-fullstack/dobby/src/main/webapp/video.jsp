<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.sql.Connection" %>
<%@ page import = "java.sql.DriverManager" %>
<%@ page import = "java.sql.PreparedStatement" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import = "java.sql.SQLException" %>
<%@ page import = "java.util.HashMap" %>
<%@ page import = "java.util.Map" %>
<%@ page import = "com.dobby.utils.DBManager" %>

<%
    String seq = request.getParameter("seq");

    if (seq == null || seq.isEmpty()) {
        out.println("Invalid video ID.");
        return;
    }

    Map<String, String> video = new HashMap<>();

    try (Connection conn = DBManager.getDBConnection()) {
        // 조회수 증가
        String updateViewsSql = "UPDATE video SET views = views + 1 WHERE seq = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateViewsSql)) {
            pstmt.setString(1, seq);
            pstmt.executeUpdate();
        }

        // 동영상 정보 가져오기
        String selectSql = "SELECT seq, title, description, upload_date, duration, thumbnail_url, video_url, views, likes FROM video WHERE seq = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(selectSql)) {
            pstmt.setString(1, seq);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    video.put("seq", rs.getString("seq"));
                    video.put("title", rs.getString("title"));
                    video.put("description", rs.getString("description"));
                    video.put("upload_date", rs.getString("upload_date"));
                    video.put("duration", rs.getString("duration"));
                    video.put("thumbnail_url", rs.getString("thumbnail_url"));
                    video.put("video_url", rs.getString("video_url"));
                    video.put("views", rs.getString("views"));
                    video.put("likes", rs.getString("likes"));
                } else {
                    out.println("Video not found.");
                    return;
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
<!DOCTYPE html>
<html lang="kr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Video Player</title>
    <link rel="stylesheet" href="./css/video.css">
    <script defer src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <div class="video-container">
        <iframe id="videoPlayer" width="600" height="400" frameborder="0" allowfullscreen></iframe>
    </div>

    <script>
        function getParameterByName(name, url = window.location.href) {
            name = name.replace(/[\[\]]/g, '\\$&');
            const regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)');
            const results = regex.exec(url);
            if (!results) return null;
            if (!results[2]) return '';
            return decodeURIComponent(results[2].replace(/\+/g, ' '));
        }

        const videoUrl = getParameterByName('video');
        if (videoUrl) {
            let embedUrl;
            if (videoUrl.includes('youtu.be')) {
                // YouTube 'youtu.be' 링크를 'embed' 링크로 변환
                const videoId = videoUrl.split('/').pop();
                embedUrl = `https://www.youtube.com/embed/${videoId}`;
            } else if (videoUrl.includes('youtube.com')) {
                // YouTube 'youtube.com/watch?v=' 링크를 'embed' 링크로 변환
                const videoId = new URL(videoUrl).searchParams.get('v');
                embedUrl = `https://www.youtube.com/embed/${videoId}`;
            } else {
                // 일반 동영상 파일
                embedUrl = videoUrl;
                document.getElementById('videoPlayer').setAttribute('src', embedUrl);
                document.getElementById('videoPlayer').setAttribute('type', 'video/mp4');
            }
            if (embedUrl) {
                document.getElementById('videoPlayer').setAttribute('src', embedUrl);
            }
        }
    </script>
</body>
</html>