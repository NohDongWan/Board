package net.board.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("*.bo")
public class BoardFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String RequestURI = request.getRequestURI(); // 1. uri ��������
		System.out.println("RequestURI = " + RequestURI);

		// getContextPath() : ���ؽ�Ʈ ��ΰ� ��ȯ�˴ϴ�.
		// contextPath�� "/0420_jsp"�� ��ȯ�˴ϴ�.
		String contextPath = request.getContextPath(); // 2. ������Ʈ �̸� ��ȯ�ϰ�					==> uri �� ¥�������� 
		System.out.println("contextPath = " + contextPath);

		// RequestURI ���� ���ؽ�Ʈ ��� ���� ���� �ε��� ��ġ�� ���ں���
		// ������ ��ġ ���ڱ��� �����մϴ�.
		// command �� "/login.net" ��ȯ�˴ϴ�.
		String command = RequestURI.substring(contextPath.length()); // 3. uri ��ü���� 0420_jsp �� ¥���ڴ�.
		System.out.println("command = " + command);

		// �ʱ�ȭ
		ActionForward forward = null;
		Action action = null; // action �ʱⰪ ���ְ�

		if (command.equals("/BoardList.bo")) {
			action = new BoardListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/BoardWrite.bo")) {
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("./board/qna_board_write.jsp");
		} 
		
		else if (command.equals("/BoardAddAction.bo")) {
			action = new BoardAddAction();
			try {
				forward=action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		else if (command.equals("/BoardDetailAction.bo")) {
			action = new BoardDetailAction();
			try {
				forward=action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		else if (command.equals("/BoardReplyView.bo")) {
			action = new BoardReplyView();
			try {
				forward=action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		else if (command.equals("/BoardReplyAction.bo")) {
			action = new BoardReplyAction();
			try {
				forward=action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		else if (command.equals("/BoardModifyView.bo")) {
			action = new BoardModifyView();
			try {
				forward=action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		else if (command.equals("/BoardModfyAction.bo")) {
			action = new BoardModifyAction();
			try {
				forward=action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		else if (command.equals("/BoardDelete.bo")) {
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("./board/qna_board_delete.jsp");
		}
		
		else if (command.equals("/BoardDeleteAction.bo")) {
			action = new BoardDeleteAction();
			try {
				forward=action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		else if (command.equals("/BoardFileDown.bo")) {
			action = new BoardFileDownAction();
			try {
				forward=action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		if (forward != null) { // ���Ϲ޾ƿ� ������ ���� ���̾ƴ϶��
			if (forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}
	}

	public BoardFrontController() {
		super();
	}

	// doprocess(request,response) �޼��带 �����Ͽ� ��û�� get ����̵�
	// post ������� ���۵Ǿ�

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

}
