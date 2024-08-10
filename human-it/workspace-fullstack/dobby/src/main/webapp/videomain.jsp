<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "com.dobby.utils.DBManager" %>
<%@ page import = "java.sql.Connection" %>
<%@ page import = "java.sql.PreparedStatement" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import = "java.sql.SQLException" %>
<%@ page import = "java.util.HashMap" %>
<%@ page import = "java.util.Map" %>
<%@ page import = "java.util.Date" %>
<%@ page import = "java.util.concurrent.TimeUnit" %>
<%@ page import = "java.text.SimpleDateFormat" %>
<%
    String seq = request.getParameter("seq");

    if (seq == null || seq.isEmpty()) {
        out.println("Invalid video ID.");
        return;
    }

    Map<String, String> video = new HashMap<>();
    long daysBetween = 0;
    
    try (Connection conn = DBManager.getDBConnection()) {
        String sql = "SELECT seq, title, description, tags, thumbnail_url, video_url, views, likes, logo_url, uploader_id, upload_date FROM video WHERE seq = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, seq);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    video.put("seq", rs.getString("seq"));
                    video.put("title", rs.getString("title"));
                    video.put("description", rs.getString("description"));
                    video.put("tags", rs.getString("tags"));
                    video.put("thumbnail_url", rs.getString("thumbnail_url"));
                    video.put("video_url", rs.getString("video_url"));
                    video.put("views", rs.getString("views"));
                    video.put("likes", rs.getString("likes"));
                    video.put("logo_url", rs.getString("logo_url"));
                    video.put("uploader_id", rs.getString("uploader_id"));
                    
                    // 날짜 차이 계산
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date uploadDate = rs.getDate("upload_date");
                    Date currentDate = new Date();
                    long diffInMillies = Math.abs(currentDate.getTime() - uploadDate.getTime());
                    daysBetween = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
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
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link href="https://fonts.googleapis.com/css?family=Roboto&display=swap" rel="stylesheet" />
  <script src="https://kit.fontawesome.com/2d323a629b.js" crossorigin="anonymous"></script>
 
  <!-- 1. 탭 타이틀 세팅 -->
  <title><%= video.get("title") %></title>
  <!-- 2. favicon(favorite icon) 세팅 -->
  <link rel="shortcut icon" href="./favicon.ico" />
  <link rel="icon" href="./favicon.png" />
  <!-- 3. reset.css 세팅(cdn) -->
  <link href="https://cdn.jsdelivr.net/npm/reset-css@5.0.2/reset.min.css" rel="stylesheet">
  <!-- 4. 커스템 css파일 세팅(local) -->
  <link href="./css/header.css" rel="stylesheet">
  <link href="./css/sidebar.css" rel="stylesheet">
  <link href="./css/videoplay.css" rel="stylesheet">
  <!-- 5. 폰트 설정 -->
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link
    href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap"
    rel="stylesheet">
  <!-- 6. 아이콘 설정 -->
  <link rel="stylesheet"
    href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
  
  <!-- 8. 자바 스크립트 설정 -->
  <script defer src="./js/main.js"></script>
  <script defer src="./js/videoplay.js" ></script>
  
 
  <!-- gsap js -->
  <script src="https://cdnjs.cloudflare.com/ajax/libs/lodash.js/4.17.21/lodash.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.5.1/gsap.min.js"
    integrity="sha512-IQLehpLoVS4fNzl7IfH8Iowfm5+RiMGtHykgZJl9AWMgqx0AmJ6cRWcB+GaGVtIsnC4voMfm8f2vwtY+6oPjpQ=="
    crossorigin="anonymous" referrerpolicy="no-referrer"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.5.1/ScrollToPlugin.min.js"
    integrity="sha512-nTHzMQK7lwWt8nL4KF6DhwLHluv6dVq/hNnj2PBN0xMl2KaMm1PM02csx57mmToPAodHmPsipoERRNn4pG7f+Q=="
    crossorigin="anonymous" referrerpolicy="no-referrer"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/ScrollMagic/2.0.8/ScrollMagic.min.js"
    integrity="sha512-8E3KZoPoZCD+1dgfqhPbejQBnQfBXe8FuwL4z/c8sTrgeDMFEnoyTlH3obB4/fV+6Sg0a0XF+L/6xS4Xx1fUEg=="
    crossorigin="anonymous" referrerpolicy="no-referrer"></script>
  <!-- swiper 6.8.4 -->
  <script src="https://cdnjs.cloudflare.com/ajax/libs/Swiper/6.8.4/swiper-bundle.min.js"
    integrity="sha512-BABFxitBmYt44N6n1NIJkGOsNaVaCs/GpaJwDktrfkWIBFnMD6p5l9m+Kc/4SLJSJ4mYf+cstX98NYrsG/M9ag=="
    crossorigin="anonymous" referrerpolicy="no-referrer"></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/Swiper/6.8.4/swiper-bundle.min.css"
    integrity="sha512-aMup4I6BUl0dG4IBb0/f32270a5XP7H1xplAJ80uVKP6ejYCgZWcBudljdsointfHxn5o302Jbnq1FXsBaMuoQ=="
    crossorigin="anonymous" referrerpolicy="no-referrer" />
  <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

  <!--  아이콘 불러오기 -->
  <script src="https://kit.fontawesome.com/12d13cde63.js" crossorigin="anonymous"></script>
  
  
</head>

<body>
  <!-- ------HEADER ------ -->
<%@ include file="header.jsp" %>
<%@ include file="sidebar.jsp" %>

<!-- Video Player -->
<div class="allVideo">
  <!-- Video Player -->
  <div class="videoAndNext">
      <div class="player">
      	<video src="<%= video.get("video_url") %>" controls></video>
      </div>

    <!-- Video info & Up next -->
    <div class="infoAndUpNext">
      <!-- Video Info -->
      <section class="info">
        <!-- Metadata -->
        <div class="metadata">
          <div class="hashtags">
            <span><%= video.get("tags") %></span>
          </div>
          <h2><%= video.get("title") %></h2>
            <button class="moreBtn">
              <i class="fas fa-caret-down"></i>
            </button>
          </div>
          <div class="view-day">
          	<div class="views"><%= video.get("views") %> views · <%= daysBetween %> days ago</div>
        </div>
        <!-- Actions buttons -->
        <ul class="actions">
          <li>
            <button id="likeButton" data-seq="<%= video.get("seq") %>" data-action="like">
              <i class="active fas fa-thumbs-up"></i><span id="likeCount"><%= video.get("likes") %></span>
            </button>
          </li>
          <li>
            <button id="dislikeButton" data-seq="<%= video.get("seq") %>" data-action="dislike">
              <i class="fas fa-thumbs-down"></i><span id="dislikeCount">0</span>
            </button>
          </li>
          <li>
            <button><i class="fas fa-share"></i><span>Share</span></button>
          </li>
          <li>
            <button><i class="fas fa-plus"></i><span>Save</span></button>
          </li>
          <li>
            <button>
              <i class="fab fa-font-awesome-flag"></i><span>Report</span>
            </button>
          </li>
        </ul>
        <!-- Channel description -->
        <div class="channel">
          <div class="metadata">
            <img src="<%= video.get("logo_url") %>" alt="" />
            <div class="info">
              <span class="name"><%= video.get("uploader_id") %></span>
              <span class="subscribers">1M subcribers</span>
            </div>
          </div>
          <button class="subscribe">구독</button>
        </div>
        <!-- 작성자 코멘트 -->
        <div class="channelComment">
          <p><%= video.get("description") %></p>
        </div>
      </section>
    </div>
  </div>

  <!-- 사이드 다음영상 시작-->
  <section class="upNext">
    <span class="title">추천 영상</span>
    <ul>
      <li class="item">
        <div class="img"><img src="./images/amd.png" alt="" /></div>
        <div class="itemInfo">
          <span class="title">AMD</span>
          <span class="name">민기</span>
          <span class="views">82K views</span>
        </div>
        <button class="moreBtn"><i class="fas fa-ellipsis-v"></i></button>
      </li>
      <li class="item">
        <div class="img"><img src="./images/intel.png" alt="" /></div>
        <div class="itemInfo">
          <span class="title">intel</span>
          <span class="name">민기</span>
          <span class="views">82K views</span>
        </div>
        <button class="moreBtn"><i class="fas fa-ellipsis-v"></i></button>
      </li>
      <li class="item">
        <div class="img"><img src="./images/nvidia.png" alt="" /></div>
        <div class="itemInfo">
          <span class="title"><strong>nvidia</strong></span>
          <span class="name">민기</span>
          <span class="views">82K views</span>
        </div>
        <button class="moreBtn"><i class="fas fa-ellipsis-v"></i></button>
      </li>
      <li class="item">
        <div class="img"><img src="./images/amd.png" alt="" /></div>
        <div class="itemInfo">
          <span class="title">AMD</span>
          <span class="name">민기</span>
          <span class="views">82K views</span>
        </div>
        <button class="moreBtn"><i class="fas fa-ellipsis-v"></i></button>
      </li>
      <li class="item">
        <div class="img"><img src="./images/amd.png" alt="" /></div>
        <div class="itemInfo">
          <span class="title">AMD</span>
          <span class="name">민기</span>
          <span class="views">82K views</span>
        </div>
        <button class="moreBtn"><i class="fas fa-ellipsis-v"></i></button>
      </li>
      <li class="item">
        <div class="img"><img src="./images/amd.png" alt="" /></div>
        <div class="itemInfo">
          <span class="title">AMD</span>
          <span class="name">민기</span>
          <span class="views">82K views</span>
        </div>
        <button class="moreBtn"><i class="fas fa-ellipsis-v"></i></button>
      </li>
    </ul>
  </section>
</div>
<script>
    // 좋아요 및 싫어요 버튼 클릭 이벤트 처리
    $(document).ready(function() {
        $('#likeButton').click(function() {
            const seq = $(this).data('seq');
            $.post('likes.jsp', { seq: seq, action: 'like' }, function(response) {
                $('#likeCount').text(response.newLikes);
            });
        });

        $('#dislikeButton').click(function() {
            const seq = $(this).data('seq');
            $.post('likes.jsp', { seq: seq, action: 'dislike' }, function(response) {
                $('#likeCount').text(response.newLikes);
            });
        });
    });
</script>
	  
	</div>
</body>

</html>