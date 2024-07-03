package com.student.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager {
	/**	
	 * 설명 : 오라클 접속 메소드
	 * @return Connection객체 -> 오라클 접속 클래스 객체
	 */
	public static Connection getConnection() {
		Connection conn = null;
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");	// 오라클 접속을 위한 Driver 사전작업
			
			String url = "jdbc:oracle:thin:@localhost:1521:orcl";	// 오라클 접속할 위치값 저장
			String username = "boarduser1";		// 오라클 접속 계정id
			String password = "1234";			// 오라클 접속 비밀번호
			
			// 실제 오라클 접속 후 접속을 다루는 객체를 받아 Connection클래스 객체에 대입
			conn = DriverManager.getConnection(url, username, password);	 

			
			}catch (Exception e) {
				e.printStackTrace();		// 에러추적표시
				
				System.out.println("DB연결오류");
			}
		
		return conn;
		
	}
	public static void dbClose(Connection conn, PreparedStatement pstmt, ResultSet rs ) {
		//DB입출력 자원 정리
		try {
			if (rs != null) {
				rs.close();			//데이터 가져온 것을 close
			}
			if (pstmt != null) {
				pstmt.close();		//데이터 가져온 실행을 close
			}
			if (conn != null) {		//오라클 접속을 close
				conn.close();
			}
			
		} catch(SQLException se){
			se.printStackTrace();		// 에러 추척 표시
			
			System.out.println("Oracle DB IO 오류" + se.getMessage());
		
	}
	}

}
