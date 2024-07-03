package com.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.student.utils.DBManager;

public class Board1Service {
	Scanner scanner = new Scanner(System.in);
	
	/**
	 * 설명 : 게시글 작성
	 */
	public int insertBoard() {
		// 1. 게시글 제목 받기
		System.out.println("글제목(취소: quit): ");
		String title = scanner.nextLine();	// 키보드로 글 제목 받기
		if (title.equals("quit")) {
			System.out.println("작성이 취소되었습니다.");
			return -1;
		}
		
		// 2. 게시글 내용 받기
		System.out.println("글내용(취소: quit): ");
		String content = scanner.nextLine(); // 키보드로 글 내용 받기
		if (content.equals("quit")) {
			System.out.println("작성이 취소되었습니다.");
			return -1;
		}
		
		// 3. DB위에 입력된 게시글의 제목과 내용을 받아서 Oracle DB에 저장할 수 있도록 DB접속준비
		Connection conn = DBManager.getConnection();
		
		// 4. Oracle DB에 데이터를 삽입하기 위해 insert sql문을 작성
		String insertSql = """
				INSERT INTO board(seq,title,content, read_count) 
				VALUES (seq_board_no.NEXTVAL, ?, ?,0)				
				""";
		
		int resultRows =0;	//insert문을 실행한 뒤에 테이블 영향을 받은 행 개수
		PreparedStatement pstmt = null;
		
		try {
		// 5. Oracle DB에 데이터를 삽입할 코드를 실행할 준비 
		pstmt = conn.prepareStatement(insertSql);
		pstmt.setString(1, title);		// 위 insertSql에 첫번째?에 키보드로 입력받은 제목을 세팅
		pstmt.setString(2, content);	// 위 insertSql에 두번째?에 키보드로 입력받은 내용을 세팅
		
		// 6. 실제로 sql코드를  실행
		resultRows = pstmt.executeUpdate();	//executeUpdate로 insert, update, delete sql코드를 실행
		System.out.println("글이 성공적으로 등록됨.");
		
		}catch(SQLException se) {
			System.out.println("게시글 삽입하는 도중 에러가 발생 -> " + se.getMessage());
		} finally {
			DBManager.dbClose(conn, pstmt,null);
		}
		return resultRows;
		
		
	}
	
	/**
	 * 설명 : 게시글 목록조회
	 */
	public void selectAllBoard() {
		
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		
		// 게시판 목록조회 화면구성
		System.out.println("--------------------------------------------------");
		System.out.println("번호       제목                          작성일             조회수");
		System.out.println("--------------------------------------------------");
		
		//Oracle DB의 Board 테이블에서 위의 4가지 항묵(번호, 제목, 작성일자, 조회수)가 나오도록 출력
		Connection conn = DBManager.getConnection();
		String selectSql = "SELECT seq, title, create_date, read_count FROM board Order BY seq DESC";
		try {
			pstmt2 = conn.prepareStatement(selectSql);
			rs = pstmt2.executeQuery();
			
			while (rs.next()) {
				System.out.println(rs.getString("seq") +"\t" +  
						rs.getString("title") + "\t" +"\t"+
						rs.getString("create_date")+"\t" +"      "+
						rs.getString("read_count")) ; 
				
				//printf -> %-3s -> 음수는 오른쪽에서부터 공백문자열 채워줌
				//printf -> %3s -> 양수는 왼쪽에서부터 공백문자열 채워줌
//				System.out.printf("%-5s | %-25s  | %-20s |%-5s \n"
//						,rs.getString("seq")
//						,rs.getString("title")
//						,rs.getString("create_date")
//						,rs.getString("read_count") == null ? "0" : rs.getString("read_count") );
				
			
			
			
			
			}
			
		}catch(SQLException se) {
			se.printStackTrace();
			System.out.println("게시글 목록 실행오류 -> " + se.getMessage());
			
		} finally {
			DBManager.dbClose(conn, pstmt2,rs);
		}
						
}
	
	/**
	 * 설명 : 게시글 수정
	 */
	public void updateBoard() {
		
		
	}
	
	public void selectOneBoard(){
		System.out.println("상세조회 할 게시판 번호를 입력하세요. > ");
		int boardNo = scanner.nextInt();
		
		// 1. 게시글의 상세 데이터를 받기 위해 Oracle DB접속준비 
		Connection conn = DBManager.getConnection();
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		String selectSql = "SELECT seq, title,content, create_date FROM board Order BY seq DESC";
		try {
			pstmt2 = conn.prepareStatement(selectSql);
			rs = pstmt2.executeQuery();
			
			
		
		// 게시판 상세조회
			 if (rs.next()) {
	            System.out.println("번호: " + rs.getInt("seq"));
	            System.out.println("제목: " + rs.getString("title"));
	            System.out.println("내용: " + rs.getString("content"));
	            System.out.println("작성일: " + rs.getDate("create_date"));
	            System.out.println("조회수: " + rs.getInt("read_count"));
	            System.out.println("--------------------------------------------------");
	        } else {
	            System.out.println("해당 번호의 게시글을 찾을 수 없습니다.");
	        }

	    } catch (SQLException se) {
	        se.printStackTrace();
	        System.out.println("게시글 상세 조회 중 오류 발생 -> " + se.getMessage());
	    } finally {
	        DBManager.dbClose(conn, pstmt2, rs);
	    }
		
			
	}		
}



