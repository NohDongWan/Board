package net.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
			/*ActionForward forward = new ActionForward();*/
			
			HttpSession session = request.getSession();
			session.invalidate();
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그아웃되었습니다');");
			out.println("location.href = 'loginForm.net';");
			out.println("</script>");
			
		/*	
		   forward.setPath("loginForm.net);							alert 없는 버전 forwar
		   forward.setRedirect(true);	
		   return forward;*/
			return null;
	}

}
