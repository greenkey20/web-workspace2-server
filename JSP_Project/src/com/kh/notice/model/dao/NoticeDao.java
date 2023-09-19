package com.kh.notice.model.dao;

import static com.kh.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.kh.notice.model.vo.Notice;

//2022.1.10(월) 11h20
public class NoticeDao {
	
	private Properties prop = new Properties();
	
	public NoticeDao() {
		String fileName = NoticeDao.class.getResource("/sql/notice/notice-mapper.xml").getPath();
		
		try {
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Notice> selectNoticeList(Connection conn) {
		// 여기서 실행할 sql문 = SELECT문 -> ResultSet으로 결과 반환됨 -> ArrayList<Notice>에 담음
		
		// 필요한 변수들 세팅
		ArrayList<Notice> list = new ArrayList(); // 빈 list를 만들어서/생성해서 대입해줌 vs 여기서 list에 null을 대입하면, 아래 while 반복문에서 null인 list에 객체 추가할 수 없다고 null pointer exception 오류 발생
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectNoticeList");
		// SELECT NOTICE_NO, NOTICE_TITLE, USER_ID, NOTICE_CONTENT, COUNT, CREATE_DATE FROM NOTICE N JOIN MEMBER M ON (NOTICE_WRITER = USER_NO) WHERE N.STATUS = 'Y' ORDER BY CREATE_DATE DESC;
		// 나의 질문 = 강사님, notice_content 컬럼은 나중에 어딘가에 쓰려고 받아오는 것인가요? -> 2022.2.10(목) 17h20 나의 관찰 = 응답 페이지에서 딱히 사용하지는 않았음
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				Notice n = new Notice(rset.getInt("NOTICE_NO"),
									  rset.getString("NOTICE_TITLE"),
									  rset.getString("USER_ID"),
									  rset.getInt("COUNT"),
									  rset.getDate("CREATE_DATE"));
				// NOTICE 테이블에는 '작성자(NOTICE_WRITER)' 컬럼 값이 '작성자의 회원 번호'로 만들어져있기 때문에, 처음에 Notice vo 생성 시 noticeWriter를 int 자료형의 필드로 만들었는데, 이렇게 sql문(+JOIN) 활용해서 DB에서 뽑아보니 앞으로 글 작성자는 userNo(x) userId(o)로 표시할 것 같은 바, Notice vo 필드 선언부 내용(noticeWriter의 자료형) 수정함
				// + 이렇게 5개의 매개변수 받는 생성자 추가
				list.add(n);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	} // selectNoticeList() 종료
	
	// 2022.1.10(월) 15h25
	public int insertNotice(Connection conn, Notice n) {
		// 이번에 사용할 sql문 = INSERT문 -> 반환 값 = 처리된 행의 개수(int)

		// 필요한 변수들 세팅
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertNotice");
		// INSERT INTO NOTICE(NOTICE_NO, NOTICE_TITLE, NOTICE_CONTENT, NOTICE_WRITER) VALUES(SEQ_NNO.NEXTVAL, ?, ?, ?);
		// 나의 질문 = sql developer에서 sequence 정보 어디서 찾는지 모르겠습니다 >.<
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, n.getNoticeTitle());
			pstmt.setString(2, n.getNoticeContent());
			pstmt.setInt(3, Integer.parseInt(n.getNoticeWriter())); // NOTICE 테이블의 NOTICE_WRITER 컬럼의 값 = number 타입 -> Wrapper 클래스 Integer의 parseInt() 메소드로 String을 int로 변환함
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	} // insertNotice() 종료
	
	// 2022.1.10(월) 17h25
	public int increaseCount(Connection conn, int noticeNo) {
		// UPDATE문 실행 -> 처리된 행의 개수(int) 반환
		// 필요한 변수들 세팅
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("increaseCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, noticeNo);
			
			result = pstmt.executeUpdate(); // 1 또는 0
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	} // increaseCount() 종료
	
	// 2022.1.11(화) 9h25
	public Notice selectNotice(Connection conn, int noticeNo) {
		// SELECT문 실행 -> ResultSet 반환 + NOTICE_NO는 primary key(PK) 제약 조건이 걸려있는 바, PK 제약 조건(not null and unique -> 중복될 수 없고, 식별 가능함)에 따라 한 테이블에서 1행만 존재 -> 1행만 존재하는 ResultSet 반환 -> Notice 객체에 담음
		// 필요한 변수들 세팅
		Notice n = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectNotice");
		// SELECT NOTICE_NO, NOTICE_TITLE, NOTICE_CONTENT, USER_ID, CREATE_DATE FROM NOTICE N JOIN MEMBER ON (NOTICE_WRITER = USER_NO) WHERE NOTICE_NO = ? AND N.STATUS = 'Y'
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, noticeNo);
			rset = pstmt.executeQuery();
			
			if (rset.next()) { // rset에 다음 행이 존재하는 경우(에만) -> 위에서 만든 빈 Notice 객체 n에 조회 결과 담음
				// 테이블의 primary key(식별자 역할)가 항상 화면에 보이지는 않음 -> NOTICE_NO은 화면에 보여지는 데이터는 아님 + 해당 데이터 수정/삭제 시 반드시 필요함 -> 무조건 함께 조회해오면 좋음
				n = new Notice(rset.getInt("NOTICE_NO"),
							   rset.getString("NOTICE_TITLE"),
							   rset.getString("NOTICE_CONTENT"),
							   rset.getString("USER_ID"),
							   rset.getDate("CREATE_DATE"));				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return n;
	} // selectNotice() 종료
	
	// 2022.1.11(화) 12h20
	public int updateNotice(Connection conn, Notice n) {
		// UPDATE문 실행 -> 처리된 행의 개수 반환
		// 필요한 변수 세팅
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateNotice");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, n.getNoticeTitle());
			pstmt.setString(2, n.getNoticeContent());
			pstmt.setInt(3, n.getNoticeNo());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	} // updateNotice() 종료
	
	// 2022.1.11(화) 15h
	public int deleteNotice(Connection conn, int noticeNo) {
		// UPDATE문 실행 -> 처리된 행의 개수 반환
		// 필요한 변수들 세팅
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteNotice");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, noticeNo);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	} // deleteNotice() 종료

}
