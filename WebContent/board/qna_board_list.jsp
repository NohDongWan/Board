<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
<head>
<title>MVC 게시판</title>
<link href="css/list.css" rel="stylesheet">
</head>
<body>
	<%@ include file="/board/header.jsp"%>
	<!-- 게시판 리스트 -->

	<table border=1>


		<!--  레코드가 없으면 -->
		<c:if test="${listcount == 0 }">
			<tr>
				<td colspan="4">MVC 게시판</td>
				<td style="text-align: right"><font size=2>등록된 글이 없습니다.</font>
				</td>
			</tr>
		</c:if>

		<!-- 레코드가 있으면 -->

		<tr>
			<th colspan="4">MVC 게시판</th>
			<th colspan=2><font size=2>글 개수 : ${listcount}</font></th>
		</tr>
		<tr>
			<th width="8%"><div>번호</div></th>
			<th width="50%"><div>제목</div></th>
			<th width="14%"><div>작성자</div></th>
			<th width="17%"><div>날짜</div></th>
			<th width="11%"><div>조회수</div></th>
		</tr>
		
		<c:set var="num" value="${listcount-(page-1)*10 }"></c:set><%-- 삭제할때 제약이있기때문에 --%>
		<c:forEach var="list" items="${board}">
		<tr>
			<td>
				<c:out value="${num}"/>	<%-- num 출력 --%>		
				<c:set var="num" value="${num-1}"/>					
			</td>
			
			
			<td>
			<div>
				<%--답변글 제목앞에 여백 처리 부분
					BOARD_RE_LEV, BOARD_RE_LEV, BOARD_NUM,
					BOARD_SUBJECT, BOARD_NAME, BOARD_DATE
					BOARD_READCOUNT : property 이름
				 --%>
		
																						
				<c:if test="${list.BOARD_RE_LEV != 0 }">							 <!-- 답글인 경우 -->		<%-- 답글이 밑에달리는데 공백을 주기위해 사용하는거다 --%>
					<c:forEach var="a" begin="0" end="${list.BOARD_RE_LEV*2}" step="1">
				 	&nbsp;
				 	</c:forEach>
				 	★
				</c:if>
				
				<c:if test="${list.BOARD_RE_LEV ==0 }">
					<!-- 원문인 경우 -->
			&nbsp; 
		</c:if>
		
		<a href="./BoardDetailAction.bo?num=${list.BOARD_NUM}">
					${list.BOARD_SUBJECT} </a>
			</div>
			
			
			</td>
			
			
			
				
				
				<td><div>${list.BOARD_NAME }</div></td>
				<td><div>${list.BOARD_DATE }</div></td>
				<td><div>${list.BOARD_READCOUNT }</div></td>

			</tr>
		</c:forEach>
		
		
		
		<!-- 페이지 처리 !!!! -->
		
		<tr class="h30 lime center btn">
			<td colspan=5>
				<c:if test="${page <=1 }">									<!--  내가보여주는페이지가 1페이지면  -->
					이전&nbsp;
				</c:if>
				<c:if test="${page > 1 }">												<!-- 현재나는 2페이지다.  -->
					<a href="./BoardList.bo?page=${page-1}">이전</a>&nbsp;					<!--  이전눌렀을 때 현재페이지 - 1 할 수 있게 정의 -->
				</c:if>
				
				<!--  숫자 누르면 가는곳 -->
				<c:forEach var="a" begin="${startpage}" end="${endpage}">				<!--  1부터 10 a 의변수에넣고 -->
					<c:if test="${a==page }">
						${a}
					</c:if>
					<c:if test="${a!=page }">
						<a href="./BoardList.bo?page=${a}">${a}</a>&nbsp;			<!--  내가누른 그페이지로 이동하게 해준다. -->
					</c:if>
				</c:forEach>
				<!--  숫자 누르면 가는곳 -->		
				
				<c:if test="${page >= maxpage }">
					&nbsp;다음
				</c:if>
				<c:if test="${page < maxpage }">
					<a href="./BoardList.bo?page=${page+1 }">다음</a>&nbsp;
					</c:if>
			</td>
		</tr>
		
		
		
		
		
		
		
		
		
		
		
		<tr>
			<td colspan="5" style="text-align: right"><a
				href="./BoardWrite.bo">[글쓰기]</a></td>
		</tr>
	</table>
</body>
</html>