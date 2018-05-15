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

<div id="header">
	<c:if test="${empty id }">
		<script>
			location.href="loginForm.net"
		</script>
	</c:if>
	
	<c:if test="${!empty id}">
	<c:if test="${id=='admin'}">
		<span>관리자 ${id} 님 환영합니다.</span>
		<a href="member_list.net">관리자모드 접속(회원 목록 보기)</a>
		<a href="BoardList.bo">(게시판)</a>
	</c:if>
	<c:if test="${id!= 'admin'}">
		<span>일반회원 ${id} 님 환영합니다.</span>
	</c:if>
	
	</c:if>

</div>



<%-- 														내가한거 위에건 선생님이한거
<c:choose>
	<c:when test="${id=='admin'}">
	<div>관리자 ${id} 님 환영합니다</div> 
	</c:when>
	
	<c:otherwise>
		<div>일반회원 ${id} 님 환영합니다</div> 
	</c:otherwise>
</c:choose> --%>

(<a href = "updateForm.net">정보수정</a>)
(<a href = "logOut.net">로그아웃</a>)
</body>
</html>