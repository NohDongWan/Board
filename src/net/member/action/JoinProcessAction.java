package net.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.member.db.Member;
import net.member.db.MemberDAO;


public class JoinProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
				
		/*ActionForward forward = new ActionForward();*/
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		String name = request.getParameter("name");
		int age = Integer.parseInt(request.getParameter("age"));
		String gender = request.getParameter("gender");
		String email = request.getParameter("email");
		
		MemberDAO mdao = new MemberDAO();
		Member m = new Member();
		m.setId(id);
		m.setAge(age);
		m.setEmail(email);
		m.setGender(gender);
		m.setName(name);
		m.setPassword(pass);
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		int result = mdao.insert(m);
		
		if(result == 1) {
			out.println("<script>");
			out.println("alert('회원가입이 완료되었습니다');");
			out.println("location.href = 'loginForm.net';");
			out.println("</script>");
		}
		else {
			out.println("<script>");
			out.println("alert('아이디 중복입니다.');");
			out.println("location.href = 'loginForm.net';");
			out.println("</script>");
		}
		return null;
	}

}
