/*
 	DAO(Data Access Object) 클래스
 	- 데이터 베이스와 연동하여 레코드의 추가, 수정, 삭제 작업이
 		이루어지는 클래스 입니다.
 	- 어떤 Action 클래스가 호출되더라도 그에 해당하는
 		데이터베이스 연동처리는 DAO 클래스에서 이루어지게 됩니다.
 		
<%@ page import="java.sql.*"%>
<%@ page import="javax.sql.*"%>
<%@ page import="javax.naming.*"%>
 */

package net.board.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.*;
import javax.naming.*;

public class BoardDAO {
	DataSource ds;
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	int result;

	// 생성자에서 JNDI 리소스를 참조하여 Connection 객체를 얻어옵니다.
	public BoardDAO() {
		try {
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");

		} catch (Exception ex) {
			System.out.println("DB 연결 실패 : " + ex);
			return;
		}
	}

	public int getListCount() {
		int x = 0;
		try {
			con = ds.getConnection();
			String sql = "select count(*) from board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sq) {
					sq.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sq) {
					sq.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException sq) {
					sq.printStackTrace();
				}
			}
		}
		return x;

	}

	public List<BoardBean> getBoardList(int page, int limit) {
		// 글 목록 보기
		// page : 페이지
		// limit : 페이지 당 목록의 수
		// BOARD_RE_REF desc,BOARD_RE_SEQ asc 에 의해 정렬한 것을
		// 조건절에 맞는 rnum의 범위 만큼 가져오는 쿼리문입니다.

		String board_list_sql = "select * from " + "(select rownum rnum,board_num,Board_name,"
				+ "board_subject, board_content,board_file," + "board_re_ref,board_re_lev,board_re_seq,"
				+ "board_readcount,board_date from" + "(select * from board " + " order by board_re_ref desc,"
				+ "board_re_seq asc)) " + "where rnum>=? and rnum <=?";

		List<BoardBean> list = new ArrayList<BoardBean>();
		// 한 페이지당 10개씩 목록인 경우 1페이지
		int startrow = (page - 1) * limit + 1; // 읽기 시작할 row 번호(1
		int endrow = startrow + limit - 1; // 읽을 마지막 row 번호(10
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(board_list_sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, endrow);
			rs = pstmt.executeQuery();

			// 가져온 데이터를 VO 객체에 담습니다.			//리스트에 저장
			while (rs.next()) {
				BoardBean board = new BoardBean();
				board.setBOARD_NUM(rs.getInt("BOARD_NUM"));
				board.setBOARD_NAME(rs.getString("BOARD_NAME"));
				board.setBOARD_SUBJECT(rs.getString("BOARD_SUBJECT"));
				board.setBOARD_CONTENT(rs.getString("BOARD_CONTENT"));
				board.setBOARD_FILE(rs.getString("BOARD_FILE"));
				board.setBOARD_RE_REF(rs.getInt("BOARD_RE_REF"));
				board.setBOARD_RE_LEV(rs.getInt("BOARD_RE_LEV"));
				board.setBOARD_RE_SEQ(rs.getInt("BOARD_RE_SEQ"));
				board.setBOARD_READCOUNT(rs.getInt("BOARD_READCOUNT"));
				board.setBOARD_DATE(rs.getDate("BOARD_DATE"));
				list.add(board); // 값을 담은 객체를 리스트에 저장합니다.
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sq) {
					sq.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sq) {
					sq.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException sq) {
					sq.printStackTrace();
				}
			}
		}
		return list;
	}

	// 글 등록하기
	public boolean boardInsert(BoardBean board) {
		int num = 0;
		String sql = "";
		int result = 0;
		try {
			con = ds.getConnection();
			// board 테이블의 board_num 필드의 최대값을 구해와서 글을
			// 등록할 때 글 번호를 순차적으로 지정하기 위함입니다.
			String max_sql = "select max(board_num) from board";
			pstmt = con.prepareStatement(max_sql);
			rs = pstmt.executeQuery();

			if (rs.next())
				num = rs.getInt(1) + 1; // 최대값 보다 1만큼 큰 값을 지정합니다 왜? 최대값 다음번호로 넘버를 지정해줘야하기 때문이다. 아래를 보면 알 수있음
			else
				num = 1; // 처음 데이터를 등록하는 경우입니다.

			sql = "insert into board " + "(BOARD_NUM,BOARD_NAME,BOARD_PASS,BOARD_SUBJECT,"
					+ " BOARD_CONTENT, BOARD_FILE, BOARD_RE_REF," + " BOARD_RE_LEV, BOARD_RE_SEQ,BOARD_READCOUNT,"
					+ " BOARD_DATE) " + " values(?,?,?,?,?,?,?,?,?,?,sysdate)";

			// 새로운 글을 등록하는 부분입니다.
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, board.getBOARD_NAME());
			pstmt.setString(3, board.getBOARD_PASS());
			pstmt.setString(4, board.getBOARD_SUBJECT());
			pstmt.setString(5, board.getBOARD_CONTENT());
			pstmt.setString(6, board.getBOARD_FILE());
			pstmt.setInt(7, num); // BOARD_RE_REF 필드 //이게뭐냐

			// 원문의 경우 BOARD_RE_LEV, BOARD_RE_SEQ 필드 값은 0 입니다.
			pstmt.setInt(8, 0); // BOARD_RE_LEV 필드 //원본글 댓글이달릴때마다 레벨이올라간다.
			pstmt.setInt(9, 0); // BOARD_RE_SEQ 필드
			pstmt.setInt(10, 0); // BOARD_RE_READCOUNT 필드

			result = pstmt.executeUpdate();
			if (result == 0)
				return false;
			return true;
		} catch (Exception ex) {
			System.out.println("boardInsert() 에러 : " + ex);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sq) {
					sq.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sq) {
					sq.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException sq) {
					sq.printStackTrace();
				}
			}
		}

		return false;
	}

	public BoardBean getDetail(int num) {
		BoardBean b = new BoardBean();
		try {
			con = ds.getConnection();

			String sql = "select * from board where BOARD_NUM = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				b.setBOARD_NUM(rs.getInt("BOARD_NUM"));
				b.setBOARD_NAME(rs.getString("BOARD_NAME"));
				b.setBOARD_SUBJECT(rs.getString("BOARD_SUBJECT"));
				b.setBOARD_CONTENT(rs.getString("BOARD_CONTENT"));
				b.setBOARD_FILE(rs.getString("BOARD_FILE"));
				b.setBOARD_RE_REF(rs.getInt("BOARD_RE_REF"));
				b.setBOARD_RE_LEV(rs.getInt("BOARD_RE_LEV"));
				b.setBOARD_RE_SEQ(rs.getInt("BOARD_RE_SEQ"));
				b.setBOARD_READCOUNT(rs.getInt("BOARD_READCOUNT"));
				b.setBOARD_DATE(rs.getDate("BOARD_DATE"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sq) {
					sq.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sq) {
					sq.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException sq) {
					sq.printStackTrace();
				}
			}
		}
		return b;
	}

	public void setReadCountUpdate(int num) {
			
		String sql = "update board "
				+ "set BOARD_READCOUNT=BOARD_READCOUNT+1" 
						+ "WHERE BOARD_NUM = ?";
		try {
			con = ds.getConnection();		
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sq) {
					sq.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException sq) {
					sq.printStackTrace();
				}
			}
		}
	}

	
	
	public int boardReply(BoardBean boarddata) {
		
		
		
		int num = 0;
		String sql2="";
		String sql1="";
	
		
		/*
		  답변을 할 원문 글 그룹 번호입니다.
		  답변을 달게 되면 답변글은 이 번호와 같은 관련글 번호를 갖게 처리되면서
		  같은 그룹에 속하게 됩니다.
		 */
		
		try {
			con = ds.getConnection();
			//board 테이블의 board_num 필드의 최대값을 구해와서 글을 등록할 때
			//글 번호를 순차적으로 지정하기 위함입니다.
			String board_max_sql
					="select max(board_num) from board";
			pstmt = con.prepareStatement(board_max_sql);
			rs = pstmt.executeQuery();
			
			
			
			if (rs.next())
				num = rs.getInt(1) + 1; // 최대값 보다 1만큼 큰 값을 지정합니다 왜? 최대값 다음번호로 넘버를 지정해줘야하기 때문이다. 아래를 보면 알 수있음
			else
				num = 1; // 처음 데이터를 등록하는 경우입니다.
			

			pstmt.close();
			
				sql1 = "update board "
				     + "set BOARD_RE_SEQ=BOARD_RE_SEQ + 1 "											//이렇게해줘여ㅑ 1234가된다.
				     + "where BOARD_RE_REF = ? "
				     + "and BOARD_RE_SEQ > ?";
			
			pstmt= con.prepareStatement(sql1);
			pstmt.setInt(1, boarddata.getBOARD_RE_REF());
			pstmt.setInt(2, boarddata.getBOARD_RE_SEQ());
			result = pstmt.executeUpdate();
			pstmt.close();
			
			
			//등록할 답변 글의 BOARD_RE_LEV, BOARD_RE_SEQ 값을 원문 글보다 1씩
			//증가시킵니다.
			int re_seq = boarddata.getBOARD_RE_SEQ();
			int re_lev = boarddata.getBOARD_RE_LEV();
			
			re_seq = re_seq + 1;
		    re_lev = re_lev + 1;
		    
		    
			sql2 = "insert into board "
				      + "(BOARD_NUM,BOARD_NAME,BOARD_PASS,BOARD_SUBJECT,"
				      + " BOARD_CONTENT, BOARD_FILE, BOARD_RE_REF,"
				      + " BOARD_RE_LEV, BOARD_RE_SEQ,"
				      + " BOARD_READCOUNT,BOARD_DATE) "
				      + "values(?,?,?,?,?,?,?,?,?,?,sysdate)";
			
			pstmt = con.prepareStatement(sql2);
			pstmt.setInt(1, num);
			pstmt.setString(2, boarddata.getBOARD_NAME());
			pstmt.setString(3, boarddata.getBOARD_PASS());
			pstmt.setString(4, boarddata.getBOARD_SUBJECT());
			pstmt.setString(5, boarddata.getBOARD_CONTENT());
			pstmt.setString(6, "");	//답변은 파일을 업로드하지않습니다.
			pstmt.setInt(7, boarddata.getBOARD_RE_REF());
			pstmt.setInt(8, re_lev);
			pstmt.setInt(9, re_seq);
			pstmt.setInt(10, 0);				//BOARD_READCOUNT(조회수는) 0
			pstmt.executeUpdate();
			return num;//글번호
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sq) {
					sq.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sq) {
					sq.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException sq) {
					sq.printStackTrace();
				}
			}
		}
		return 0;
		
	}//boardreply

	public boolean isBoardWriter(int num, String pass) {
			String board_sql = "select * from board where BOARD_NUM = ? ";
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(board_sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			rs.next();										//rs 의 들어있는 행 하나를 뽑아온다 		rs 의 값을 쓸라면 rs.next() 를 써야된다.
			if(pass.equals(rs.getString("BOARD_PASS"))) {
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sq) {
					sq.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sq) {
					sq.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException sq) {
					sq.printStackTrace();
				}
			}
		}
		return false;
		
	}

	public boolean boardModify(BoardBean boarddata) {
		String sql = "update board set BOARD_SUBJECT = ?, BOARD_CONTENT = ? where BOARD_NUM = ? "; 
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, boarddata.getBOARD_SUBJECT());
			pstmt.setString(2, boarddata.getBOARD_CONTENT());
			pstmt.setInt(3, boarddata.getBOARD_NUM());
			pstmt.executeUpdate();
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sq) {
					sq.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException sq) {
					sq.printStackTrace();
				}
			}
		}
		return false;
		
	}

	public boolean boardDelete(int num) {
		
		BoardBean bb = new BoardBean();
		BoardDAO bd = new BoardDAO();
		bb = bd.getDetail(num);
		
		
		String sql = "delete board where BOARD_NUM = ? ";
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sq) {
					sq.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException sq) {
					sq.printStackTrace();
				}
			}
		}
		return false;
		
	}
}