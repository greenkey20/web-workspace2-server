package ncstestNotice.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import static ncstestNotice.common.JDBCTemplate.*;
import ncstestNotice.model.dao.NoticeDao;
import ncstestNotice.model.vo.Comment;

//2022.2.16(수) 15h40
public class NoticeService {

	public int insertComment(String commentContents) {
		Connection conn = getConnection();
		
		int result = new NoticeDao().insertComment(conn, commentContents);
		
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	} // insertComment() 종료

	public ArrayList<Comment> selectCommentList() {
		Connection conn = getConnection();
		
		ArrayList<Comment> list = new NoticeDao().selectCommentList(conn);
		
		close(conn);
		
		return list;
	}

}
