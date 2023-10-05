package com.kh.board.model.dao;

import static com.kh.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;
import com.kh.board.model.vo.Reply;
import com.kh.common.model.vo.PageInfo;

// 2022.1.11(화) 16h10
public class BoardDao {

	private Properties prop = new Properties();

	public BoardDao() {
		String fileName = BoardDao.class.getResource("/sql/board/board-mapper.xml").getPath();
		
		try {
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 2022.1.11(화) 17h5
	public int selectListCount(Connection conn) {
		// SELECT문 실행 -> ResultSet 결과 반환 vs 지금 내가 필요한 것은 총 게시글의 개수(SELECT문을 쓰지만, 상식적으로 반환되는 값으로 정수가 필요함)
		
		// 필요한 변수 세팅
		int listCount = 0;
//		Board b = null; // 필요 없음
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectListCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			if (rset.next()) {
				listCount = rset.getInt("COUNT");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return listCount;
	} // selectListCount() 종료
	
	// 2022.1.12(수) 10h20
	public ArrayList<Board> selectList(Connection conn, PageInfo pi) {
		// SELECT문 실행 -> 여러 행의 ResultSet의 결과 반환 -> ArrayList<Board>에 담음
		
		// 필요한 변수들 세팅
		ArrayList<Board> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectList");
		// inline view(FROM절에 들어가는 서브쿼리) 활용 -> TOP-N 분석 활용 
		// SELECT *
//			FROM (SELECT ROWNUM RNUM, A.*
//			        FROM (SELECT BOARD_NO, CATEGORY_NAME, BOARD_TITLE, USER_ID, COUNT, CREATE_DATE
//			                FROM BOARD B 
//			                JOIN CATEGORY USING (CATEGORY_NO)
//			                JOIN MEMBER ON (BOARD_WRITER = USER_NO)
//			                WHERE BOARD_TYPE = 1 AND B.STATUS = 'Y'
//			                ORDER BY CREATE_DATE DESC) A)
//			WHERE RNUM BETWEEN ? AND ?
		// 단계1) 쿼리문에서 ORDER BY 실행 순서가 가장 마지막인데, 맨 처음에 실행되어야 정렬된 목록의 순서대로 조회할 수 있으므로, 일단 정렬해주는 SELECT문을 만듦 = 서브쿼리
		// 단계2) 서브쿼리를 메인쿼리의 FROM절에 넣음 + ROWNUM 붙이(고 별명 짓)기 <- ROWNUM은 시작 값이 1일 때만 잘 조회될 수 있도록 Oracle에서 만들어져 있음
		// 단게3) 어디서부터(시작 값) 어디까지(끝 값) 조회할 것인지 메인쿼리 WHERE절의 ROWNUM에 조건 주어 잘라내기
		// inline view와 같은 서브쿼리 볼 때는 -> 2022.2.10(목) 14h15 나의 질문 = 내가 무엇을 쓰다 만 거지? >.<
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			// sql문 위치홀더 채우기: boardLimit(한 페이지에 보여질 게시글의 최대 개수) = 10이라는 가정 하에,
			/* currentPage = 1 -> 시작 값 1, 끝 값 10
			 * currentPage = 2 -> 시작 값 11, 끝 값 20
			 * currentPage = 3 -> 시작 값 21, 끝 값 30
			 * ..
			 * -> 시작 값 = (currentPage - 1) * boardLimit + 1
			 * 	    끝 값 = 시작 값 + boardLimit - 1
			 * -> startRow와 endRow는 boardLimit의 영향을 받음
			 */
			
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				list.add(new Board(rset.getInt("BOARD_NO"),
								   rset.getString("CATEGORY_NAME"),
								   rset.getString("BOARD_TITLE"),
								   rset.getString("USER_ID"),
								   rset.getInt("COUNT"),
								   rset.getDate("CREATE_DATE")));			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	} // selectList() 종료
	
	// 2022.1.12(수) 14h15
	public ArrayList<Category> selectCategory(Connection conn) {
		// SELECT문 실행 -> 여러 개(7개) 행의 조회 결과 ResultSet 반환 -> ArrayList<Category>에 담음
		
		// 필요한 변수들 세팅
		ArrayList<Category> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectCategory");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				list.add(new Category(rset.getInt("CATEGORY_NO"),
									  rset.getString("CATEGORY_NAME")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	} // selectCategory() 종료
	
	// 2022.1.12(수) 17h40
	public int insertBoard(Connection conn, Board b) {
		// INSERT문 실행 -> 처리된 행의 개수 int 반환
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertBoard");
		// INSERT INTO BOARD(BOARD_NO, BOARD_TYPE, CATEGORY_NO, BOARD_TITLE, BOARD_CONTENT, BOARD_WRITER)
		//			 VALUES(SEQ_BNO.NEXTVAL, 1, ?, ?, ?, ?);
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, Integer.parseInt(b.getCategory()));
			pstmt.setString(2, b.getBoardTitle());
			pstmt.setString(3, b.getBoardContent());
			pstmt.setInt(4, Integer.parseInt(b.getBoardWriter()));
			
			// 2022.1.13(목) 9h30
			result = pstmt.executeUpdate(); // 1(글 작성 성공) 또는 0(글 작성 실패)
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	} // insertBoard() 종료
	
	// 2022.1.13(목) 9h
	public int insertAttachment(Connection conn, Attachment at) {
		// INSERT문 실행 -> 처리된 행의 개수 int 반환
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertAttachment");
		// INSERT INTO ATTACHMENT(FILE_NO, REF_BNO, ORIGIN_NAME, CHANGE_NAME, FILE_PATH)
//		VALUES(SEQ_FNO.NEXTVAL, SEQ_BNO.CURRVAL, ?, ?, ?);
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, at.getOriginName());
			pstmt.setString(2, at.getChangeName());
			pstmt.setString(3, at.getFilePath());
			
			result = pstmt.executeUpdate(); // 1(파일 첨부 성공) or 0(파일 첨부 실패)
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	} // insertAttachment() 종료
	
	// 2022.1.13(목) 12h20
	// 사용자가 게시글 번호 넘기며 요청함 -> 값(정수) 1개 넘어온 바, 가공 필요 없으므로 + controller에서 어떤 값들을 넘겨줄지 알면, dao부터 만들어도 상관 없음 -> 일반게시판 게시글 상세 조회 기능 구현을 위해서는 sql문 3개 날려야 하는데, 왔다갔다 하면 번거로우므로 dao 먼저 만들어보기로 함
	public int increaseCount(Connection conn, int boardNo) {
		// UPDATE문 실행 -> 처리된 행의 개수 int 반환

		// 필요한 변수 세팅 
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("increaseCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);

			result = pstmt.executeUpdate(); 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	} // increaseCount() 종료
	
	public Board selectBoard(Connection conn, int boardNo) {
		// SELECT문 실행 -> primary key에 의해 1행/건짜리 ResultSet 반환 -> Board 객체에 담음
		
		// 필요한 변수 세팅
		Board b = null; // 2022.1.17(월) 12h15(?) left join으로 쿼리문 수정 안 해서 어떤 학우님 null pointer exception 발생 -> 나의 질문 = 여기에 빈 문자열 대신 null을 대입해두는 특별한 이유가 (있다면) 무엇일까? 
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectBoard");
		// SELECT BOARD_NO, CATEGORY_NAME, BOARD_TITLE, BOARD_CONTENT, USER_ID, CREATE_DATE
//		FROM BOARD B
//		JOIN CATEGORY USING (CATEGORY_NO)
//		JOIN MEMBER ON (BOARD_WRITER = USER_NO)
//		WHERE BOARD_NO = ? AND B.STATUS = 'Y'
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			if (rset.next()) {
				b = new Board(rset.getInt("BOARD_NO"),
							  rset.getString("CATEGORY_NAME"),
							  rset.getString("BOARD_TITLE"),
							  rset.getString("BOARD_CONTENT"),
							  rset.getString("USER_ID"),
							  rset.getDate("CREATE_DATE"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return b;
	} // selectBoard() 종료
	
	public Attachment selectAttachment(Connection conn, int boardNo) {
		// SELECT문 실행 -> 일반게시판에는 파일 1개만 첨부 가능하므로, 1행/건짜리 ResultSet 반환 -> Attachment 객체에 담음
		
		// 필요한 변수 세팅
		Attachment at = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectAttachment");
		// SELECT FILE_NO, ORIGIN_NAME, CHANGE_NAME, FILE_PATH
//		FROM ATTACHMENT
//		WHERE REF_BNO = ?
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			if (rset.next()) {
				at = new Attachment();
				// 객체에 생성자를 통해서 값 넣거나 setter를 통해 값 넣거나 결과는 동일 -> 필요/상황에 따라 내가 합리적으로 선택해서 쓰면 됨
				// e.g. 필드 많은데 필드 전부 채워야 하는 경우 생성자가 더 편리 vs 생성자 만들기 귀찮거나, 선택적으로 몇 개 필드의 값만 넣어야 하는 경우 setter 사용이 더 편리
				at.setFileNo(rset.getInt("FILE_NO"));
				at.setOriginName(rset.getString("ORIGIN_NAME"));
				at.setChangeName(rset.getString("CHANGE_NAME"));
				at.setFilePath(rset.getString("FILE_PATH"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return at;
	} // selectAttachment() 종료
	
	// 2022.1.14(금) 9h45
	public int updateBoard(Connection conn, Board b) {
		// UPDATE문 실행 -> 처리된 행의 개수 int 반환
		// 필요한 변수 세팅
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateBoard");
		// UPDATE BOARD SET CATEGORY_NO = ?, BOARD_TITLE = ?, BOARD_CONTENT = ? WHERE BOARD_NO = ?;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(b.getCategory()));
			pstmt.setString(2, b.getBoardTitle());
			pstmt.setString(3, b.getBoardContent());
			pstmt.setInt(4, b.getBoardNo());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	} // updateBoard() 종료
	
	// 2022.1.14(금) 10h5
	public int updateAttachment(Connection conn, Attachment at) {
		// UPDATE문 실행 -> 처리된 행의 개수 int 반환 
		// 필요한 변수 세팅
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateAttachment");
		// UPDATE ATTACHMENT SET ORIGIN_NAME = ?, CHANGE_NAME = ?, FILE_PATH =?, UPLOAD_DATE = SYSDATE WHERE FILE_NO = ?
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, at.getOriginName());
			pstmt.setString(2, at.getChangeName());
			pstmt.setString(3, at.getFilePath());
			pstmt.setInt(4, at.getFileNo());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	} // updateAttachment() 종료
	
	// 2022.1.14(금) 10h20
	public int insertNewAttachment(Connection conn, Attachment at) {
		// INSERT문 실행 -> 처리된 행의 개수 int 반환
		// 필요한 변수 세팅
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertNewAttachment");
		// INSERT INTO ATTACHMENT(FILE_NO, REF_BNO, ORIGIN_NAME, CHANGE_NAME, FILE_PATH)
//		VALUES(SEQ_FNO.NEXTVAL, ?, ?, ?, ?)
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, at.getRefNo());
			pstmt.setString(2, at.getOriginName());
			pstmt.setString(3, at.getChangeName());
			pstmt.setString(4, at.getFilePath());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	} // insertNewAttachment() 종료
	
	
	// 2022.1.14(금) 주말 숙제 = 일반게시판 게시글 삭제
	
	// 2022.1.14(금) 15h35
	public int insertThumbnailBoard(Connection conn, Board b) {
		// INSERT문 실행 -> 처리된 행의 개수 int 반환
		// 필요한 변수 세팅
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertThumbnailBoard");
		// INSERT INTO BOARD(BOARD_NO, BOARD_TYPE, BOARD_TITLE, BOARD_CONTENT, BOARD_WRITER)
//		VALUES(SEQ_BNO.NEXTVAL, 2, ?, ?, ?)
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, b.getBoardTitle());
			pstmt.setString(2, b.getBoardContent());
			pstmt.setInt(3, Integer.parseInt(b.getBoardWriter()));
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	} // insertThumbnailBoard() 종료
	
	// 2022.1.14(금) 15h35
	public int insertAttachmentList(Connection conn, ArrayList<Attachment> list) {
		// INSERT문 실행 -> 처리된 행의 개수 int 반환
		// 필요한 변수 세팅
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertAttachmentList");
		// INSERT INTO ATTACHMENT(FILE_NO, REF_BNO, ORIGIN_NAME, CHANGE_NAME, FILE_PATH, FILE_LEVEL)
//		VALUES(SEQ_FNO.NEXTVAL, SEQ_BNO.CURRVAL, ?, ?, ?, ?)
		
		try {
			// 반복적으로 list에 접근해서 1개씩 INSERT
			
			// 내가 시도한 방법 = 나는 모르고 pstmt 객체를 1번만 생성하려고 했음 vs 서로 다른 sql문을 전달해야 하므로 sql문마다 pstmt 객체 생성해야 함
//			pstmt = conn.prepareStatement(sql);
//			for (int i = 0; i < list.size(); i++) {
//				pstmt.setString(1, list.get(i).getOriginName());
//				pstmt.setString(2, list.get(i).getChangeName());
//				pstmt.setString(3, list.get(i).getFilePath());
//				pstmt.setInt(4, list.get(i).getFileLevel());
//				
//				result = pstmt.executeUpdate(); // 이 줄 쓰다가 각 원소별 result를 다른 변수에 대입해두어야 하는지 고민하다 실습 시간 완료
//			}
			
			for (Attachment at : list) {
				// 반복문이 돌 때마다 미완성된 SQL문을 담은 pstmt 객체 생성
				System.out.println(at);
				pstmt = conn.prepareStatement(sql);
				
				// 순차적으로 at 뽑아와서 완성된 형태의 쿼리문 만들기
				pstmt.setString(1, at.getOriginName());
				pstmt.setString(2, at.getChangeName());
				pstmt.setString(3, at.getFilePath());
				pstmt.setInt(4, at.getFileLevel());
				
				// 쿼리문 실행
				result = pstmt.executeUpdate();
				// 강사님 보충 설명 = 파일 업로드 1개가 잘 되면 다 잘 될 것이고, 1개가 잘 안 되면 나머지도 다 잘 안 될 것임
				
				// 학우님의 제안 방법 = 확실한 방법(모든 파일이 잘 업로드되었는지 확인 가능)
//				result += pstmt.executeUpdate(); // ATTACHMENT 테이블에 file1 정보가 INSERT되면 1 -> file2가 추가되면 2.. -> 모든 파일이 각각 잘 업로드되는지 확인 가능
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	} // insertAttachmentList() 종료
	
	// 2022.1.17(월) 9h20
	public ArrayList<Board> selectThumbnailList(Connection conn) {
		// SELECT문 실행 -> 여러 행인 ResultSet으로 결과 받음 -> while문으로 ArrayList<Board>에 담음
		// 필요한 변수 세팅
		ArrayList<Board> list = new ArrayList<>(); // null로 만들어두면, 조회 결과가 없을 때 null list가 반환되어 null pointer exception이 발생할 수 있기 때문에, 빈 배열로 만들어둠
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectThumbnailList");
		// SELECT BOARD_NO, BOARD_TITLE, COUNT, FILE_PATH||CHANGE_NAME "TITLEIMG"
//		FROM BOARD B
//		JOIN ATTACHMENT ON (BOARD_NO = REF_BNO)
//		WHERE BOARD_TYPE = 2 AND B.STATUS = 'Y' AND FILE_LEVEL = 1
//		ORDER BY BOARD_NO DESC
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				Board b = new Board();
				// 조회해온 게시글 번호, 제목, 조회 수, TITLEIMG를 Board 객체에 담음
				b.setBoardNo(rset.getInt("BOARD_NO"));
				b.setBoardTitle(rset.getString("BOARD_TITLE"));
				b.setCount(rset.getInt("COUNT"));
				b.setTitleImg(rset.getString("TITLEIMG"));
				
				list.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	} // selectThumbnailList() 종료

	// 2022.1.17(월) 11h35
	public ArrayList<Attachment> selectAttachmentList(Connection conn, int boardNo) {
		// SELECT문 실행 -> 여러 행(1~4행)인 ResultSet으로 결과 받음 -> while문으로 ArrayList<Attachment>에 담음
		// 필요한 변수 세팅
		ArrayList<Attachment> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectAttachment"); // 기존에 만들어둔 쿼리문 재활용 가능
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				Attachment at = new Attachment();
				
				at.setFileNo(rset.getInt("FILE_NO"));
				at.setOriginName(rset.getString("ORIGIN_NAME"));
				at.setChangeName(rset.getString("CHANGE_NAME"));
				at.setFilePath(rset.getString("FILE_PATH"));
				
				list.add(at);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	} // selectAttachmentList() 종료
	
	// 2022.1.18(화) 17h10
	public ArrayList<Reply> selectReplyList(Connection conn, int boardNo) {
		// SELECT문 실행 -> 여러 행의 ResultSet 결과 반환 -> while문을 통해 ArrayList<Reply>에 담음
		// 필요한 변수 세팅
		ArrayList<Reply> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectReplyList");
		// SELECT REPLY_NO, REPLY_CONTENT, USER_ID, TO_CHAR(CREATE_DATE, 'YY/MM/DD HH:MI:SS') CREATE_DATE
//		FROM REPLY R
//		JOIN MEMBER ON (REPLY_WRITER = USER_NO)
//		WHERE REF_BNO = ? AND R.STATUS = 'Y'
//		ORDER BY CREATE_DATE DESC
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				list.add(new Reply(rset.getInt("REPLY_NO"),
								   rset.getString("REPLY_CONTENT"),
								   rset.getString("USER_ID"),
								   rset.getString("CREATE_DATE")));	
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	} // selectReplyList() 종료
	
	// 2022.1.19(수) 9h35
	public int insertReply(Connection conn, Reply r) {
		// INSERT문 실행 -> 처리된 행의 개수 int 반환(필기 다 못 함)
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertReply");
		// INSERT INTO REPLY(REPLY_NO, REPLY_CONTENT, REF_BNO, REPLY_WRITER)
//		VALUES(SEQ_RNO.NEXTVAL, ?, ?, ?)
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, r.getReplyContent());
			pstmt.setInt(2,  r.getRefBno());
			pstmt.setInt(3, Integer.parseInt(r.getReplyWriter()));
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	} // insertReply() 영역 끝

	// 2023.10.3(화) 20h15
	public int deleteBoard(Connection conn, int boardNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteBoard");

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, boardNo);

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
//			throw new RuntimeException(e);
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}
}
