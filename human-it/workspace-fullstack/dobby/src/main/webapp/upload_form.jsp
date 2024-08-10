<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="com.dobby.utils.DBManager" %>
<%
    HttpSession currentsession2 = request.getSession(false);
    if (session == null || session.getAttribute("memberID") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%> 
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Upload Video</title>
    <link rel="shortcut icon" href="favicon.ico" />
    <link rel="icon" href="favicon.png" />
    <link href="https://cdn.jsdelivr.net/npm/reset-css@5.0.2/reset.min.css" rel="stylesheet">
    <link href="./css/upload.css" rel="stylesheet">
    <link href="./css/header.css" rel="stylesheet">
    <link href="./css/sidebar.css" rel="stylesheet">
    <!-- 폰트 설정 -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
    <!-- 아이콘 설정 --> 
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght@20..700&display=swap" />
    <!-- 오픈 그래프 설정 -->
    <meta property="og:image" content="https://www.youtube.com/img/desktop/yt_1200.png">
    <meta property="fb:app_id" content="87741124305">
    <meta name="description" content="YouTube에서 마음에 드는 동영상과 음악을 감상하고, 직접 만든 콘텐츠를 업로드하여 친구, 가족뿐 아니라 전 세계 사람들과 콘텐츠를 공유할 수 있습니다.">
    <meta name="keywords" content="동영상, 공유, 카메라폰, 동영상폰, 무료, 올리기">
    <meta name="theme-color" content="rgba(255, 255, 255, 0.98)">
    <!-- 자바 스크립트 설정 -->
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <!-- gsap js -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.5.1/gsap.min.js" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.5.1/ScrollToPlugin.min.js" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/ScrollMagic/2.0.8/ScrollMagic.min.js" crossorigin="anonymous"></script>
    <!-- swiper 6.8.4 -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Swiper/6.8.4/swiper-bundle.min.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/Swiper/6.8.4/swiper-bundle.min.css" crossorigin="anonymous" />
    <!-- 아이콘 불러오기 -->
    <script src="https://kit.fontawesome.com/12d13cde63.js" crossorigin="anonymous"></script>
    <script defer src="./js/upload.js"></script>
</head>
<body>
    <div class="header">
        <%@ include file="header.jsp" %>
    </div>
    <%@ include file="sidebar.jsp" %>
    <div class="upload-form">
        <div class="upload-header">
            <h1>동영상 업로드</h1>
        </div>
        <form action="upload.jsp" method="post" id="form1">
            <div class="upload-body">
	            <div class="textbox">
	            	<h4>제목</h4>
	                <input type="text" placeholder="Title" name="title" required>
	            </div>
	            <div class="textbox">
	            	<h4>설명</h4>
	                <textarea placeholder="Description" name="description" required></textarea>
	            </div>
	            <div class="textbox">
	            	<h4>영상길이</h4>
	                <input type="text" placeholder="Duration (in seconds)" name="duration" required>
	            </div>
	            <div class="textbox">
	            	<h4>카테고리</h4>
	                <input type="text" placeholder="Category" name="category" required>
	            </div>
	            <div class="textbox">
	            	<h4>태그</h4>
	                <input type="text" placeholder="Tags" name="tags" required>
	            </div>
	            <div class="textbox">
	            	<h4>상태</h4>
	                <input type="text" placeholder="Status (e.g., 공개, 비공개)" name="status" required>
	            </div>
	            <div class="textbox">
	            	<h4>썸네일</h4>
	                <input type="text" placeholder="Thumbnail URL" name="thumbnail_url" required>
	            </div>
	            <div class="textbox">
	            	<h4>영상주소</h4>
	                <input type="text" placeholder="Video URL" name="video_url" required>
	            </div>
	            <div class="textbox">
	            	<h4>로고이미지주소</h4>
	                <input type="text" placeholder="Logo URL" name="logo_url" required>
	            </div>
	            <div class="btn-sc">
	                <button class="btn submit" type="submit">등 록</button>
	                <button class="btn clear" type="reset">취 소</button>
            	</div>
            </div>
            
        </form>
    </div>
</body>
</html>
