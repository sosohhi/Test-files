<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import = "com.dobby.utils.DBManager" %>
<%@ page import = "java.sql.Connection" %>
<%@ page import = "java.sql.PreparedStatement" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import = "java.sql.SQLException" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.List" %>

<%
    class Video {
        String seq;
        String title;
        String description;
        String thumbnailUrl;
        String videoUrl;
        String views;
        String likes;
        String logoUrl;
        String uploaderId;
    }

    List<Video> videos = new ArrayList<>();

    try (Connection conn = DBManager.getDBConnection()) {
        String selectSql = "SELECT seq, title, description, thumbnail_url, video_url, views, likes, logo_url, uploader_id FROM video";
        try (PreparedStatement pstmt = conn.prepareStatement(selectSql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Video video = new Video();
                video.seq = rs.getString("seq");
                video.title = rs.getString("title");
                video.description = rs.getString("description");
                video.thumbnailUrl = rs.getString("thumbnail_url");
                video.videoUrl = rs.getString("video_url");
                video.views = rs.getString("views");
                video.likes = rs.getString("likes");
                video.logoUrl = rs.getString("logo_url");
                video.uploaderId = rs.getString("uploader_id");
                videos.add(video);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 1. 탭 타이틀 세팅 -->
    <title>집요정 TV</title>
    <!-- 2. favicon(favorite icon) 세팅 -->
    <link rel="shortcut icon" href="favicon.ico" />
    <link rel="icon" href="favicon.png" />
    <!-- 3. reset.css 세팅(cdn) -->
    <link href="https://cdn.jsdelivr.net/npm/reset-css@5.0.2/reset.min.css" rel="stylesheet">
    <!-- 4. 커스템 css파일 세팅(local) -->
    <link href="./css/main.css" rel="stylesheet">
    <link href="./css/sidebar.css" rel="stylesheet">
    <link href="./css/header.css" rel="stylesheet">
    <!-- 5. 폰트 설정 -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <!-- 6. 아이콘 설정 --> 
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
    <!-- 7. 오픈 그래프 설정(더 많은 속성을 보고 싶으면 https://ogp.me) -->
    <meta property="og:image" content="https://www.youtube.com/img/desktop/yt_1200.png">
    <meta property="fb:app_id" content="87741124305">
    <meta name="description" content="YouTube에서 마음에 드는 동영상과 음악을 감상하고, 직접 만든 콘텐츠를 업로드하여 친구, 가족뿐 아니라 전 세계 사람들과 콘텐츠를 공유할 수 있습니다.">
    <meta name="keywords" content="동영상, 공유, 카메라폰, 동영상폰, 무료, 올리기">
    <meta name="theme-color" content="rgba(255, 255, 255, 0.98)">
    <!-- 8. 자바 스크립트 설정 -->
    <!-- <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script> -->
    
    <!-- gsap js -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/lodash.js/4.17.21/lodash.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.5.1/gsap.min.js" integrity="sha512-IQLehpLoVS4fNzl7IfH8Iowfm5+RiMGtHykgZJl9AWMgqx0AmJ6cRWcB+GaGVtIsnC4voMfm8f2vwtY+6oPjpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.5.1/ScrollToPlugin.min.js" integrity="sha512-nTHzMQK7lwWt8nL4KF6DhwLHluv6dVq/hNnj2PBN0xMl2KaMm1PM02csx57mmToPAodHmPsipoERRNn4pG7f+Q==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/ScrollMagic/2.0.8/ScrollMagic.min.js" integrity="sha512-8E3KZoPoZCD+1dgfqhPbejQBnQfBXe8FuwL4z/c8sTrgeDMFEnoyTlH3obB4/fV+6Sg0a0XF+L/6xS4Xx1fUEg==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <!-- swiper 6.8.4 -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Swiper/6.8.4/swiper-bundle.min.js" integrity="sha512-BABFxitBmYt44N6n1NIJkGOsNaVaCs/GpaJwDktrfkWIBFnMD6p5l9m+Kc/4SLJSJ4mYf+cstX98NYrsG/M9ag==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/Swiper/6.8.4/swiper-bundle.min.css" integrity="sha512-aMup4I6BUl0dG4IBb0/f32270a5XP7H1xplAJ80uVKP6ejYCgZWcBudljdsointfHxn5o302Jbnq1FXsBaMuoQ==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <!-- jQuery 연결 -->
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <!--  아이콘 불러오기 -->
    <script src="https://kit.fontawesome.com/12d13cde63.js" crossorigin="anonymous"></script>
    <script defer src="./js/main.js"></script>
   
</head>
<body>
	<!-- ------HEADER ------ -->
   	<%@ include file="./header.jsp" %>

   <!------MAIN------>
   <div class="YtBody">
    <%@ include file="./sidebar.jsp" %>
    <div class="video_selection">
        <div class="recommendboxes">
            <button class="box">전체</button>
            <button class="box">반려동물</button>
            <button class="box">야구</button>
            <button class="box">뉴스</button>
            <button class="box">요리</button>
        </div>
        <div class="container">
        <%
        	for (Video video : videos) {
        %>
            <div class="video" data-seq="<%= video.seq %>">
                <div class="thumbnail">
                    <img src="<%= video.thumbnailUrl %>" alt="<%= video.title %>">
                </div>
                <div class="details">
                	<div class = details-inner>
	                    <div class="logo">
	                        <img src="<%= video.logoUrl %>" alt="">
	                    </div>
	                    <div class="title">
	                        <h2><%= video.title %></h2>
	                    </div>
	                </div>
	                <a href="#"><%= video.uploaderId %></a>
                    <div class = "views-time">
                    	<span><%= video.views %> views · <%= video.likes %> likes</span>
                   	</div>
            	</div>
           </div>
            <%
             }
             %>
              
        </div>
    </div>
   </div>
</body>
</html>