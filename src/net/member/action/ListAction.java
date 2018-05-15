/*
 	���� �׼� �������� ������ �������ְ�		: �����̷�Ʈ�� ���� ������� ���� �����ؾ��ϱ⶧����... 
 	DAO Ŭ���� �����༭ �Լ� ���ְ�	
 	alert ���ְ� ������ ���ش� 
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
			ActionForward forward = new ActionForward();	//������ ���� �������ֱ����� ���ش�.
			MemberDAO mdao = new MemberDAO();				//����������� ���ش�
			List<Member> list = mdao.getList();				//List�����δ�Ƽ� ���������� ���ش�. �̺κ� ���Ϲ����
			
			forward.setPath("/member/member_list.jsp");
			forward.setRedirect(false);
			request.setAttribute("totallist", list);
			return forward;
	}

}
