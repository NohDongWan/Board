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
<script src = "http://code.jquery.com/jquery-3.3.1.js"></script>
 <script>								/*  일부로 폼아래다하는거다 폼먼저읽고 이게 입력되라고*/
 
 $(document).ready(function(){
	var pandan = "${m.gender}";
	if(pandan == '남'){
		$('input:radio').eq(0).attr('checked','checked');
	}else{
		$('input:radio').eq(1).attr('checked','checked');
	}
	$(".canclebtn").click(function(){
		location.href = "main.net";
	});
	$('form').submit(function(){
		if(!$.isNumeric($("input[name='age']").val())){
			alert("나이는 숫자를 입력하세요");
			return false;
		}
	})
 })
	</script>
<form name = updateform action = "updateProcess.net"
		method = "post">
				<h1>회원수정 페이지</h1>
				<c:set var = "m" value ="${memberinfo}"/>
	<hr>
		<b>아이디</b>
		<input type = text name = id value ="${m.id}" required="required" readonly><br>
		<b>비밀번호</b> <input type = password value="${m.password}" name = pass  required="required" readonly><br>
		<b>이름</b><input type = text value ="${m.name}" name = name  required="required"><br>
		<b>나이</b><input type = text value ="${m.age}" name = age required="required"><br>
		<b>성별</b><div>
				<input type = radio class = gender name = gender value = 남 ><span>남자</span>
				<input type = radio class = gender name = gender value = 여 ><span>여자</span>
				</div> 
		<b>이메일 주소</b><input type = text value = "${m.email}" name = email  required="required"><br>
		<div class = clearfix>
		<button type = submit class = submitbtn>정보 수정</button>
		<button type = reset class = canclebtn>취소</button>
		</div>
		</form>
		
	<%-- <script>								/*  일부로 폼아래다하는거다 폼먼저읽고 이게 입력되라고*/	
		var pandan = '<%= gender%>';
	if(pandan == '남'){
		$('input:radio').eq(0).attr('checked','checked');
	}else{
		$('input:radio').eq(1).attr('checked','checked');
	}
		
	</script> --%>
</body>
</html>