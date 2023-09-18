package com.kh.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

// 2022.1.4(화) 11h20
public class JDBCTemplate {
	// JDBC 처리 과정 중 반복적으로 쓰이는 구문들을 각각의 메소드로 정의해둘 용도로 사용; 재사용할 목적으로 공통 템플릿 작업 진행 -> 다른 클래스에서 사용 가능 <- 이 클래스의 모든 메소드는 static 메소드로 만듦 <- singleton 패턴 = 메모리 영역에 단 1번만 올라간 것을 재사용하는 개념
	
	public static Connection getConnection() {
		// DB와 접속된 Connection 객체를 생성해서 반환해주는 메소드
		// Connection = DB의 연결정보(내가 접속할 DB의 url 정보 + IP주소, port번호, 접속할 user/계정명, 비밀번호)를 담고 있는 객체
		
		// 방법1) Java 코드 내에 명시적으로 작성 = 정적/hard coding 방식 -> DB 연결 정보가 변경된 경우 수정 등 유지/보수가 어려움
		// 방법2/해결 방식) DB 관련 정보를 별도로 관리하는 외부파일(.properties)로 만들어서 관리-> key에 대한 value를 외부 파일로부터 읽어들여서 반영시킴 = 동적 coding 방식 -> driver.properties = db 접속 정보를 담는 파일
		
		Properties prop = new Properties();
		Connection conn = null;
		
		// JDBC 처리 순서1) JDBC Driver 등록
		// 읽어들이고자 하는 driver.properties 파일이 존재하는 물리적인 경로를 알려줘야 함; getPath() = ("/sql/driver/driver.properties" 파일이 존재하는)물리적인 경로를 알아낼 수 있는 방법/메소드  -> 물리적인 경로를 문자열로 뺌 
		String fileName = JDBCTemplate.class.getResource("/sql/driver/driver.properties").getPath(); 
		// syso(fileName) 찍어보기
		
		try {
			// prop(객체)으로부터 load() 메소드를 이용해서 각 key에 해당하는 value를 가져옴
			prop.load(new FileInputStream(fileName)); // 파일 읽어오기
			
			// prop으로부터 getProperty() 메소드를 이용해서 각 key에 해당되는 value를 뽑아서 배치
//			Class.forName("oracle.jdbc.driver.OracleDriver"); // 서버 껐다 켜야 함 -> 403 error(서버에 문제가 있다) 뜸
			Class.forName(prop.getProperty("driver")); // 서버 껐다 켤 필요 없음 + class not found exception(예외) 처리 필요
			
			// JDBC 처리 순서2) connection 객체 생성
			conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Connection 객체 반환
		return conn;
	} // getConnection() 종료
	
	// 2. 전달받은 JDBC용 객체를 반납시켜주는 메소드(각 객체별로)
	// 2a) Connection 객체 닫아주는 메소드
	public static void close(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 2b) Statement 객체를 전달받아서 반납시켜주는 메소드
	public static void close(Statement stmt) {
		try {
			if (stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 2c) ResultSet 객체를 전달받아서 반납시켜주는 메소드
	public static void close(ResultSet rset) {
		try {
			if (rset != null && !rset.isClosed()) {
				rset.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 3. 전달받은 Connection 객체를 가지고 트랜잭션 처리해주는 메소드
	// 3a) 전달받은 Connection 객체를 가지고 COMMIT 시켜주는 메소드
	public static void commit(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 3b) 전달받은 Connection 객체를 가지고 ROLLBACK 시켜주는 메소드
	public static void rollback(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	

} // 클래스 영역 끝