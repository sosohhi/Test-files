package com.soso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.soso.util.DBManager;

public class Customer {
	Scanner scanner = new Scanner(System.in);
	
	public boolean login(String customerId, String password) {
        Connection conn = DBManager.getConnection();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        String sql = "SELECT * FROM Customers WHERE CustomerID = ? AND CustomerPW = ?";

        try { 
        	pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, customerId);
            pstmt.setString(2, password);

            rs = pstmt.executeQuery();
            return rs.next(); // ID와 PW가 일치하는 행이 있는 경우 true 반환
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.dbClose(conn,pstmt,rs);
        }
        return false;
    }
	
	public void showCustomerMenu() {
		System.out.println("Java ShoppingMall");
		System.out.println("┌───────────────────────────────────────────────────────────────────┐");
		System.out.println("│1.상품보기		2.주문확인		3.배송확인		4.자유게시판	5.종료│");
		System.out.println("└───────────────────────────────────────────────────────────────────┘");
	
		int choice = scanner.nextInt();
		scanner.nextLine();
		switch(choice) {
		case 1:
			showProducts();
            break;
		case 2:
			orderCheck();
            break;
		case 3:
			showProducts();
            break;
		case 4:
			showProducts();
            break;
            
		}
	
	}

	public void  registerCustomer() {
		System.out.println("회원가입");
		System.out.print("사용할 아이디: ");
		String customerId = scanner.nextLine();

	    System.out.print("사용할 비밀번호: ");
	    String customerPw = scanner.nextLine();

	    System.out.print("성함: ");
	    String customerName = scanner.nextLine();

	    System.out.print("핸드폰번호: ");
	    String phone = scanner.nextLine();

	    System.out.print("Email주소: ");
	    String email = scanner.nextLine();

	    System.out.print("주소: ");
	    String address = scanner.nextLine();

	    System.out.print("시: ");
	    String city = scanner.nextLine();
	    
	    Connection conn = DBManager.getConnection();
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    int rownums = 0;
        
	    String sql = "INSERT INTO Customers (CustomerID, CustomerPW, CustomerName, Phone, Email, Address, City) VALUES (?, ?, ?, ?, ?, ?, ?)";
	    try { 
	    	pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, customerId);
            pstmt.setString(2, customerPw);
            pstmt.setString(3, customerName);
            pstmt.setString(4, phone);
            pstmt.setString(5, email);
            pstmt.setString(6, address);
            pstmt.setString(7, city);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("고객 정보가 성공적으로 저장되었습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.dbClose(conn,pstmt,rs);
        }
    }
	
	private static void showProducts(Scanner scanner) {
        Category category = new CategoryDAO();
        ProductManagement productDAO = new ProductDAO();

        List<String> categories = categoryDAO.getAllCategories();

        System.out.println("카테고리를 선택하세요:");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i));
        }

        int categoryChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        if (categoryChoice < 1 || categoryChoice > categories.size()) {
            System.out.println("잘못된 선택입니다.");
            return;
        }

        String selectedCategory = categories.get(categoryChoice - 1);
        String categoryId = categoryDAO.getCategoryIdByName(selectedCategory);

        List<String> products = productDAO.getProductsByCategory(categoryId);

        System.out.println(selectedCategory + " 카테고리의 상품 목록:");
        for (String product : products) {
            System.out.println("- " + product);
        }
    }
	public void orderCheck() {
		System.out.println("주문 확인");
		System.out.println("주문목록	|	교환/반품/환불	|	배송조회");
	}
	
}

