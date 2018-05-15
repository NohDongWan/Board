<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/board/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<link href="css/form.css" rel="stylesheet">
</head>
<body>

	<form action="./BoardDeleteAction.bo" method="post" name=boardDelete>
		<table border=1>
			<tr>
				<td>삭제하기<input type="password" name="pass" value=""> <input
					type="hidden" name="BOARD_NUM" value="${param.num}"></td>
			</tr>
			
			<tr>
				<td colspan = 2 class="h30 center lime">
				<input type=submit value="삭제">
				<input type=button value="취소" onClick="history.go(-1)">  
				</td>
				
			</tr>
			
			
			
		</table>
		
		
	</form>
</body>
</html>
