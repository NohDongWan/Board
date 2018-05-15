package net.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.member.db.Member;
import net.member.db.MemberDAO;
public class Member_deleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
			ActionForward forward = new ActionForward();
			request.setCharacterEncoding("UTF-8");
			String id = request.getParameter("id");
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			if(id.equals("admin")) {
				out.print("<script>");
				out.print("alert('관리자는 삭제하실 수 없습니다.');");
				out.println("history.back();");
				out.print("</script>");
				out.close();
				return null;
			}else {
			MemberDAO mdao = new MemberDAO();		//데이터베이스 접근하니깐 생성
			mdao.delete(id);
			forward.setPath("member_list.net");
			forward.setRedirect(false);
			}
			return forward;
				
	}
			
		

}
