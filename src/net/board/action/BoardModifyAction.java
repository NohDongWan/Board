package net.board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardBean;
import net.board.db.BoardDAO;

public class BoardModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
			//액션에서 해야할게  비밀번호를 받아와서 비밀번호가 맞으면 바꾸게해줘라
		
		ActionForward forward = new ActionForward();
		request.setCharacterEncoding("UTF-8");
		boolean result = false;		
		
		
		//전달받은 파라미터 num 변수에 저장합니다.
		int num=Integer.parseInt(request.getParameter("BOARD_NUM"));
		
		BoardDAO boarddao = new BoardDAO();
		BoardBean boarddata = new BoardBean();
		
		//글쓴이 인지 확인하기 위해저장된 비밀번호와 입력한 비밀번호를 비교합니다.
		boolean usercheck= 
		boarddao.isBoardWriter(num,request.getParameter("BOARD_PASS"));
		
		//비밀번호가 다른경우
			if(usercheck == false) {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('비밀번호가 다릅니다')");
				out.println("history.back();");
				out.println("</script>");
				out.close();
			}
			
			//비밀번호가 일치하는 경우
			//수정 내용을 설정합니다.
				boarddata.setBOARD_NUM(num);
				boarddata.setBOARD_SUBJECT(request.getParameter("BOARD_SUBJECT"));
				boarddata.setBOARD_CONTENT(request.getParameter("BOARD_CONTENT"));
				
				//DAO 에서 수정 메서드 호출하여 수정합니다.
				result = boarddao.boardModify(boarddata);
				//수정에 실패한 경우
				if(result == false) {
					System.out.println("게시판 수정 실패");
					return null;
			}
				//수정 성공한 경우
				System.out.println("게시판 수정 완료");
				
				forward.setRedirect(true);
				//수정 한 글 내용 보여주기 위해 글내용 보기 페이지로 이동하기 위해 경로를 설정
				forward.setPath("./BoardDetailAction.bo?num="+boarddata.getBOARD_NUM());
				
		return forward;
	}

}
