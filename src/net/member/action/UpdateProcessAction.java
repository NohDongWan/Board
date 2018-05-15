package net.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.member.db.Member;
import net.member.db.MemberDAO;
public class UpdateProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
			ActionForward forward = new ActionForward();
			request.setCharacterEncoding("UTF-8");
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			int age = Integer.parseInt(request.getParameter("age"));
			String gender = request.getParameter("gender");
			String email = request.getParameter("email");
			
			Member m = new Member();
			m.setAge(age);
			m.setEmail(email);
			m.setGender(gender);
			m.setId(id);
			m.setName(name);
			MemberDAO mdao = new MemberDAO();
			int result = mdao.update(m);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			if(result == 1) {
				out.println("alert('수정 되었습니다');");
				out.println("location.href='main.net';");
			}else
			{
				out.println("histroy.back();");
			}
			out.println("</script>");
			out.close();
		return null;
	}

}
