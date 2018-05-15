package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardBean;
import net.board.db.BoardDAO;

public class BoardDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
		
		ActionForward forward = new ActionForward();
		int num = Integer.parseInt(request.getParameter("num"));
		BoardDAO boarddao = new BoardDAO();
		
		
		
		
		//글의 내용을 DAO 에서 읽은 후 얻은 결과를 boarddata 객체에 저장합니다
		BoardBean boarddata = boarddao.getDetail(num);
		
		//내용을 확인할 글의 조회수를 증가합니다.
		boarddao.setReadCountUpdate(num);

		
		//DAO에서 글의 내용을 읽지 못했을때 null 을 반환합니다.
		if(boarddata == null) {
			System.out.println("상세보기 실패");
			return null;
			
		}
		System.out.println("상세보기 성공");
		
			
		
		
		
		forward.setPath("/board/qna_board_view.jsp");
		forward.setRedirect(false);
		request.setAttribute("detail", boarddata);
		
		
		
		
		return forward;
	}

}
