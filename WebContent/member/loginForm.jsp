<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<script src = "http://code.jquery.com/jquery-3.3.1.js"></script>


<script>
	$(document).ready(function(){
		$('.join').click(function(){
		location.href = "joinForm.net";			
		})
	})

</script>
</head>
<body>
	<form name = loginform action = "loginProcess.net"
		method = post><h1>로그인</h1>
		<hr>
		
	<b>아이디</b> : <input type ="text" name = id placeholder="Enter id"><br>
	<b>Password</b> <input type = text name = pass placeholder="Enter password" required><br>
	<div class ="clearfix">
		<button type = submit class = submitbtn>로그인</button>
		<button type = button class = join>회원가입</button>
	</div>
	</form>
</body>
</html>