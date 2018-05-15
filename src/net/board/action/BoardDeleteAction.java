package net.board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardDAO;

public class BoardDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = new ActionForward();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		
		
		int num = Integer.parseInt(request.getParameter("BOARD_NUM"));
		
		
		
		
		BoardDAO boarddao = new BoardDAO();

		
		boolean result = false;
		boolean usercheck =
			boarddao.isBoardWriter(num, request.getParameter("pass"));
		
		if(usercheck == false) {
			
			response.setContentType("text/html;charset=UTF-8");
			out.println("<script>");
			out.println("alert('비밀번호가 다릅니다')");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
		
		
		result = boarddao.boardDelete(num);
		
		if(result == false)
		{
			System.out.println("삭제실패했니다!");
			out.println("<script>");
			out.println("alert('삭제 실패!');");
			out.println("history.back();");
			out.println("<script>");
			return null;
		}
		
		System.out.println("삭제완료");
		
		forward.setRedirect(false);
		forward.setPath("/BoardList.bo");
		return forward;
	}

}
