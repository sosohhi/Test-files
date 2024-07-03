package com.mine;

import java.util.InputMismatchException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import com.mine.utils.DBManager;

public class Project1 {

	public static void main(String[] args) {
		Service service = new Service();
		Scanner scanner = new Scanner(System.in);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBManager.getConnection();
			System.out.println("관리자 계정을 입력해주세요.");
			System.out.print("ID : ");
			String inputID = scanner.nextLine();
			System.out.print("Password : ");
			String inputPW  = scanner.nextLine();
			
			String sql = "SELECT * FROM Supervisor WHERE adminID = ? AND adminPW = ?";
			pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, inputID);
	        pstmt.setString(2, inputPW);
	        rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	        	System.out.println("직원 관리시스템 시작");
	        	
	        	//입력창 무한루프
	    		while(true) {
	    			//게시판 제목
	    			System.out.println();
	    			System.out.println("		   	   [직원 관리 시스템]");
	    			System.out.println();
	    			//게시판 메뉴
	    			System.out.println("┌─────────────────────────────────────────────────────────────────┐");
	    			System.out.println("│	1.등록	2.목록	3.조회	4.수정	5.삭제	6.급여	7.종료	  │");
	    			System.out.println("└─────────────────────────────────────────────────────────────────┘");
	    			System.out.println("번호 입력 >>  ");
	    			
	    			//1. 메뉴선택번호 받기
	    			int menuNum = 0;
	    			while(true) {
	    			try {	menuNum = Integer.parseInt(scanner.nextLine());
	    				if (menuNum>6 |menuNum<1 ) {
	    					System.out.println("1번~7번 중에 입력해주세요");
	    				}else {
	    					System.out.println("선택된 번호 -> " +menuNum );
	    					break;
	    				}
	    					
	    			}catch(Exception e) {
	    				e.printStackTrace();
	    				System.out.println("잘못된 값을 입력하셨습니다. 다시 입력해주세요");			
	    				}
	    			}
	    			switch(menuNum) {
	    			case 1: //직원 등록
	    			System.out.println("[직원 등록]");
	    			service.register();
	    			break;
	    			case 2: //직원 목록
	    			System.out.println("[직원 명부 조회]");
	    			service.All_list();
	    			break;
	    			case 3: //직원 조회
	    			System.out.println("[직원 조회]");
	    			service.inquiry_emp();
	    			break;
	    			case 4: //직원 정보 수정
	    			System.out.println("[수정]");
	    			service.update_emp();
	    			break;
	    			case 5:	//직원 정보 삭제
	    			System.out.println("[삭제]");
	    			service.delete_emp();
	    			break;
	    			case 6:	//급여 정보 조회
	    			System.out.println("[급여조회]");
	    			service.salary_emp();
	    			break;
	    			case 7: // 시스템 종료
	    			System.out.println("[시스템 종료]");
		    		return;
	    			
	    			}
	    		}
	        }else {
	        	System.out.println("로그인 실패: 잘못된 관리자 계정입니다.");
	        	
	        }
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.dbClose(conn, pstmt, rs);
		}
		
		//종료 표시
		System.out.println("[직원 관리 시스템이 종료되었습니다.]");
	}
}


