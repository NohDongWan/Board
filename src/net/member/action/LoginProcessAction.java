
//나를 부른건 FrontController다.

package net.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.member.db.MemberDAO;
public class LoginProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, // execute 는 재정의
			HttpServletResponse response) throws Exception {

		ActionForward forward = new ActionForward();
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");

		MemberDAO mdao = new MemberDAO();
		int result = mdao.isId(id, pass);
		System.out.println("결과는 " + result);		//로그인성공후 result 1 들어옴
		if (result == 1) {								//로그인이 성공했다면
			HttpSession session = request.getSession();		//세션을 쓰기위한 
			// 로그인 성공
			session.setAttribute("id", id);					//세션값을 담아주고
			forward.setRedirect(false);						//리다이렉트로 안할꺼다.
			forward.setPath("BoardList.bo");		//	★★★	제일핵심 바뀐곳
			return forward;
		} else {
			String message = "";
			if (result == -1)
				message = "아이디가 존재하지 않습니다.";
			else
				message = "비밀번호가 일치하지 않습니다.";

			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('" + message + "');");
			out.println("location.href='./loginForm.net';");
			out.println("</script>");
			out.close();
			return null;
		}
	}
}
