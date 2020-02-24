<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%-- <%@page import="com.douzone.mysite.vo.UserVO"%> --%>

<%-- <%
	//jsp안에서는 session이 내장되어 있음
	UserVO authUser = (UserVO)session.getAttribute("authUser");	<!--  -->
%> --%>
<div id="header">
	<h1>MySite</h1>
	<ul>
		<c:choose>
			<c:when test="${empty authUser }">
				<li><a href="${pageContext.request.contextPath }/user/login">로그인</a><li>
				<%-- <li><a href="${pageContext.request.contextPath }/user?a=loginform">로그인</a><li> --%>
				<%-- <li><a href="<%=request.getContextPath() %>/user?a=loginform">로그인</a><li> --%>
				<li><a href="${pageContext.request.contextPath }/user/join">회원가입</a><li>
				<%-- <li><a href="${pageContext.request.contextPath }/user?a=joinform">회원가입</a><li> --%>
				<%-- <li><a href="<%=request.getContextPath() %>/user?a=joinform">회원가입</a><li> --%>
			</c:when>
			<c:otherwise>
				<li><a href="${pageContext.request.contextPath }/user/update">회원정보수정</a>	<li>
				<%-- <li><a href="${pageContext.request.contextPath }/user?a=updateform">회원정보수정</a>	<li> --%>
				<%-- <li><a href="<%=request.getContextPath() %>/user?a=updateform">회원정보수정</a>	<li> --%>
				<li><a href="${pageContext.request.contextPath }/user/logout">로그아웃</a><li>
				<%-- <li><a href="${pageContext.request.contextPath }/user?a=logout">로그아웃</a><li> --%>
				<%-- <li><a href="<%=request.getContextPath() %>/user?a=logout">로그아웃</a><li> --%>
				<li>${authUser.name }님 안녕하세요 ^^;</li>
				<%-- <li><%=authUser.getName() %>님 안녕하세요 ^^;</li> --%>
			</c:otherwise>
		</c:choose>
		
<%-- 		<%
			if(authUser == null){
		%>
			<li><a href="<%=request.getContextPath() %>/user?a=loginform">로그인</a>
			<li>
			<li><a href="<%=request.getContextPath() %>/user?a=joinform">회원가입</a>
			<li>
		<%
			}else{
		%>	
			
			<li><a href="<%=request.getContextPath() %>/user?a=updateform">회원정보수정</a>
			<li>
			<li><a href="<%=request.getContextPath() %>/user?a=logout">로그아웃</a>
			<li>
			<li><%=authUser.getName() %>님 안녕하세요 ^^;</li>
		<%
			}
		%> --%>
	</ul>
</div>