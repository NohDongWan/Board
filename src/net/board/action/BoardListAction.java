package net.board.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardBean;
import net.board.db.BoardDAO;

public class BoardListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BoardDAO boarddao = new BoardDAO();
		List<BoardBean> boardlist = new ArrayList<BoardBean>(); // ����Ʈ�� ��Ƽ��ò���.

		int page = 1;		//���� �������� ��				������
		int limit = 10;		// ���������� ������ ����� ���Ǽ�			���

		
		
		if (request.getParameter("page") != null) {												//���� �������ذŰ��� �����ִ� ���̾���.	
			page = Integer.parseInt(request.getParameter("page"));
		}
		System.out.println("�Ѿ�� ������ = " + page);

		
		
		// �� ����Ʈ ���� �޾ƿɴϴ�. 
		int listcount = boarddao.getListCount();										//�Խñ��� ��		

		
		boardlist = boarddao.getBoardList(page, limit);			// �ڰԽ���ȭ���� �����ֱ����� ����Ʈ�� �޾ƿɴϴ�.		����Ʈ�� �޾ƿɴϴ�. //�Խñ���ü�� �޾ƿ°�

		/*
		 * ���� ��� �� ���������� �����ִ� ����Ʈ�� ���� 10 ���� ��� ��1) DB�� ����� �� ����Ʈ�� ���� 0 �̸� �� ���������� 1~10
		 * ������ ������������ 1������ ��1) DB�� ����� �� ����Ʈ�� ���� 0 �̸� �� ���������� 11~20 ������ ������������ 2������ ��1)
		 * DB�� ����� �� ����Ʈ�� ���� 0 �̸� �� ���������� 21~30 ������ ������������ 3������
		 */

		int maxpage = (listcount + limit-1) / limit; //					�Խñ���27  + 9= 36 / 10  4 	
		System.out.println("�� �������� = " + maxpage);

		/*
		 * startpage : ���� ������ �׷쿡�� �� ó���� ǥ�õ� ���������� �ǹ��մϴ�. ([1],[11],[21] �� ..) ������ ��������
		 * 30���� ��� [1][2][3]...[30] ���� ��ǥ���ϱ⿡�� �ʹ� ���� ������ ���v �� ���������� 10 ������ �������� �̵��� �� �ְ�
		 * ǥ���մϴ�. ��) ��Ű�� �׷쿡 [1]2[3][4][5][6][7][8][9][10] ������ �׷��� ���� �������� startpage ��
		 * ������ �������� endpage �� ���մϴ�
		 * 
		 * ���� 1~10 �������� ������ ��Ÿ������ �������׷��� [1][2][3]..[10] 11~20 �������� ������ ��Ÿ�� ���� ������ �׷���
		 * [11][12]..[20] ����ǥ�õ˴ϴ�.
		 */
		int startpage = ((page - 1) / 10) * 10 + 1;							//
		System.out.println("���� �������� ������ ���� �������� = " + startpage);

		// endpage : ���� ������ �׷쿡�� ������ ������ ��������([10],[20],[30]
		int endpage = startpage + 10 - 1;
		System.out.println("���� �������� ������ ������ ������ �� " + endpage);

		/*
		 * ������ �׷��� ������ ������ ���� �ִ� ������ ���Դϴ� ���� ������ ������ �׷��� [21]~[30] �� ���
		 * ����������(startpage=21) �� ������������(endpage=30) ������ �ִ� ������(maxpage)�� 25��� [21] ~[25]
		 * ������ ǥ�õǵ����մϴ�.
		 */

		if (endpage > maxpage)
			endpage = maxpage;
		request.setAttribute("page", page); // ���� �������� ��
		request.setAttribute("maxpage", maxpage); // �ִ� �������� ��

		// ���� �������� ǥ���� ù ������ ��
		request.setAttribute("startpage", startpage);
		request.setAttribute("endpage", endpage);
		// �ش� �������� �� ����� ���� �ִ� ����Ʈ
		request.setAttribute("board", boardlist);		
		
		request.setAttribute("listcount", listcount);		//�۰���
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);

		// �� ��� �������� �̵��ϱ� ���� ��θ� �����Ѵ�.
		forward.setPath("./board/qna_board_list.jsp");
		return forward; // BoardFrontController.java �� ���ϵ˴ϴ�.
	}
}
