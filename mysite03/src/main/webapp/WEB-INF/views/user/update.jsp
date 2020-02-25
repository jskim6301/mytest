<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="com.douzone.mysite.vo.UserVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%
	//jsp안에서는 session이 내장되어 있음
	UserVO authUser = (UserVO)session.getAttribute("authUser");	

%> --%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/header.jsp" /><!--  -->
		<div id="content">
			<div id="user">

				<form id="join-form" name="joinForm" method="post" action="${pageContext.request.contextPath }/user/update">
					<label class="block-label" for="name">이름</label>
					<input id="name" name="name" type="text" value="${authUser.name }">

					<label class="block-label" for="email">이메일</label>
					<h4>${userVO.email }</h4>
					<%-- <h4><%=authUser.getEmail() %></h4> --%>					
					<label class="block-label">패스워드</label>
					<input name="password" type="password" value="">
					
					<fieldset>
						<legend>성별</legend>
						<c:choose>
							<c:when test="${authUser.gender == 'female' }">
								<label>여</label> <input type="radio" name="gender" value="female" checked="checked">
								<label>남</label> <input type="radio" name="gender" value="male">	
							</c:when>
							<c:otherwise>
								<label>여</label> <input type="radio" name="gender" value="female" >
								<label>남</label> <input type="radio" name="gender" value="male" checked="checked">							
							</c:otherwise>
						</c:choose>
					</fieldset>
					

<%-- 					<fieldset>
						<legend>성별</legend>
						<%if("female".equals(authUser.getGender())){ %>
						<label>여</label> <input type="radio" name="gender" value="female" checked="checked">
						<label>남</label> <input type="radio" name="gender" value="male">
						<%}else if("male".equals(authUser.getGender())){ %>
						<label>여</label> <input type="radio" name="gender" value="female" >
						<label>남</label> <input type="radio" name="gender" value="male" checked="checked">
						<%} %>
					</fieldset> --%>

					
					
					<input type="submit" value="수정하기">
					
				</form>
			</div>
		</div>
	<jsp:include page="/WEB-INF/views/includes/navigation.jsp" />
	<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>