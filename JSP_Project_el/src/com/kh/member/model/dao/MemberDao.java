package com.kh.member.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.kh.common.JDBCTemplate;
import com.kh.member.model.vo.Member;

public class MemberDao {
	// 2022.1.10(월) 9h
	// dao 클래스의 역할 = sql 쿼리문 날려서 결과 받아오기
	// 1개의 메소드는 1개의 기능을 하는 것이 좋음 e.g. 아래 예시의 방법1
	// 비밀번호 update 후 update된 회원 정보를 반환하는 경우
	// 방법1) 수업 시간에서 한 것처럼 public int updatePwdMember() 메소드와 public Member selectMember() 메소드 따로 만듦
	// 방법2) updatePwdAndSelectMember() 메소드에 sql문1과 2 날려서 한꺼번에 처리
	
	// 2022.1.4(화) 15h45
	// sql문들 모아둔 별도의 외부 파일을 만들고 dao 클래스의 메소드들 호출할 때마다 불러읽어들여서 (수정된)sql문 실행
	private Properties prop = new Properties(); // properties 객체 생성 + 이 클래스만 사용할 수 있도록 캡슐화(접근제한자 private)
	
	// 기본 생성자; 이 생성자 내부에 파일 호출하는 코드 작성 -> 이 생성자가 호출될 때마다/dao 객체 생성 시 아래 xml 파일 다시 읽어옴
	public MemberDao() {
		String fileName = MemberDao.class.getResource("/sql/member/member-mapper.xml").getPath(); // 내가 참조하고자 하는 파일의 물리적인 경로를 얻어옴 -> String 자료형 반환
		
		try {
			prop.loadFromXML(new FileInputStream(fileName)); // 'surround with try/multi-catch'로 'IOException'로 예외 처리해줌
		} catch (IOException e) {
			e.printStackTrace();
		}
	} // 기본생성자 영역 끝
	
