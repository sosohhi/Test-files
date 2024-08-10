package com.dobby.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager {
	/**
	 *  설명: 오라클 접속 메소드
	 *  @return Connection객체 -> 오라클 접속 클래스 객체
	 */
	public static Connection getDBConnection() {
		Connection conn = null;
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			
			String url = "jdbc:oracle:thin:@1.220.247.78:1522:orcl";
			String username = "semi_2405_team1";
			String password = "1234";
			
			conn = DriverManager.getConnection(url, username, password);
		}catch(Exception e) {
			e.printStackTrace();
			
			System.out.println("DB연결 오류");
		}
		
		return conn;
	}	
	public static void dbClose(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}catch (SQLException se) {
			System.out.println("Oracle DB IO 오류 -> " + se.getMessage());
		}
	}
}

