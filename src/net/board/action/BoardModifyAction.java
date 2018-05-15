package net.board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardBean;
import net.board.db.BoardDAO;

public class BoardModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
			//�׼ǿ��� �ؾ��Ұ�  ��й�ȣ�� �޾ƿͼ� ��й�ȣ�� ������ �ٲٰ������
		
		ActionForward forward = new ActionForward();
		request.setCharacterEncoding("UTF-8");
		boolean result = false;		
		
		
		//���޹��� �Ķ���� num ������ �����մϴ�.
		int num=Integer.parseInt(request.getParameter("BOARD_NUM"));
		
		BoardDAO boarddao = new BoardDAO();
		BoardBean boarddata = new BoardBean();
		
		//�۾��� ���� Ȯ���ϱ� ��������� ��й�ȣ�� �Է��� ��й�ȣ�� ���մϴ�.
		boolean usercheck= 
		boarddao.isBoardWriter(num,request.getParameter("BOARD_PASS"));
		
		//��й�ȣ�� �ٸ����
			if(usercheck == false) {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('��й�ȣ�� �ٸ��ϴ�')");
				out.println("history.back();");
				out.println("</script>");
				out.close();
			}
			
			//��й�ȣ�� ��ġ�ϴ� ���
			//���� ������ �����մϴ�.
				boarddata.setBOARD_NUM(num);
				boarddata.setBOARD_SUBJECT(request.getParameter("BOARD_SUBJECT"));
				boarddata.setBOARD_CONTENT(request.getParameter("BOARD_CONTENT"));
				
				//DAO ���� ���� �޼��� ȣ���Ͽ� �����մϴ�.
				result = boarddao.boardModify(boarddata);
				//������ ������ ���
				if(result == false) {
					System.out.println("�Խ��� ���� ����");
					return null;
			}
				//���� ������ ���
				System.out.println("�Խ��� ���� �Ϸ�");
				
				forward.setRedirect(true);
				//���� �� �� ���� �����ֱ� ���� �۳��� ���� �������� �̵��ϱ� ���� ��θ� ����
				forward.setPath("./BoardDetailAction.bo?num="+boarddata.getBOARD_NUM());
				
		return forward;
	}

}
