package com.soso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.soso.util.DBManager;

public class Manager {
	Scanner scanner = new Scanner(System.in);
	ProductManagement productManagement = new ProductManagement();
	CustomerManagement customerManagement = new CustomerManagement();
	OrderManagement orderManagement = new OrderManagement();
	BoardManagement boardManagement = new BoardManagement();
	/*
	 * 관리자 로그인
	 */
	public boolean login(String managerId, String password) {
        Connection conn = DBManager.getConnection();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        String sql = "SELECT * FROM Managers WHERE ManagerID = ? AND ManagerPW = ?";

        try {
        	pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, managerId);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            return rs.next(); // ID와 PW가 일치하는 행이 있는 경우 true 반환
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("로그인 오류" + e.getMessage());
        } finally {
            DBManager.dbClose(conn, pstmt,rs);
        }
        return false;
    }
	
	/*
	 * 관리자 메뉴
	 */
	public void showManagerMenu() {
		while (true) {
			System.out.println("Java ShoppingMall 운영자 메뉴");
			System.out.println("1. 상품 관리");
			System.out.println("2. 고객 관리");
			System.out.println("3. 주문 관리");
			System.out.println("4. 게시판 관리");
			System.out.println("5. 종료");
			
			int choice = scanner.nextInt();
			switch(choice) {
			case 1:
				productManagement.manageProducts();
	            break;
			case 2:
				customerManagement.manageCustomers();
	            break;
			case 3:
				orderManagement.manageOrders();
	            break;
			case 4:
				boardManagement.manageBoard();
	            break;
			case 5:
				System.out.println("프로그램을 종료합니다.");
	            return;
			default:
	            System.out.println("잘못된 선택입니다. 다시 시도해주세요.");
	            showManagerMenu();
	            break;
			}		
		}
	}
}

