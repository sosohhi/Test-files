<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    HttpSession currentsession = request.getSession(false);
    if (session != null) {
        session.invalidate();
    }
    response.sendRedirect("main.jsp");
%>