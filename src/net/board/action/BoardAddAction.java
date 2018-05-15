package net.board.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.board.db.BoardBean;
import net.board.db.BoardDAO;

public class BoardAddAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		BoardDAO boarddao = new BoardDAO();
		BoardBean boarddata = new BoardBean();
		ActionForward forward = new ActionForward();

		String realFolder = "";

		// WebContent 아래에 꼭 폴더 생성하세요
		String saveFolder = "boardupload";

		int size = 5 * 1024 * 1024; // 업로드할 파일의 최대 사이즈 5MB로 설정

		ServletContext sc = request.getServletContext();
		realFolder = sc.getRealPath(saveFolder);

		System.out.println("realFolder=" + realFolder);
		boolean result = false;

		try {

			/*
			 * 업로드를 담당하는 부분입니다. -첫 번째 인자 request : 폼에서 가져온 값을 얻기 위해 request 객체를 전달해줍니다. -두
			 * 번째 인자 uploadPath : 업로드될 파일의 위치 입니다. -세 번째 인자 size : 업로드 할 크기이며 지정 크기보다 크면
			 * Excetption 발생합니다. -네 번째 인자 "euc-kr" : 파일 이름이 한글인 경우 처리하는 부분입니다. -다섯 번째 인자 :
			 * 똑같은 파일을 업로드 할 경우 중복되지 않도록 자동으로 파일이름을 변환해주는 기능을 합니다.
			 */
			MultipartRequest multi = new MultipartRequest(request, realFolder, size, "UTF-8",
					new DefaultFileRenamePolicy());

			boarddata.setBOARD_NAME(multi.getParameter("BOARD_NAME")); // 작성자
			boarddata.setBOARD_PASS(multi.getParameter("BOARD_PASS")); // 비밀번호
			boarddata.setBOARD_SUBJECT(multi.getParameter("BOARD_SUBJECT")); // 제목
			boarddata.setBOARD_CONTENT(multi.getParameter("BOARD_CONTENT")); // 내용

			// 업로드의 파일명은 업로드한 파일의 전체 경로에서 파일 이름만 저장합니다.
			boarddata.setBOARD_FILE(multi.getFilesystemName((String) multi.getFileNames().nextElement())); // 첨부파일 명

			// 글 등록 처리를 위해 DAO 의 broadInsert() 메서드를 호출합니다.
			// 글 등록 폼에서 입력한 정보가 저장되어 있는 boarddata 객체를 전달합니다.
			result = boarddao.boardInsert(boarddata);

			if (result == false) {
				System.out.println("게시판 등록 실패");
			} else {
				System.out.println("게시판 등록 성공");
			}

			// 글 등록이 완료되면 글 목록을 단순히 보여주기만 할 것이므로
			// Redircet 여부를 true 로 설정합니다.
			forward.setRedirect(true);
			forward.setPath("./BoardList.bo");
			return forward;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
