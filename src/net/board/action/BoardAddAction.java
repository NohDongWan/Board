package net.board.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.board.db.BoardBean;
import net.board.db.BoardDAO;

public class BoardAddAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		BoardDAO boarddao = new BoardDAO();
		BoardBean boarddata = new BoardBean();
		ActionForward forward = new ActionForward();

		String realFolder = "";

		// WebContent �Ʒ��� �� ���� �����ϼ���
		String saveFolder = "boardupload";

		int size = 5 * 1024 * 1024; // ���ε��� ������ �ִ� ������ 5MB�� ����

		ServletContext sc = request.getServletContext();
		realFolder = sc.getRealPath(saveFolder);

		System.out.println("realFolder=" + realFolder);
		boolean result = false;

		try {

			/*
			 * ���ε带 ����ϴ� �κ��Դϴ�. -ù ��° ���� request : ������ ������ ���� ��� ���� request ��ü�� �������ݴϴ�. -��
			 * ��° ���� uploadPath : ���ε�� ������ ��ġ �Դϴ�. -�� ��° ���� size : ���ε� �� ũ���̸� ���� ũ�⺸�� ũ��
			 * Excetption �߻��մϴ�. -�� ��° ���� "euc-kr" : ���� �̸��� �ѱ��� ��� ó���ϴ� �κ��Դϴ�. -�ټ� ��° ���� :
			 * �Ȱ��� ������ ���ε� �� ��� �ߺ����� �ʵ��� �ڵ����� �����̸��� ��ȯ���ִ� ����� �մϴ�.
			 */
			MultipartRequest multi = new MultipartRequest(request, realFolder, size, "UTF-8",
					new DefaultFileRenamePolicy());

			boarddata.setBOARD_NAME(multi.getParameter("BOARD_NAME")); // �ۼ���
			boarddata.setBOARD_PASS(multi.getParameter("BOARD_PASS")); // ��й�ȣ
			boarddata.setBOARD_SUBJECT(multi.getParameter("BOARD_SUBJECT")); // ����
			boarddata.setBOARD_CONTENT(multi.getParameter("BOARD_CONTENT")); // ����

			// ���ε��� ���ϸ��� ���ε��� ������ ��ü ��ο��� ���� �̸��� �����մϴ�.
			boarddata.setBOARD_FILE(multi.getFilesystemName((String) multi.getFileNames().nextElement())); // ÷������ ��

			// �� ��� ó���� ���� DAO �� broadInsert() �޼��带 ȣ���մϴ�.
			// �� ��� ������ �Է��� ������ ����Ǿ� �ִ� boarddata ��ü�� �����մϴ�.
			result = boarddao.boardInsert(boarddata);

			if (result == false) {
				System.out.println("�Խ��� ��� ����");
			} else {
				System.out.println("�Խ��� ��� ����");
			}

			// �� ����� �Ϸ�Ǹ� �� ����� �ܼ��� �����ֱ⸸ �� ���̹Ƿ�
			// Redircet ���θ� true �� �����մϴ�.
			forward.setRedirect(true);
			forward.setPath("./BoardList.bo");
			return forward;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