	public Member loginMember(Connection conn, String userId, String userPwd) {
		// SELECT문 날리면 ResultSet 객체로 결과를 받게 됨 -> userId에 걸린  unique key 제약 조건에 의해 1개의 행만 조회됨 -> Member 객체(의 필드 값)에 각 컬럼의 값을 담음

		// 필요한 변수 세팅/선언
		Member m = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("loginMember"); // 헷갈리지 않도록 sql문의 key 값 = 메소드명
		
		try {
			pstmt = conn.prepareStatement(sql); // pstmt 객체 생성
			
			// 쿼리문의 위치홀더 '?' 부분 채워서 완성시키기
			pstmt.setString(1, userId);
			pstmt.setString(2, userPwd);
			
			// 쿼리문 실행 후 결과 받기
			// 쿼리문 실행 메소드: SELECT문인 경우 pstmt.executeQuery(); vs INSERT/UPDATE/DELETE문인 경우 pstmt.executeUpdate(); -> preparedStatement의 경우 sql문을 (미완성된 상태이지만) 미리 날렸으므로, 매개변수로 넣을 필요 없음
			rset = pstmt.executeQuery();
			
			// SELECT문 실행에 따른 조회 결과가 있는 경우 rset으로부터 각 컬럼의 값을 뽑아서(rset.getInt/String/Date(뽑아올 컬럼명 또는 컬럼의 순서)) Member 객체에 담음
			// 조회 결과가 여러 행일 때 while (rset.next()) {
			// 조회 결과가 1개 행일 때 if (rset.next()) {
			if (rset.next()) {
				m = new Member(rset.getInt("USER_NO")
							 , rset.getString("USER_ID")
							 , rset.getString("USER_PWD")
							 , rset.getString("USER_NAME")
							 , rset.getString("PHONE")
							 , rset.getString("EMAIL")
							 , rset.getString("ADDRESS")
							 , rset.getString("INTEREST")
							 , rset.getDate("ENROLL_DATE")
							 , rset.getDate("MODIFY_DATE")
							 , rset.getString("STATUS"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 생성된 순서의 역순으로 자원 반납
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		// service에 결과(Member 객체) 넘기기
		return m;
	} // loginMember() 종료
	
	// 2022.1.5(수) _h
	public int insertMember(Connection conn, Member m) {
		// 이 메소드에서 실행하고자 하는  sql 쿼리문 = INSERT문 -> 반환 자료 = 처리된 행의 갯수(int)
		
		// 필요한 변수들 세팅
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertMember");
		
		try {
			// pstmt 객체 생성 + 미완성 상태의 sql문 미리 넘기기
			pstmt = conn.prepareStatement(sql);
			
			// 미완성 상태의 sql문의 위치홀더/빈칸 채우기
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getUserPwd());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getPhone());
			pstmt.setString(5, m.getEmail());
			pstmt.setString(6, m.getAddress());
			pstmt.setString(7, m.getInterest());
			
			// sql 실행(SELECT문인 경우 pstmt.executeQuery(); vs INSERT/UPDATE/DELETE문인 경우 pstmt.executeUpdate();) 및 결과 받기
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 자원 반납
			JDBCTemplate.close(pstmt); // JDBCTemplate에서 close() 메소드 객체별로 오버로딩(매개변수 다름)해서 만들어두었음
		}
		
		// 결과 반환/return
		return result;
	} // insertMember() 종료
	
	// 2022.1.7(금) _h
	public int updateMember(Connection conn, Member m) {
		// 이번에 실행하고자 하는 sql 쿼리문 = UPDATE문/DML -> 반환 자료 = 처리된 행의 갯수(int)
		
		// 필요한 변수들 세팅
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateMember");
		// 나의 질문 = sql UPDATE문 실행 시, default 값이 부여되어 있는 컬럼(예를 들면 회원 가입일)은 변경하겠다고 명시하지 않으면 변경 안 되는 것이 맞는지요? 즉, default 값은 INSERT할 때 해당 컬럼 값을 입력하지 않은 경우에만 적용되는 것이지요? -> 강사님 답변 = (정확하게 내 질문 모두에 답해주신 건 아니지만) update한다고 명시하지 않은 컬럼들은 변경 안 됨 
		
		try {
			pstmt = conn.prepareStatement(sql); // pstmt 객체 생성
			
			// 미완성 상태의 sql문 위치홀더/빈칸 채우기
			// UPDATE MEMBER SET USER_NAME = '사용자가 입력한 이름', PHONE = '사용자가 입력한 전화번호', EMAIL = '사용자가 입력한 이메일', ADDRESS = '사용자가 입력한 주소', INTEREST = '사용자가 입력한 관심 분야/취미', MODIFY_DATE = SYSDATE WHERE USER_ID = '사용자가 입력한 ID'; 
			pstmt.setString(1, m.getUserName());
			pstmt.setString(2, m.getPhone());
			pstmt.setString(3, m.getEmail());
			pstmt.setString(4, m.getAddress());
			pstmt.setString(5, m.getInterest());
			pstmt.setString(6, m.getUserId());
			
			result = pstmt.executeUpdate();	
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	} // updateMember() 종료
	
	// 2022.1.7(금) 14h
	public Member selectMember(Connection conn, String userId) {
		// 여기서 실행하고자 하는 sql문 = SELECT문 -> 반환 자료 = ResultSet 형태 -> Member 객체로 만들어 Service단으로 return
		
		// 필요한 변수 setting
		Member m = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectMember");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			
			// unique한 USER_ID로 조회를 하고자 하므로, 처리 결과는 1행 또는 0행 -> 반복문 쓸 필요 없음
			if (rset.next()) { // resultSet에 다음 행이 존재한다면/있다면
				m = new Member(rset.getInt("USER_NO"),
							   rset.getString("USER_ID"),
							   rset.getString("USER_PWD"),
							   rset.getString("USER_NAME"),
							   rset.getString("PHONE"),
							   rset.getString("EMAIL"),
							   rset.getString("ADDRESS"),
							   rset.getString("INTEREST"),
							   rset.getDate("ENROLL_DATE"),
							   rset.getDate("MODIFY_DATE"),
							   rset.getString("STATUS"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}	
		
		return m;
	} // selectMember() 종료
	
	// 2022.1.7(금) 16h25
	public int updatePwdMember(Connection conn, String userId, String userPwd, String updatePwd) {
		// UPDATE문 실행 -> 처리된 행의 개수 return(반환형 int)
		
		// 필요한 변수 세팅
		int result = 0;
		PreparedStatement pstmt = null; // 실행하려고 하는 sql문이 유동적이므로, preparedStatement 사용하고자 함 -> sql문에서 유동적인 부분에는 ?(위치 홀더)로 작성해둠
		String sql = prop.getProperty("updatePwdMember");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, updatePwd);
			pstmt.setString(2, userPwd);
			pstmt.setString(3, userId);
			
			result = pstmt.executeUpdate(); // userId는 고유값(unique)인 바, 반환 결과 = 1(비밀번호 변경 성공) 또는 0(실패)
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	} // updatePwdMember() 종료
	
	// 2022.1.7(금) 17h40 수업 실습 + 주말 숙제 -> 2022.1.10(월) 10h20 강사님 설명
	public int deleteMember (Connection conn, String userId, String userPwd) {
		// DELETE보다 복구의 여지가 있는 UPDATE문 사용/실행(STATUS 컬럼의 값을 'Y'->'N'로 변경) -> int형 처리된 행의 개수 return
		// btc 서비스(?) -> 회사별 회원 정보 처리 방침에 따라 회원 탈퇴 시 회원 정보를 바로 삭제해야 하는 경우가 있을 수도 있음 -> 회사마다 다르게 처리
		
		// 필요한 변수 세팅
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteMember");
		
		try {
			pstmt = conn.prepareStatement(sql);
			// UPDATE MEMBER SET STATUS = 'N', MODIFY_DATE = SYSDATE WHERE USER_ID = '현재 로그인되어있는/탈퇴하고자 하는 회원 ID' AND USER_PWD = '사용자가 입력한/view단에서 전달받은 비밀번호';
			
			pstmt.setString(1, userId);
			pstmt.setString(2, userPwd);
			
			result = pstmt.executeUpdate(); // 0 또는 1
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	} // deleteMember() 종료
	
	// 2022.1.17(월) 15h45
	public int idCheck(Connection conn, String checkId) {
		// SELECT문 실행 -> ResultSet 조회 결과 반환 -> COUNT 함수 이용해서 숫자 1개에 담음
		// 필요한 변수 세팅
		int count = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("idCheck");
		// SELECT COUNT(*) FROM MEMBER WHERE USER_ID = '사용자가 입력한 checkId';
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, checkId);
			rset = pstmt.executeQuery();
			
			if (rset.next()) {
				count = rset.getInt("COUNT(*)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return count;
	} // idCheck() 종료

}
