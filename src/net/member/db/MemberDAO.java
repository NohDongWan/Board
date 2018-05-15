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

package net.member.db;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
	DataSource ds;
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	int result;
	// 생성자에서 JNDI 리소스를 참조하여 Connection 객체를 얻어옵니다.

	public MemberDAO() {
		try {
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");

		} catch (Exception ex) {
			System.out.println("DB 연결 실패 : " + ex);
			return;
		}
	}

	public int isId(String id, String pass) {
		try {
			con = ds.getConnection();					//연결 완료
			System.out.println("getConnection");
			String sql = "select id,password from member where id = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {			//결과가 있다면.
				if (rs.getString(2).equals(pass)) { // 결과 rs 안에있는 두번째 칼럼데이터가 로그인폼에서 입력한 패스워드와 값이 같냐..
					result = 1; // 아이디와 비밀번호가 일치하는 경우
				} else {
					result = 0; // 비밀번호가 일치하지 않는 경우
				}
			} else {
				result = -1; // 아이디가 존재하지 않는 경우
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
		return result;

	}

	public List<Member> getList() {
		List<Member> list = new ArrayList<Member>();	//리스트로 담 아주기위해서 리스트형을 선언한다.
		try {
			con = ds.getConnection();

			String sql = "select * from member";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Member m = new Member();
				m.setId(rs.getString("id"));
				m.setPassword(rs.getString(2));
				m.setName(rs.getString(3));
				m.setAge(rs.getInt(4));
				m.setGender(rs.getString(5));
				m.setEmail(rs.getString(6));
				list.add(m);
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

	public Member member_info(String id) {
		Member m = new Member();
		try {
			con = ds.getConnection();

			String sql = "select * from member where id= ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				m.setId(rs.getString(1));
				m.setPassword(rs.getString(2));
				m.setName(rs.getString(3));
				m.setAge(rs.getInt(4));
				m.setGender(rs.getString(5));
				m.setEmail(rs.getString(6));

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
		return m;
	}

	public void delete(String id) {

		try {
			con = ds.getConnection();
			String sql = "delete from member where id = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();

		} catch (Exception e) {
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

	public int update(Member m) {

		try {
			con = ds.getConnection();
			String sql = "update member set name = ?, age = ?, gender = ? , email = ? where id = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m.getName());
			pstmt.setInt(2, m.getAge());
			pstmt.setString(3, m.getGender());
			pstmt.setString(4, m.getEmail());
			pstmt.setString(5, m.getId());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
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
		return result;
	}

	
	
	
	
	public int insert(Member m) {
		int result= 0;
		try {
			con = ds.getConnection();
			String sql = "insert into member values(?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m.getId());
			pstmt.setString(2, m.getPassword());
			pstmt.setString(3, m.getName());
			pstmt.setInt(4, m.getAge());
			pstmt.setString(5, m.getGender());
			pstmt.setString(6, m.getEmail());
			result = pstmt.executeUpdate();
			
			
			//primary key 제약 조건 위반했을 경우 발생하는에러 
		}catch(java.sql.SQLIntegrityConstraintViolationException e) {
			result = 0;
			System.out.println("아이디중복입니다.");
		} catch (Exception e) {
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
		return result;
	}

}
