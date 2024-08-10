<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.sql.Connection" %>
<%@ page import = "java.sql.DriverManager" %>
<%@ page import = "java.sql.PreparedStatement" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import = "java.sql.SQLException" %>
<%@ page import = "dob.DBManager" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 1. 탭 타이틀 세팅 -->
    <title>집요정 TV</title>
    <!-- 2. favicon(favorite icon) 세팅 -->
    <link rel="shortcut icon" href="./favicon.ico" />
    <link rel="icon" href="./favicon.png" />
    <!-- 3. reset.css 세팅(cdn) -->
    <link href="https://cdn.jsdelivr.net/npm/reset-css@5.0.2/reset.min.css" rel="stylesheet">
    <!-- 4. 커스템 css파일 세팅(local) -->
    <link href="./YouTube/css/main.css" rel="stylesheet">
    <link href="./music.css" rel="stylesheet">
    <link href="./popup.css" rel="stylesheet">
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
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <!-- 로컬 jQuery 파일을 불러오기 -->
    
    <!--  아이콘 불러오기 -->
    <script src="https://kit.fontawesome.com/12d13cde63.js" crossorigin="anonymous"></script>
    <script defer src="./YouTube/js/main.js"></script>
    <!-- <script defer src="./js/jquery-3.7.1.js"></script> -->
    <script src="./js/MV.js"></script>
    <script src="./js/popup.js"></script>
