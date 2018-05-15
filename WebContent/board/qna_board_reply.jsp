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
<form action="./BoardReplyAction.bo" method="post" name="boardf">

	<input type="hidden" name="BOARD_NUM"
							value="${boarddata.BOARD_NUM }">
	<input type="hidden" name="BOARD_RE_REF"
							value="${boarddata.BOARD_RE_REF }">
	<input type="hidden" name="BOARD_RE_LEV"
							value="${boarddata.BOARD_RE_LEV }">
	<input type="hidden" name="BOARD_RE_SEQ"
							value="${boarddata.BOARD_RE_SEQ}">	
							
	
	<table>
		<tr class="center">
			<th colspan="2">MVC 게시판-write 페이지</th>
		</tr>
		<tr>
			<td><div>글쓴이</div></td>
			<td>
				<input name="BOARD_NAME" id="board_name" type="text" value="${id}">
			</td>
		</tr>
		
		<tr>
			<td><div>제목</div></td>
			<td>
			<input name="BOARD_SUBJECT" id="board_subject"
							type="text" size="50" maxlength="100"
									value="Re: ${boarddata.BOARD_SUBJECT}">
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
			<td><div>비밀번호</div></td>
			<td>
				<input type="password" id="board_pass" name="BOARD_PASS">
			</td>
		</tr>
		
		<tr class="center">
			<td colspan="2" class="h30 lime">
				<input type=submit value="등록">
				<input type=reset value="취소" onClick="history.go(-1);">
		</tr>
	</table>	
	</form>
</body>
</html>