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

		String RequestURI = request.getRequestURI(); // 1. uri 값얻어오고
		System.out.println("RequestURI = " + RequestURI);

		// getContextPath() : 컨텍스트 경로가 반환됩니다.
		// contextPath는 "/0420_jsp"가 반환됩니다.
		String contextPath = request.getContextPath(); // 2. 프로젝트 이름 반환하고					==> uri 값 짜르기위해 
		System.out.println("contextPath = " + contextPath);

		// RequestURI 에서 컨텍스트 경로 길이 값의 인덱스 위치의 문자부터
		// 마지막 위치 문자까지 추출합니다.
		// command 는 "/login.net" 반환됩니다.
		String command = RequestURI.substring(contextPath.length()); // 3. uri 전체에서 0420_jsp 만 짜르겠다.
		System.out.println("command = " + command);

		// 초기화
		ActionForward forward = null;
		Action action = null; // action 초기값 해주고

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
		
		
		
		
		if (forward != null) { // 리턴받아온 포워드 값이 널이아니라면
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

	// doprocess(request,response) 메서드를 구현하여 요청이 get 방식이든
	// post 방식으로 전송되어

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

}