</head>
<body>
    <!-- ------HEADER ------ -->
   <header class="header">
    <div class="header_logo">
        <button id = "toggleButton">
            <i class = "fas fa-bars"></i>
        </button>
        <img src="./YouTube/images/logo1.png" alt="집요정TV">
    </div>

    <div class="search">
        <form action="">
            <div class = "search-inner">
            <input type="search" placeholder="검색">
            </div>
            <button><i class="fas fa-search"></i></button>
        </form>
    </div>
    <div class="header_icons">
        <button id ="videoToggle"> <i class="fas fa-video"></i></button>
        <i class="fas fa-ellipsis-h"></i>
        <i class="fas fa-bell"></i>
        <i class="fas fa-user-circle"></i>
    </div>
   </header>

   <!------MAIN------>
   <div class="YtBody">
    <div id = "sidebar" class="sidebar">
        <div class="sidebar_nav">
            <div class="nav-item">
                <i class="fas fa-home"></i>
                <span id="123">홈</span>
                <script>
                    document.getElementById('123').addEventListener('click', function() {
                        window.location.href = 'youtube.jsp'; // 음악 파일의 정확한 경로를 입력하세요.
                    });
                </script>
            </div>
            <!-- <div class="nav-item">
                <i class="fa-solid fa-bolt"></i>
                <span>Shorts</span>
            </div> -->
            <div class="nav-item">
                <i class="fab fa-youtube"></i>
                <span>구독</span>
            </div>
        </div>
        <hr>
        <div class="sidebar_nav">
            <div class="nav-item na">
                <i class="fas fa-list"></i>
                <span>나</span>
            </div>
            <div class = "sub-menu">
                <div class="nav-item items">
                    <i class="fa-solid fa-history"></i>
                    <span>시청기록</span>
                </div>
                <div class="nav-item items">
                    <i class="fas fa-play"></i>
                    <span>내 동영상</span>
                </div>
                <div class="nav-item items">
                    <i class="fas fa-clock"></i>
                    <span>나중에 볼 영상</span>
                </div>
                <div class="nav-item items">
                    <i class="fas fa-thumbs-up"></i>
                    <span>좋아요 표시한 영상</span>
                </div>
            </div>
            
            
        </div>
        <hr>
        <div class="sidebar_nav">
            <div class="nav-item">
                <i class="fa-solid fa-fire"></i>
                <span>인기 급상승</span>
            </div>
            <div class="nav-item">
                <i class="fa-solid fa-bag-shopping"></i>
                <span>쇼핑</span>
            </div>
            <div class="nav-item" id="music-button">
                <i class="fa-solid fa-music"></i>
                <span>음악</span>
            </div>
        
            <script>
                document.getElementById('music-button').addEventListener('click', function() {
                    window.location.href = 'music.jsp'; // 음악 파일의 정확한 경로를 입력하세요.
                });
            </script>
            <div class="nav-item">
                <i class="fa-solid fa-clapperboard"></i>
                <span>영화</span>
            </div>
            <div class="nav-item">
                <i class="fa-solid fa-tower-broadcast"></i>
                <span>실시간</span>
            </div>
            <div class="nav-item">
                <i class="fa-solid fa-gamepad"></i>
                <span>게임</span>
            </div>
            <div class="nav-item">
                <i class="fa-solid fa-trophy"></i>
                <span>스포츠</span>
            </div>
            <div class="nav-item">
                <i class="fa-regular fa-lightbulb"></i>
                <span>학습프로그램</span>
            </div>
            <div class="nav-item">
                <i class="fa-solid fa-podcast"></i>
                <span>팟캐스트</span>
            </div>
        </div>
    </div>
    <div class="video_selection">
        <div class="recommendboxes">
            <button class="box">전체</button>
            <button class="box">뉴진스</button>
            <button class="box">NewJeans</button>
            <button class="box">Hype Boy</button>
            <button class="box">Shper Shy</button>
        </div>
        
        <div class="main-wrap" id="home">
            <div class="newestAlbum" id="newestAlbum">
                <h1></h1>
                <div class="new-album">
                    <ul class="each-song" data-key="124"="dark-mystery-trailer-taking-our-time">
                        <li onclick="openVideo('./video1/1.mp4')">
                        
                            <img class="coverImg" src="https://cdnimg.melon.co.kr/cm2/album/images/114/87/023/11487023_20240527154018_500.jpg?1592e95ebf259717f07c393e684b7395/melon/resize/282/quality/80/optimize">
                            <div class="song-detail">
                                <span class="title">Supernova</span>
                                <span class="singer">aespa</span>
                            </div>
                        </li>
                    </ul>
                    <ul class="each-song" data-song-id="6441ff15b5e7a75b48e399a1" music="awaken-136824">
                        <li onclick="openVideo('./video1/2.mp4')">
                            <img class="coverImg" src="https://cdnimg.melon.co.kr/cm2/album/images/112/81/456/11281456_20230706180841_500.jpg/melon/resize/260/quality/80/optimize">
                            <div class="song-detail">
                                <span class="title">Super Shy</span>
                                <span class="singer">NewJeans</span>
                            </div>
                        </li>
                    </ul>
                    <ul class="each-song" data-song-id="6441ff15b5e7a75b48e399a2" music="inspiring-dream">
                        <li onclick="openVideo('https://youtu.be/11iZcYbq_is')">
                            <img class="coverImg" src="https://cdnimg.melon.co.kr/cm2/album/images/115/17/951/11517951_20240620173412_500.jpg/melon/resize/120/quality/80/optimize">
                            <div class="song-detail">
                                <span class="title">Small girl(feat.도경수(D.O))</span>
                                <span class="singer">이영지</span>
                            </div>
                        </li>
                    </ul>
                    <ul class="each-song" data-song-id="6441ff15b5e7a75b48e399a3" music="playful">
                        <li onclick="openVideo('https://youtu.be/rTKqSmX9XhQ')">
                            <img class="coverImg" src="https://cdnimg.melon.co.kr/cm2/album/images/115/33/515/11533515_20240708112323_500.jpg/melon/resize/120/quality/80/optimize">
                            <div class="song-detail">
                                <span class="title">클락션 (Klaxon)</span>
                                <span class="singer">(여자)아이들</span>
                            </div>
                        </li>
                    </ul>
                    <ul class="each-song" data-song-id="6441ff15b5e7a75b48e399a5" music="stomps-and-claps-percussion-and-rhythm">
                        <li onclick="openVideo('https://youtu.be/IajeQM00yfE')">
                            <img class="coverImg" src="https://cdnimg.melon.co.kr/cm2/album/images/115/25/661/11525661_20240628155508_500.jpg/melon/resize/120/quality/80/optimize">
                            <div class="song-detail">
                                <span class="title">Sticky</span>
                                <span class="singer">KISS OF LIFE</span>
                            </div>
                        </li>
                    </ul>
                    <ul class="each-song" data-song-id="6441ff15b5e7a75b48e399a9" music="business-time">
                        <li onclick="openVideo('https://youtu.be/ZncbtRo7RXs')">
                            <img class="coverImg" src="https://cdnimg.melon.co.kr/cm2/album/images/115/14/014/11514014_20240621110830_500.jpg/melon/resize/120/quality/80/optimize">
                            <div class="song-detail">
                                <span class="title">Supernatural</span>
                                <span class="singer">NewJeans</span>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            
                <script></script>
                <div class="top10">
                    <div class="chart-header">
                        <span>Today's TOP</span>
                        <button>
                            <a href="#fullchart">더보기</a>
                        </button>
                    </div>
                    <div class="chart-wrap">
                        <div class="chart-div">
                            <ul class="each-song" data-song-id="6441ff15b5e7a75b48e399a0" music="dark-mystery-trailer-taking-our-time">
                                <li onclick="openVideo('https://youtu.be/phuiiNCxRMg')">
                                    <img class="coverImg" src="https://cdnimg.melon.co.kr/cm2/album/images/114/87/023/11487023_20240527154018_500.jpg/melon/resize/120/quality/80/optimize">
                                    <div class="song-detail">
                                        <span class="rank">1</span>
                                        <div class="songInfo">
                                            <span class="title">Supernova</span>
                                            <span class="singer">aespa</span>
                                        </div>
                                    </div>
                                    <i class="fa fa-play"></i>
                                </li>
                            </ul>
                            <ul class="each-song" data-song-id="6441ff15b5e7a75b48e399a3" music="playful">
                                <li>
                                    <img class="coverImg" src="https://cdnimg.melon.co.kr/cm2/album/images/112/81/456/11281456_20230706180841_500.jpg/melon/resize/260/quality/80/optimize">
                                    <div class="song-detail">
                                        <span class="rank">2</span>
                                        <div class="songInfo">
                                            <span class="title">Super Shy</span>
                                            <span class="singer">NewJeans</span>
                                        </div>
                                    </div>
                                    <i class="fa fa-play"></i>
                                </li>
                            </ul>
                            <ul class="each-song" data-song-id="6441ff15b5e7a75b48e399a6" music="beautiful-trip">
                                <li>
                                    <img class="coverImg" src="https://cdn.pixabay.com/audio/2023/03/14/00-19-33-916_200x200.jpg">
                                    <div class="song-detail">
                                        <span class="rank">3</span>
                                        <div class="songInfo">
                                            <span class="title">Beautiful Trip</span>
                                            <span class="singer">SoulProdMusic</span>
                                        </div>
                                    </div>
                                    <i class="fa fa-play"></i>
                                </li>
                            </ul>
                            <ul class="each-song" data-song-id="6441ff15b5e7a75b48e399a2" music="inspiring-dream">
                                <li>
                                    <img class="coverImg" src="https://cdnimg.melon.co.kr/cm2/album/images/115/17/951/11517951_20240620173412_500.jpg/melon/resize/120/quality/80/optimize">
                                    <div class="song-detail">
                                        <span class="rank">4</span>
                                        <div class="songInfo">
                                            <span class="title">Small girl(feat.도경수(D.O))</span>
                                            <span class="singer">이영지</span>
                                        </div>
                                    </div>
                                    <i class="fa fa-play"></i>
                                </li>
                            </ul>
                            <ul class="each-song" data-song-id="6441ff15b5e7a75b48e399a4" music="cinematic-documentary-piano">
                                <li>
                                    <img class="coverImg" src="https://cdnimg.melon.co.kr/cm2/album/images/115/33/515/11533515_20240708112323_500.jpg/melon/resize/120/quality/80/optimize">
                                    <div class="song-detail">
                                        <span class="rank">5</span>
                                        <div class="songInfo">
                                            <span class="title">Sticky</span>
                                            <span class="singer">KISS OF LIFE</span>
                                        </div>
                                    </div>
                                    <i class="fa fa-play"></i>
                                </li>
                            </ul>
                            <ul class="each-song" data-song-id="6441ff15b5e7a75b48e399a7" music="lifelike">
                                <li>
                                    <img class="coverImg" src="https://cdnimg.melon.co.kr/cm2/album/images/115/14/014/11514014_20240621110830_500.jpg/melon/resize/120/quality/80/optimize">
                                    <div class="song-detail">
                                        <span class="rank">6</span>
                                        <div class="songInfo">
                                            <span class="title">Supernatural</span>
                                            <span class="singer">NewJeans</span>
                                        </div>
                                    </div>
                                    <i class="fa fa-play"></i>
                                </li>
                            </ul>
                            <ul class="each-song" data-song-id="6441ff15b5e7a75b48e399a1" music="awaken-136824">
                                <li>
                                    <img class="coverImg" src="https://cdnimg.melon.co.kr/cm2/album/images/114/59/325/11459325_20240405171054_500.jpg/melon/resize/120/quality/80/optimize">
                                    <div class="song-detail">
                                        <span class="rank">7</span>
                                        <div class="songInfo">
                                            <span class="title">소나기</span>
                                            <span class="singer">이클립스(ECLIPSE)</span>
                                        </div>
                                    </div>
                                    <i class="fa fa-play"></i>
                                </li>
                            </ul>
                            <ul class="each-song" data-song-id="6441ff15b5e7a75b48e399a5" music="stomps-and-claps-percussion-and-rhythm">
                                <li>
                                    <img class="coverImg" src="https://cdnimg.melon.co.kr/cm2/album/images/114/54/681/11454681_20240328174504_500.jpg/melon/resize/120/quality/80/optimize">
                                    <div class="song-detail">
                                        <span class="rank">8</span>
                                        <div class="songInfo">
                                            <span class="title">고민중독</span>
                                            <span class="singer">QWER</span>
                                        </div>
                                    </div>
                                    <i class="fa fa-play"></i>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </main>
            <div class="music-video" id="mv">
                <h3>MV</h3>
                <div class="mv-wrap">
                    <div class="each-mv" data-video-id="jOTfBlKSQYY">
                        <img src="https://i.ytimg.com/vi/jOTfBlKSQYY/maxresdefault.jpg" width="auto" height="100%" class="preview-img">
                        <span>NewJeans(뉴진스) 'ETA'</span>
                    </div>
                    <div class="each-mv" data-video-id="m6pTbEz4w3o">
                        <img src="https://i.ytimg.com/vi/m6pTbEz4w3o/maxresdefault.jpg" width="auto" height="100%" class="preview-img">
                        <span>NewJeans(뉴진스) 'Right Now'</span>
                    </div>
                    <div class="each-mv" data-video-id="js1CtxSY38I">
                        <img src="https://i.ytimg.com/vi/js1CtxSY38I/maxresdefault.jpg" width="auto" height="100%" class="preview-img">
                        <span>NewJeans(뉴진스) 'Attention'</span>
                    </div>
                    <div class="each-mv" data-video-id="V37TaRdVUQY">
                        <img src="https://i.ytimg.com/vi/V37TaRdVUQY/maxresdefault.jpg" width="auto" height="100%" class="preview-img">
                        <span>NewJeans (뉴진스) 'Ditto'</span>
                    </div>
                    <div class="each-mv" data-video-id="ArmDp-zijuc">
                        <img src="https://i.ytimg.com/vi/ArmDp-zijuc/maxresdefault.jpg" width="auto" height="100%" class="preview-img">
                        <span>NewJeans (뉴진스) 'Super Shy'</span>
                    </div>
                    <div class="each-mv" data-video-id="Rrf8uQFvICE">
                        <img src="https://i.ytimg.com/vi/Rrf8uQFvICE/maxresdefault.jpg" width="auto" height="100%" class="preview-img">
                        <span>NewJeans (뉴진스) 'Hype Boy'</span>
                    </div>
                </div>
            </div>

            <div id="videoPopup" class="popup">
                <iframe id="videoFrame" src="" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
            </div>
            <script src="/js/popup.js"></script>

            <div id="fullchart">
                <div class="chart-wrap">
                    <h1> TOP 10  </h1>
                    <div class="full-chart-div">
                        <ul class="chart-header">
                            <li class="Rank">Rank</li>
                            <li class="Song">Song</li>
                            <li class="Play">Play</li>
                            <li class="Heart">Heart</li>
                            <li class="Listen">Listen</li>
                        </ul>
                        <ul class="each-song" data-song-id="6441ff15b5e7a75b48e399a0" music="dark-mystery-trailer-taking-our-time">
                            <li class="Rank">
                                <span class="rank">1</span>
                            </li>
                            <li class="Song">
                                <img class="coverImg" src="https://cdnimg.melon.co.kr/cm2/album/images/114/87/023/11487023_20240527154018_500.jpg/melon/resize/120/quality/80/optimize">
                                <div class="songInfo">
                                    <span class="title">Supernova</span>
                                    <span class="singer">aespa</span>
                                </div>
                            </li>
                            <li class="Play">
                                <span class="playcnt">487</span>
                            </li>
                            <li class="Heart">
                                <span class="heartcnt">18</span>
                            </li>
                            <li class="Listen">
                                <i class="fa fa-play" style="cursor:pointer"></i>
                            </li>
                        </ul>
                        <ul class="each-song" data-song-id="6441ff15b5e7a75b48e399a3" music="playful">
                            <li class="Rank">
                                <span class="rank">2</span>
                            </li>
                            <li class="Song">
                                <img class="coverImg" src="https://cdnimg.melon.co.kr/cm2/album/images/112/81/456/11281456_20230706180841_500.jpg/melon/resize/260/quality/80/optimize">
                                <div class="songInfo">
                                    <span class="title">Super shy</span>
                                    <span class="singer">NewJeans</span>
                                </div>
                            </li>
                            <li class="Play">
                                <span class="playcnt">259</span>
                            </li>
                            <li class="Heart">
                                <span class="heartcnt">16</span>
                            </li>
                            <li class="Listen">
                                <i class="fa fa-play" style="cursor:pointer"></i>
                            </li>
                        </ul>
                        <ul class="each-song" data-song-id="6441ff15b5e7a75b48e399a6" music="beautiful-trip">
                            <li class="Rank">
                                <span class="rank">3</span>
                            </li>
                            <li class="Song">
                                <img class="coverImg" src="https://cdnimg.melon.co.kr/cm2/album/images/115/17/951/11517951_20240620173412_500.jpg/melon/resize/120/quality/80/optimize">
                                <div class="songInfo">
                                    <span class="title">Small girl (feat. 도경수(D.O.))</span>
                                    <span class="singer">이영지</span>
                                </div>
                            </li>
                            <li class="Play">
                                <span class="playcnt">166</span>
                            </li>
                            <li class="Heart">
                                <span class="heartcnt">4</span>
                            </li>
                            <li class="Listen">
                                <i class="fa fa-play" style="cursor:pointer"></i>
                            </li>
                        </ul>
                        <ul class="each-song" data-song-id="6441ff15b5e7a75b48e399a2" music="inspiring-dream">
                            <li class="Rank">
                                <span class="rank">4</span>
                            </li>
                            <li class="Song">
                                <img class="coverImg" src="https://cdnimg.melon.co.kr/cm2/album/images/115/33/515/11533515_20240708112323_500.jpg/melon/resize/120/quality/80/optimize">
                                <div class="songInfo">
                                    <span class="title">Sticky</span>
                                    <span class="singer">KISS OF LIFE</span>
                                </div>
                            </li>
                            <li class="Play">
                                <span class="playcnt">164</span>
                            </li>
                            <li class="Heart">
                                <span class="heartcnt">4</span>
                            </li>
                            <li class="Listen">
                                <i class="fa fa-play" style="cursor:pointer"></i>
                            </li>
                        </ul>
                        <ul class="each-song" data-song-id="6441ff15b5e7a75b48e399a4" music="cinematic-documentary-piano">
                            <li class="Rank">
                                <span class="rank">5</span>
                            </li>
                            <li class="Song">
                                <img class="coverImg" src="https://cdnimg.melon.co.kr/cm2/album/images/115/25/661/11525661_20240628155508_500.jpg/melon/resize/120/quality/80/optimize">
                                <div class="songInfo">
                                    <span class="title">클락션 (Klaxon)</span>
                                    <span class="singer">(여자)아이들</span>
                                </div>
                            </li>
                            <li class="Play">
                                <span class="playcnt">158</span>
                            </li>
                            <li class="Heart">
                                <span class="heartcnt">0</span>
                            </li>
                            <li class="Listen">
                                <i class="fa fa-play" style="cursor:pointer"></i>
                            </li>
                        </ul>
                        <ul class="each-song" data-song-id="6441ff15b5e7a75b48e399a7" music="lifelike">
                            <li class="Rank">
                                <span class="rank">6</span>
                            </li>
                            <li class="Song">
                                <img class="coverImg" src="https://cdnimg.melon.co.kr/cm2/album/images/115/14/014/11514014_20240621110830_500.jpg/melon/resize/120/quality/80/optimize">
                                <div class="songInfo">
                                    <span class="title">Supernatural</span>
                                    <span class="singer">NewJeans</span>
                                </div>
                            </li>
                            <li class="Play">
                                <span class="playcnt">148</span>
                            </li>
                            <li class="Heart">
                                <span class="heartcnt">4</span>
                            </li>
                            <li class="Listen">
                                <i class="fa fa-play" style="cursor:pointer"></i>
                            </li>
                        </ul>
                        <ul class="each-song" data-song-id="6441ff15b5e7a75b48e399a1" music="awaken-136824">
                            <li class="Rank">
                                <span class="rank">7</span>
                            </li>
                            <li class="Song">
                                <img class="coverImg" src="https://cdnimg.melon.co.kr/cm2/album/images/114/59/325/11459325_20240405171054_500.jpg/melon/resize/120/quality/80/optimize">
                                <div class="songInfo">
                                    <span class="title">소나기</span>
                                    <span class="singer">이클립스 (ECLIPSE)</span>
                                </div>
                            </li>
                            <li class="Play">
                                <span class="playcnt">141</span>
                            </li>
                            <li class="Heart">
                                <span class="heartcnt">6</span>
                            </li>
                            <li class="Listen">
                                <i class="fa fa-play" style="cursor:pointer"></i>
                            </li>
                        </ul>
                        <ul class="each-song" data-song-id="6441ff15b5e7a75b48e399a5" music="stomps-and-claps-percussion-and-rhythm">
                            <li class="Rank">
                                <span class="rank">8</span>
                            </li>
                            <li class="Song">
                                <img class="coverImg" src="https://cdnimg.melon.co.kr/cm2/album/images/114/54/681/11454681_20240328174504_500.jpg/melon/resize/120/quality/80/optimize">
                                <div class="songInfo">
                                    <span class="title">고민중독</span>
                                    <span class="singer">QWER</span>
                                </div>
                            </li>
                            <li class="Play">
                                <span class="playcnt">118</span>
                            </li>
                            <li class="Heart">
                                <span class="heartcnt">4</span>
                            </li>
                            <li class="Listen">
                                <i class="fa fa-play" style="cursor:pointer"></i>
                            </li>
                        </ul>
                        <ul class="each-song" data-song-id="6441ff15b5e7a75b48e399a9" music="business-time">
                            <li class="Rank">
                                <span class="rank">9</span>
                            </li>
                            <li class="Song">
                                <img class="coverImg" src="https://cdnimg.melon.co.kr/cm2/album/images/114/75/749/11475749_20240524105642_500.jpg/melon/resize/120/quality/80/optimize">
                                <div class="songInfo">
                                    <span class="title">Bubble Gum</span>
                                    <span class="singer">NewJeans</span>
                                </div>
                            </li>
                            <li class="Play">
                                <span class="playcnt">28</span>
                            </li>
                            <li class="Heart">
                                <span class="heartcnt">0</span>
                            </li>
                            <li class="Listen">
                                <i class="fa fa-play" style="cursor:pointer"></i>
                            </li>
                        </ul>
                        <ul class="each-song" data-song-id="6441ff15b5e7a75b48e399a8" music="for-you">
                            <li class="Rank">
                                <span class="rank">10</span>
                            </li>
                            <li class="Song">
                                <img class="coverImg" src="https://cdnimg.melon.co.kr/cm/album/images/103/07/346/10307346_500.jpg/melon/resize/120/quality/80/optimize">
                                <div class="songInfo">
                                    <span class="title">한 페이지가 될 수 있게</span>
                                    <span class="singer">DAY6 (데이식스)</span>
                                </div>
                            </li>
                            <li class="Play">
                                <span class="playcnt">17</span>
                            </li>
                            <li class="Heart">
                                <span class="heartcnt">0</span>
                            </li>
                            <li class="Listen">
                                <i class="fa fa-play" style="cursor:pointer"></i>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <footer class="footer">&copy;2024 jeonghoonkim</footer>
            <div class="mobile-header">
                <div class="header-btn">
                    <i class="fa fa-bars"></i>
                </div>
                <div class="logo">
                    <span></span>
                </div>
            </div>
        </div>

        
        
    </div>
    </div>
   </div>
</body>
</html>