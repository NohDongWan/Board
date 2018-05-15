<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<script src = "http://code.jquery.com/jquery-3.3.1.js"></script>
 <script>								/*  일부로 폼아래다하는거다 폼먼저읽고 이게 입력되라고*/
 
 $(document).ready(function(){
	$('form').submit(function(){
		if(!$.isNumeric($("input[name='age']").val())){
			alert("나이는 숫자를 입력하세요");
			return false;
		}
	})
 })
	</script>

</head>
<body>

<form name = joinform action = joinProcess.net
		method = "post">
				<h1>회원가입 페이지</h1>
	<hr>
		<b>아이디</b>
		<input type = text name = id placeholder="Enter id" required="required"><br>
		<b>비밀번호</b> <input type = password id=pass name = pass placeholder = password required="required"><br>
		<b>이름</b><input type = text name = name placeholder="Enter name" required="required"><br>
		<b>나이</b><input type = text name = age placeholder = "Enter age"required="required"><br>
		<b>성별</b><div>
				<input type = radio name = gender value = 남 checked><span>남자</span>
				<input type = radio name = gender value = 여 ><span>여자</span>
				</div>
				
		<b>이메일 주소</b><input type = email name = email placeholder="Enter email" required="required"><br>
		<div class = clearfix>
		<button type = submit class = submitbtn>회원가입</button>
		<button type = reset class = canclebtn>다시작성</button>
		</div>
		</form>

</body>
</html>