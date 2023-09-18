// 2022.1.18(화) 9h server 평가자 checklist <- Properties, xml 등 외부 파일 사용하지 않는다고 가정
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCTemplate {
	
	public static Connection getConnection() {
		Connection conn = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:", "KH", "KH");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void close(Connection conn) {
		
	}
	
	public static void close(Statement stmt) {
		
	}
	
	public static void close(ResultSet rset) {
		
	}
	
	public static void commit(Connection conn) {
		
	}
	
	public static void rollback(Connection conn) {
		
	}

}
