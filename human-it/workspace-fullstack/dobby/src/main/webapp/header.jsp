<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    HttpSession session1 = request.getSession(false);
    String memberID = (session != null) ? (String) session.getAttribute("memberID") : null;
%>    
<header class="header">
    <div class="header_logo">
        <button id = "toggleButton">
            <i class = "fas fa-bars"></i>
        </button>
        <a href="./main.jsp">
        <img src="./images/logo1.png" alt="집요정TV">
        </a>
    </div>

    <div class="search">
        <form action="javascript:void(0);">
            <div class = "search-inner">
            <input type="search" placeholder="검색">
            </div>
            <button type = "submit">
                <i class="fas fa-search"></i>
            </button>
        </form>
    </div>
    <div class="header_icons">	
    	 <%
            if (memberID == null) {
        %>
            <a href="login.jsp"><i id="login" class="fas fa-user-circle">로그인</i></a>
        <%
            } else {
        %>
            <span id="my_id"><%= memberID %></span>
            <a href="logout.jsp">
            <button class="logout-btn">Logout</button>
            </a>
            <a href="upload_form.jsp"><i class="fas fa-video"></i></a>
            <i class="fas fa-ellipsis-h"></i>
            <i class="fas fa-bell"></i>
        <%
            }
        %>
    </div>
   </header>