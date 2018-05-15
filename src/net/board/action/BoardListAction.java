package net.board.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardBean;
import net.board.db.BoardDAO;

public class BoardListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BoardDAO boarddao = new BoardDAO();
		List<BoardBean> boardlist = new ArrayList<BoardBean>(); // 리스트에 담아서올꺼다.

		int page = 1;		//현재 페이지의 수				페이지
		int limit = 10;		// 한페이지에 보여줄 목록의 글의수			목록

		
		
		if (request.getParameter("page") != null) {												//아직 안정해준거같다 보내주는 곳이없다.	
			page = Integer.parseInt(request.getParameter("page"));
		}
		System.out.println("넘어온 페이지 = " + page);

		
		
		// 총 리스트 수를 받아옵니다. 
		int listcount = boarddao.getListCount();										//게시글의 수		

		
		boardlist = boarddao.getBoardList(page, limit);			// ★게시판화면을 보여주기위해 리스트를 받아옵니다.		리스트를 받아옵니다. //게시글자체를 받아온거

		/*
		 * 예를 들어 한 페이지에서 보여주는 리스트의 수가 10 개인 경우 예1) DB에 저장된 총 리스트의 수가 0 이면 총 페이지수는 1~10
		 * 페이지 총페이지수는 1페이지 예1) DB에 저장된 총 리스트의 수가 0 이면 총 페이지수는 11~20 페이지 총페이지수는 2페이지 예1)
		 * DB에 저장된 총 리스트의 수가 0 이면 총 페이지수는 21~30 페이지 총페이지수는 3페이지
		 */

		int maxpage = (listcount + limit-1) / limit; //					게시글이27  + 9= 36 / 10  4 	
		System.out.println("총 페이지수 = " + maxpage);

		/*
		 * startpage : 현재 페이지 그룹에서 맨 처음에 표시될 페이지수를 의미합니다. ([1],[11],[21] 등 ..) 보여줄 페이지가
		 * 30개일 경우 [1][2][3]...[30] 까지 다표시하기에는 너무 많기 깨문에 보틍 한 페이지에는 10 페이지 정도까지 이동할 수 있게
		 * 표시합니다. 예) 패키지 그룹에 [1]2[3][4][5][6][7][8][9][10] 페이지 그룹의 시작 페이지는 startpage 에
		 * 마지막 페이지는 endpage 에 구합니다
		 * 
		 * 예로 1~10 페이지의 내용을 나타낼때는 페이지그룹은 [1][2][3]..[10] 11~20 페이지의 내용을 나타낼 떄는 페이지 그룹은
		 * [11][12]..[20] 까지표시됩니다.
		 */
		int startpage = ((page - 1) / 10) * 10 + 1;							//
		System.out.println("현재 페이지에 보여줄 시작 페이지수 = " + startpage);

		// endpage : 현재 페이지 그룹에서 보여줄 마지막 페이지수([10],[20],[30]
		int endpage = startpage + 10 - 1;
		System.out.println("현재 페이지의 보여줄 마지막 페이지 수 " + endpage);

		/*
		 * 마지막 그룹의 마지막 페이지 값은 최대 페이지 값입니다 예로 마지막 페이지 그룹이 [21]~[30] 인 경우
		 * 시작페이지(startpage=21) 와 마지막페이지(endpage=30) 이지만 최대 페이지(maxpage)가 25라면 [21] ~[25]
		 * 까지만 표시되도록합니다.
		 */

		if (endpage > maxpage)
			endpage = maxpage;
		request.setAttribute("page", page); // 현재 페이지의 수
		request.setAttribute("maxpage", maxpage); // 최대 페이지의 수

		// 현재 페이지에 표시할 첫 페이지 수
		request.setAttribute("startpage", startpage);
		request.setAttribute("endpage", endpage);
		// 해당 페이지의 글 목록을 갖고 있는 리스트
		request.setAttribute("board", boardlist);		
		
		request.setAttribute("listcount", listcount);		//글개수
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);

		// 글 목록 페이지로 이동하기 위해 경로를 설정한다.
		forward.setPath("./board/qna_board_list.jsp");
		return forward; // BoardFrontController.java 로 리턴됩니다.
	}
}
