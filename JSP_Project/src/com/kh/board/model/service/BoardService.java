package com.kh.board.model.service;

import static com.kh.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.board.model.dao.BoardDao;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;
import com.kh.board.model.vo.Reply;
import com.kh.common.model.vo.PageInfo;

// 2022.1.11(화) 16h10
public class BoardService {
	
	// 2022.1.11(화) 16h50
	public int selectListCount() {
		Connection conn = getConnection();
		
		// SELECT문의 실행 결과는 ResultSet이긴 한데, 게시글의 총 개수를 알아야 하기 때문에 정수형 자료를 반환 받음
		int listCount = new BoardDao().selectListCount(conn);
		
		close(conn);
		
		return listCount;
	} // selectListCount() 종료
	
	// 2022.1.12(수) 10h20
	public ArrayList<Board> selectList(PageInfo pi) {
		Connection conn = getConnection();
		
		ArrayList<Board> list = new BoardDao().selectList(conn, pi);
		
		close(conn);
		
		return list;
	} // selectList() 종료
	
	// 2022.1.12(수) 14h10
	public ArrayList<Category> selectCategory() {
		Connection conn = getConnection();
		
		ArrayList<Category> list = new BoardDao().selectCategory(conn);
		
		close(conn);
		
		return list;
	} // selectCategory() 종료
	
	// 2022.1.12(수) 17h40
	public int insertBoard(Board b, Attachment at) {
		Connection conn = getConnection();
		
		// BOARD 테이블에 (행)삽입은 무조건 실행해야 함(필수) vs 첨부 파일(객체 at)이 null이 아니라면(선택적/조건적) ATTACHMENT 테이블에 삽입
		// 해야할 일1) BOARD 테이블에 INSERT -> 무조건 수행해야 함
		int result1 = new BoardDao().insertBoard(conn, b);
		
		// 해야할 일2) at이 null이 아닐 때만 ATTACHMENT 테이블에 INSERT
		int result2 = 1; // 일단 1 대입해둠('첨부할 파일 없음' ou '첨부할 파일이 있는데 + 파일 첨부 성공') vs '첨부할 파일이 있는데 + 파일 첨부 실패하면' result2 = 0으로 반환됨
		
		if (at != null) {
			result2 = new BoardDao().insertAttachment(conn, at);
		}
		
		// 해야할 일3) 트랜잭션 처리: result1도 성공이고 result2도 성공일 때 COMMIT vs 둘 중에 하나라도 실패하면 무조건 ROLLBACK e.g. 첨부할 파일이 있는데 파일 첨부가 실패하면, 게시글 작성도 COMMIT하면 안 됨
		if ((result1 * result2) > 0) { // [게시판 글 INSERT 성공 시] + ['첨부할 파일 없는 경우' ou '첨부할 파일이 있는데 + 파일 첨부도 성공 시'] = result1 > 0 && result2 > 0 또는 좌측과 같이 곱셈으로 종종 표현
			commit(conn);
		} else { // 둘 중에 하나라도 실패하면 -> 무조건 ROLLBACK
			rollback(conn);
		}
		
		close(conn);
		
		return (result1 * result2);		
	} // insertBoard() 종료
	
	// 2022.1.13(목) 12h45
	public int increaseCount(int boardNo) {
		Connection conn = getConnection();
		
		int result = new BoardDao().increaseCount(conn, boardNo);
		
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	} // increaseCount() 종료
	
	// 2022.1.13(목) 14h10
	public Board selectBoard(int boardNo) {
		Connection conn = getConnection();
		
		Board b = new BoardDao().selectBoard(conn, boardNo);
		
		close(conn);
		
		return b;
	} // selectBoard() 종료
	
	public Attachment selectAttachment(int boardNo) {
		Connection conn = getConnection();
		
		Attachment at = new BoardDao().selectAttachment(conn, boardNo);
		
		close(conn);
		
		return at;
	} // selectAttachment() 종료
	
	// 2022.1.14(금) 9h40
	public int updateBoard(Board b, Attachment at) {
		Connection conn = getConnection();
		
		// cases 1~3에 대해 공통적으로 일어나는 UPDATE BOARD 먼저 하고 조건 지정
		int result1 = new BoardDao().updateBoard(conn, b);
		
		int result2 = 1; // ATTACHMENT 테이블과 관련된 결과물
		
		if (at != null) { // 새롭게 첨부된 파일이 있을 경우
			if (at.getFileNo() != 0) { // 기존의 첨부 파일이 있었을 경우 = view단에서 파일 번호를 hidden으로 넘겨받음; 파일 번호는 1부터 sequence로 1씩 증가됨
				result2 = new BoardDao().updateAttachment(conn, at);
			} else { // 기존 첨부 파일이 없었을 경우
				// 기존에 만든 BoardDao().insertAttachment() 재활용 불가능 <- 쿼리문 중 SEQ_BNO.CURRVAL은 최근에 INSERT된 게시글의 게시글 번호를 가져옴
				result2 = new BoardDao().insertNewAttachment(conn, at);
			}
		} // 새로운 첨부 파일이 없을 경우 -> 별도로 할 일 없음 -> else문 블럭 필요 없음
		
		
		if (result1 > 0 && result2 > 0) { // 둘 다 성공했을 경우에만 -> 무조건 COMMIT
			commit(conn);
		} else { // 둘 중 하나라도 실패했을 경우 -> 무조건 ROLLBACK
			rollback(conn);
		}
		
		close(conn);
		
		return (result1 * result2);
	} // updateBoard() 종료
	
	// 2022.1.14(금) 주말 숙제 = 일반게시판 게시글 삭제
	
	// 2022.1.14(금) 15h35
	public int insertThumbnailBoard(Board b, ArrayList<Attachment> list) {
		Connection conn = getConnection();
		
		int result1 = new BoardDao().insertThumbnailBoard(conn, b);
		
		int result2 = new BoardDao().insertAttachmentList(conn, list);
		
		if (result1 > 0 && result2 > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result1 * result2;
	} // insertThumbnailBoard() 종료
	
	// 2022.1.17(월) 9h20
	public ArrayList<Board> selectThumbnailList() {
		Connection conn = getConnection();
		
		ArrayList<Board> list = new BoardDao().selectThumbnailList(conn);
		
		close(conn);
		return list;
	} // selectThumbnailList() 종료
	
	// 2022.1.17(월) 11h35
	public ArrayList<Attachment> selectAttachmentList(int boardNo) {
		Connection conn = getConnection();
		
		ArrayList<Attachment> list = new BoardDao().selectAttachmentList(conn, boardNo);
		
		close(conn);
		return list;
	} // selectAttachmentList() 종료
	
	// 2022.1.18(화) 17h5
	public ArrayList<Reply> selectReplyList(int boardNo) {
		Connection conn = getConnection();
		
		ArrayList<Reply> list = new BoardDao().selectReplyList(conn, boardNo);
		
		close(conn);
		
		return list;
	} // selectReplyList() 종료
	
	// 2022.1.19(수) 9h35
	public int insertReply(Reply r) {
		Connection conn = getConnection();
		
		int result = new BoardDao().insertReply(conn, r);
		
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	} // insertReply() 종료

}
