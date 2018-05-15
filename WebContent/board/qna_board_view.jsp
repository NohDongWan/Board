<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<link href="css/form.css" rel="stylesheet">
</head>
<body>
	<%@ include file="/board/header.jsp"%>


	<table border=1>
		<tr>
			<th colspan=2>MVC 게시판 view 페이지</th>
		</tr>

		<tr>
			<td>글쓴이</td>
			<td>${detail.BOARD_NAME}</td>
		</tr>

		<tr>
			<td>제목</td>
			<td>${detail.BOARD_SUBJECT}</td>
		</tr>

		<tr>
			<td>내용</td>
			<td>${detail.BOARD_CONTENT}</td>
		</tr>

		<tr>
			<td>파일</td>
			<c:if test="${!empty detail.BOARD_FILE}">
			<td>
			<img src="image/down.png" width="10px">
			<a href="./BoardFileDown.bo?filename=${detail.BOARD_FILE}">
					${detail.BOARD_FILE}</a>	
			</td>
			</c:if>
		</tr>
		
		
		<tr>
		
		<td colspan = 2 class="center">	
				<a href="./BoardReplyView.bo?num=${detail.BOARD_NUM}">
					답변</a>&nbsp;&nbsp;
				<c:if test="${detail.BOARD_NAME == id || id == 'admin' }">
					<a href="./BoardModifyView.bo?num=${detail.BOARD_NUM}">
					수정</a> &nbsp;&nbsp;
				
				<a href="./BoardDelete.bo?num=${detail.BOARD_NUM}">
				삭제</a> &nbsp;&nbsp;
				</c:if>
				
				<a href="./BoardList.bo">목록</a>
				
			
		</td>
			
		</tr>

	</table>
<!--  게시판 수정 -->
</body>
</html>