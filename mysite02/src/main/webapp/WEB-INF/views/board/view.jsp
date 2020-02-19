<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
	pageContext.setAttribute("newLine", "\n");
%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" /><!--  -->
		<div id="content">
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${boardVO.title }</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">${fn:replace(boardVO.contents,newLine,"<br>") }</div>	
						</td>
					</tr>
				</table>
				<div class="bottom">
					<c:if test="${boardVO.userName!=authUser.name }">
						<a href="${pageContext.request.contextPath }/board?a=writeform&no=${boardVO.no}">답글작성</a>
					</c:if>
					
					<a href="${pageContext.request.contextPath }/board?num=1">글목록</a>
					<c:if test="${boardVO.userName==authUser.name }">
						<a href="${pageContext.request.contextPath }/board?a=modifyform&no=${boardVO.no}">글수정</a>
					</c:if>
					
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>