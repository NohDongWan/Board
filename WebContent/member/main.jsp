<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix = "c"
				uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>
<body>

<%@ include file = "header.jsp" %>
	<c:if test="${id=='admin'}">
		<a href = "member_list.net">관리자모드 접속</a>
	</c:if>
</body>
</html>