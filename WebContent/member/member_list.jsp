<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.sql.*"%>
<%@ page import="javax.sql.*"%>
<%@ page import="javax.naming.*"%>
 <%@ taglib prefix = "c"
				uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>
<body>

<jsp:include page="header.jsp"></jsp:include>
<table border = 1>
		<caption>회원목록</caption>
<thead>
	<tr>
		<td>아이디</td><td>이름</td><td>삭제</td>
	</tr>
	</thead>
	<tbody>
	<c:forEach var="list" items="${totallist}">
	<tr>
		<td><a href = "member_info.net?id=${list.id}">
			${list.id}</a></td>		
		<td>${list.name}</td>
		<td><a href = "member_delete.net?id=${list.id}">삭제</a></td>
		</tr>
		</c:forEach>
	</tbody>
</table>
</body>
</html>