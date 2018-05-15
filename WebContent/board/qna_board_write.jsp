<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<link href="css/form.css" rel="stylesheet">
</head>
<body>
<%@ include file="/board/header.jsp" %>
<form action="./BoardAddAction.bo" method="post"
	enctype="multipart/form-data" name="boardform">
	<table>
		<tr class="center">
			<th colspan="2">MVC 게시판-write 페이지</th>
		</tr>
		<tr>
			<td><div>글쓴이</div></td>
			<td>
				<input name="BOARD_NAME" id="board_name" readOnly
				type="text" size="10" maxlength="10" value="${id}">				<!--id: 세션아이디를 불러오는거와 똑같다.  -->
			</td>
		</tr>
		<tr>
			<td><div>비밀번호</div></td>
			<td>
				<input name="BOARD_PASS" id="board_pass" type="password" size="10" maxlength="30"
												value="">
			</td>
		</tr>
		
		<tr>
			<td><div>제목</div></td>
			<td>
			<input name="BOARD_SUBJECT" id="board_subject"
							type="text" size="50" maxlength="100"
									value="">
				</td>
		</tr>
		
		<tr>
            <td>
               <div>내 용</div>
            </td>
            <td>
               <input name="BOARD_CONTENT" id="board_content" type="text" size="10"
               maxlength="500" value="">
            </td>
         </tr>
		
		<tr>
			<td><div>파일 첨부</div></td>
			<td>
				<input type="file" id="upfile" name="BOARD_FILE" value="attach.png">
			</td>
		</tr>
		
		<tr class="center">
			<td colspan="2" class="h30 lime">
				<input type=submit value="등록">
				<input type=reset value="최소">
		</tr>
	</table>	
	</form>
</body>
</html>