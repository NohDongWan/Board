package net.member.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("*.net")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String RequestURI = request.getRequestURI(); // 1. uri 값얻어오고
		System.out.println("RequestURI = " + RequestURI);

		// getContextPath() : 컨텍스트 경로가 반환됩니다.
		// contextPath는 "/0420_jsp"가 반환됩니다.
		String contextPath = request.getContextPath(); // 2. 프로젝트 이름 반환하고
		System.out.println("contextPath = " + contextPath);

		// RequestURI 에서 컨텍스트 경로 길이 값의 인덱스 위치의 문자부터
		// 마지막 위치 문자까지 추출합니다.
		// command 는 "/login.net" 반환됩니다.
		String command = RequestURI.substring(contextPath.length()); // 3. uri 전체에서 0420_jsp 만 짜르겠다.
		System.out.println("command = " + command);

		// 초기화
		ActionForward forward = null;
		Action action = null; // action 초기값 해주고

		if (command.equals("/loginForm.net")) { // 1. 이걸로들어오면
			forward = new ActionForward();
			forward.setRedirect(false); // 리다이렉트 안쓸꺼다 포워드로 주소변경 없이갈꺼다.라는뜻
			forward.setPath("/member/loginForm.jsp"); // 리다이렉트로 이주소를썻다면 주소값이 이값으로 변경된다.

		} else if (command.equals("/loginProcess.net")) { // 이주소로 또 들어오면
			action = new LoginProcessAction(); //
			try {
				forward = action.execute(request, response); // loginProcessAction 에서 포워드값을 리턴받아온다.
																// 그리고 맨아래 if 문으로 내려간뒤
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/member_list.net")) {
			action = new ListAction();
			try {
				forward = action.execute(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/member_info.net")) {
			action = new Member_infoAction();
			try {
				forward = action.execute(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/member_delete.net")) {
			action = new Member_deleteAction();
			try {
				forward = action.execute(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/updateForm.net")) {
			action = new Member_updateAction();
			try {
				forward = new ActionForward();
				forward = action.execute(request, response);
				forward.setRedirect(false); // 주소변경없이 jsp 페이지 이동
				forward.setPath("/member/updateForm.jsp");

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/updateProcess.net")) {
			action = new UpdateProcessAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/logOut.net")) {
			action = new LogoutAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		else if (command.equals("/joinForm.net")) {
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("/member/joinForm.jsp");

		} else if (command.equals("/joinProcess.net")) {
			action = new JoinProcessAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (command.equals("/main.net")) { // 1. 이걸로들어오면
			forward = new ActionForward();
			forward.setRedirect(false); // 주소변경없이 jsp 페이지 이동
			forward.setPath("/member/main.jsp");
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

	/*
	 * RequestDispatcher dispatcher =
	 * request.getRequestDispatcher("/ch12_db/_8.member/ex1/loginForm.jsp");
	 * dispatcher.forward(request, response);
	 */

	public FrontController() {
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
