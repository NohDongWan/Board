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
    	<caption>회원 정보</caption>
   		<c:set var = "m" value = "${memberinfo}"/>
		<tr><td>아이디</td><td>${m.id}</td></tr>
		<tr><td>비밀번호</td><td>${m.password}</td></tr>
		<tr><td>이름</td><td>${m.name}</td></tr>
		<tr><td>나이</td><td>${m.age}</td></tr>
		<tr><td>성별</td><td>${m.gender}</td></tr>
		<tr><td>주소</td><td>${m.email}</td></tr>
	<tr>
		<td colspan = 2>
		<a href="member_list.net">리스트로 돌아가기</a></td>
	</tr>		   	
    </table>
    
    
    


</body>
</html>