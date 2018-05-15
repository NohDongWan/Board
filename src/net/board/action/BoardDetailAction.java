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
		
		
		
		
		//���� ������ DAO ���� ���� �� ���� ����� boarddata ��ü�� �����մϴ�
		BoardBean boarddata = boarddao.getDetail(num);
		
		//������ Ȯ���� ���� ��ȸ���� �����մϴ�.
		boarddao.setReadCountUpdate(num);

		
		//DAO���� ���� ������ ���� �������� null �� ��ȯ�մϴ�.
		if(boarddata == null) {
			System.out.println("�󼼺��� ����");
			return null;
			
		}
		System.out.println("�󼼺��� ����");
		
			
		
		
		
		forward.setPath("/board/qna_board_view.jsp");
		forward.setRedirect(false);
		request.setAttribute("detail", boarddata);
		
		
		
		
		return forward;
	}

}
