package ncstestNotice.model.dao;

import static ncstestNotice.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import ncstestNotice.model.vo.Comment;

//2022.2.16(수) 15h45
public class NoticeDao {
	
	private Properties prop = new Properties();

	public NoticeDao() {
		String fileName = NoticeDao.class.getResource("/ncstestNotice/sql/notice/notice-mapper.xml").getPath();
		
		try {
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int insertComment(Connection conn, String commentContents) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertComment");
		// INSERT INTO NCSTEST_COMMENTS(NO, COMMENTCONTENTS, WRITEDATE)
//		VALUES(SEQ_NCSTEST_COMMENTS.NEXTVAL, ?, SYSDATE)
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, commentContents);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	} // insertComment() 종료

	public ArrayList<Comment> selectCommentList(Connection conn) {
		ArrayList<Comment> list = new ArrayList<>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectCommentList");
		// SELECT * FROM NCSTEST_COMMENTS
//		ORDER BY NO DESC
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				list.add(new Comment(rset.getInt("NO"),
								   rset.getString("COMMENTCONTENTS"),
								   rset.getDate("WRITEDATE")));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

}
