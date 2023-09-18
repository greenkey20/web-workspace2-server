package checklist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static checklist.JDBCTemplate.*;

//2022.3.17(목) 11h15
public class WisewordDao {
	
	public Wiseword getRandomWord(int ranNum) {
		// 필요한 변수 선언
		Wiseword w = null;
		
		Connection conn = getConnection(); // JDBCTemplate 만들어서 사용
		PreparedStatement pstmt = null;
		ResultSet rset = null;
				
		String sql = "SELECT SEQ, SENTENCE, WRITER FROM WISEWORD WHERE SEQ = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ranNum);
			
			rset = pstmt.executeQuery();
			
			if (rset.next()) {
				w = new Wiseword(rset.getInt("SEQ"), rset.getString("SENTENCE"), rset.getString("WRITER"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset); // JDBCTemplate 만들어서 사용
			close(pstmt); // JDBCTemplate 만들어서 사용
		}
		
		return w;
	}

}
