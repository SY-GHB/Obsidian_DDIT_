package kr.or.ddit.utill0112;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// JDBC 드라이버를 로딩하고 DB에 접속하여
// Connection객체를 반환하는 메서드로 구성된 class만들기
public class DBUtill {
	//초기화블럭 만들기
	
	//인스턴스 초기화 블럭
	{
		
	}
	
	//클래스 초기화 블럭
	//생성자나 인스턴스 초기화 블록으로는 수행할 수 없는 클래스 변수의 초기화를 수행할 때 사용된다.
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			System.out.println("드라이버 로딩 실패 ㅠㅈㅠ");
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SY97", "java");
		} catch (SQLException e) {
			System.out.println("DB연결 실패 ㅠㅈㅠ");
			e.printStackTrace();
			// TODO: handle exception
		}
		return conn;
	}
}
