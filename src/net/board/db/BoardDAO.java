/*
 	DAO(Data Access Object) Ŭ����
 	- ������ ���̽��� �����Ͽ� ���ڵ��� �߰�, ����, ���� �۾���
 		�̷������ Ŭ���� �Դϴ�.
 	- � Action Ŭ������ ȣ��Ǵ��� �׿� �ش��ϴ�
 		�����ͺ��̽� ����ó���� DAO Ŭ�������� �̷������ �˴ϴ�.
 		
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

	// �����ڿ��� JNDI ���ҽ��� �����Ͽ� Connection ��ü�� ���ɴϴ�.
	public BoardDAO() {
		try {
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");

		} catch (Exception ex) {
			System.out.println("DB ���� ���� : " + ex);
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
		// �� ��� ����
		// page : ������
		// limit : ������ �� ����� ��
		// BOARD_RE_REF desc,BOARD_RE_SEQ asc �� ���� ������ ����
		// �������� �´� rnum�� ���� ��ŭ �������� �������Դϴ�.

		String board_list_sql = "select * from " + "(select rownum rnum,board_num,Board_name,"
				+ "board_subject, board_content,board_file," + "board_re_ref,board_re_lev,board_re_seq,"
				+ "board_readcount,board_date from" + "(select * from board " + " order by board_re_ref desc,"
				+ "board_re_seq asc)) " + "where rnum>=? and rnum <=?";

		List<BoardBean> list = new ArrayList<BoardBean>();
		// �� �������� 10���� ����� ��� 1������
		int startrow = (page - 1) * limit + 1; // �б� ������ row ��ȣ(1
		int endrow = startrow + limit - 1; // ���� ������ row ��ȣ(10
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(board_list_sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, endrow);
			rs = pstmt.executeQuery();

			// ������ �����͸� VO ��ü�� ����ϴ�.			//����Ʈ�� ����
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
				list.add(board); // ���� ���� ��ü�� ����Ʈ�� �����մϴ�.
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

	// �� ����ϱ�
	public boolean boardInsert(BoardBean board) {
		int num = 0;
		String sql = "";
		int result = 0;
		try {
			con = ds.getConnection();
			// board ���̺��� board_num �ʵ��� �ִ밪�� ���ؿͼ� ����
			// ����� �� �� ��ȣ�� ���������� �����ϱ� �����Դϴ�.
			String max_sql = "select max(board_num) from board";
			pstmt = con.prepareStatement(max_sql);
			rs = pstmt.executeQuery();

			if (rs.next())
				num = rs.getInt(1) + 1; // �ִ밪 ���� 1��ŭ ū ���� �����մϴ� ��? �ִ밪 ������ȣ�� �ѹ��� ����������ϱ� �����̴�. �Ʒ��� ���� �� ������
			else
				num = 1; // ó�� �����͸� ����ϴ� ����Դϴ�.

			sql = "insert into board " + "(BOARD_NUM,BOARD_NAME,BOARD_PASS,BOARD_SUBJECT,"
					+ " BOARD_CONTENT, BOARD_FILE, BOARD_RE_REF," + " BOARD_RE_LEV, BOARD_RE_SEQ,BOARD_READCOUNT,"
					+ " BOARD_DATE) " + " values(?,?,?,?,?,?,?,?,?,?,sysdate)";

			// ���ο� ���� ����ϴ� �κ��Դϴ�.
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, board.getBOARD_NAME());
			pstmt.setString(3, board.getBOARD_PASS());
			pstmt.setString(4, board.getBOARD_SUBJECT());
			pstmt.setString(5, board.getBOARD_CONTENT());
			pstmt.setString(6, board.getBOARD_FILE());
			pstmt.setInt(7, num); // BOARD_RE_REF �ʵ� //�̰Թ���

			// ������ ��� BOARD_RE_LEV, BOARD_RE_SEQ �ʵ� ���� 0 �Դϴ�.
			pstmt.setInt(8, 0); // BOARD_RE_LEV �ʵ� //������ ����̴޸������� �����̿ö󰣴�.
			pstmt.setInt(9, 0); // BOARD_RE_SEQ �ʵ�
			pstmt.setInt(10, 0); // BOARD_RE_READCOUNT �ʵ�

			result = pstmt.executeUpdate();
			if (result == 0)
				return false;
			return true;
		} catch (Exception ex) {
			System.out.println("boardInsert() ���� : " + ex);
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
		  �亯�� �� ���� �� �׷� ��ȣ�Դϴ�.
		  �亯�� �ް� �Ǹ� �亯���� �� ��ȣ�� ���� ���ñ� ��ȣ�� ���� ó���Ǹ鼭
		  ���� �׷쿡 ���ϰ� �˴ϴ�.
		 */
		
		try {
			con = ds.getConnection();
			//board ���̺��� board_num �ʵ��� �ִ밪�� ���ؿͼ� ���� ����� ��
			//�� ��ȣ�� ���������� �����ϱ� �����Դϴ�.
			String board_max_sql
					="select max(board_num) from board";
			pstmt = con.prepareStatement(board_max_sql);
			rs = pstmt.executeQuery();
			
			
			
			if (rs.next())
				num = rs.getInt(1) + 1; // �ִ밪 ���� 1��ŭ ū ���� �����մϴ� ��? �ִ밪 ������ȣ�� �ѹ��� ����������ϱ� �����̴�. �Ʒ��� ���� �� ������
			else
				num = 1; // ó�� �����͸� ����ϴ� ����Դϴ�.
			

			pstmt.close();
			
				sql1 = "update board "
				     + "set BOARD_RE_SEQ=BOARD_RE_SEQ + 1 "											//�̷������࿩�� 1234���ȴ�.
				     + "where BOARD_RE_REF = ? "
				     + "and BOARD_RE_SEQ > ?";
			
			pstmt= con.prepareStatement(sql1);
			pstmt.setInt(1, boarddata.getBOARD_RE_REF());
			pstmt.setInt(2, boarddata.getBOARD_RE_SEQ());
			result = pstmt.executeUpdate();
			pstmt.close();
			
			
			//����� �亯 ���� BOARD_RE_LEV, BOARD_RE_SEQ ���� ���� �ۺ��� 1��
			//������ŵ�ϴ�.
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
			pstmt.setString(6, "");	//�亯�� ������ ���ε������ʽ��ϴ�.
			pstmt.setInt(7, boarddata.getBOARD_RE_REF());
			pstmt.setInt(8, re_lev);
			pstmt.setInt(9, re_seq);
			pstmt.setInt(10, 0);				//BOARD_READCOUNT(��ȸ����) 0
			pstmt.executeUpdate();
			return num;//�۹�ȣ
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
			rs.next();										//rs �� ����ִ� �� �ϳ��� �̾ƿ´� 		rs �� ���� ����� rs.next() �� ��ߵȴ�.
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