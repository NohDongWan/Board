/*
 	보통 액션 페이지는 포워드 설정해주고		: 리다이렉트로 쓸지 포워드로 쓸지 결정해야하기때문에... 
 	DAO 클래스 열어줘서 함수 써주고	
 	alert 써주고 조건을 써준다 
 */

package net.member.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.member.db.Member;
import net.member.db.MemberDAO;
public class ListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
			ActionForward forward = new ActionForward();	//포워드 값을 리턴해주기위해 써준다.
			MemberDAO mdao = new MemberDAO();				//디비설정을위해 써준다
			List<Member> list = mdao.getList();				//List형으로담아서 나오기위해 써준다. 이부분 내일물어보자
			
			forward.setPath("/member/member_list.jsp");
			forward.setRedirect(false);
			request.setAttribute("totallist", list);
			return forward;
	}

}
