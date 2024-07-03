package com.soso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Scanner;

public class ProductManagement {
	Scanner scanner = new Scanner(System.in);
   
	/*
	 * 상품관리 선택화면
	 */
	public void manageProducts() {
        while (true) {
            System.out.println("상품 관리");
            System.out.println("0. 카테고리 등록");
            System.out.println("1. 상품 등록");
            System.out.println("2. 상품 조회");
            System.out.println("3. 상품 수정");
            System.out.println("4. 상품 삭제");
            System.out.println("5. 돌아가기");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline character
            switch (choice) {
            	case 0:
            		addCategory();
                case 1:
                    addProduct();
                    break;
                case 2:
                    viewProducts();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    deleteProduct();
                    break;
                case 5:
                    return;  // 돌아가기
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도해주세요.");
            }
        }
    }
	public void addCategory() {
		
	}

    public void addProduct() {
        System.out.println("새 상품 등록");

        System.out.print("상품 ID: ");
        String productId = scanner.nextLine();

        System.out.print("상품 이름: ");
        String productName = scanner.nextLine();

        System.out.print("상품 옵션: ");
        String productOption = scanner.nextLine();

        System.out.print("가격: ");
        double price = scanner.nextDouble();

        System.out.print("재고 수량: ");
        int stock = scanner.nextInt();
        scanner.nextLine();  // consume newline character

        System.out.print("카테고리 ID: ");
        String categoryId = scanner.nextLine();
        
        Connection conn = null;
        PreparedStatement ptmtp = null;
        ResultSet rs = null;
        
        String insertSql = """
        		INSERT INTO Products  VALUES ProductID,ProductName,ProductOption,Price,Stock,CategoryID
        		""";

        
        System.out.println("상품이 등록되었습니다.");
    }

    private void viewProducts() {
        System.out.println("상품 조회");
       
    }

    private void updateProduct() {
        System.out.println("상품 수정");

        System.out.print("수정할 상품 ID: ");
        String productId = scanner.nextLine();

       
        if (product == null) {
            System.out.println("해당 ID의 상품이 존재하지 않습니다.");
            return;
        }

        System.out.print("새 상품 이름 (기존: " + product.getProductName() + "): ");
        String productName = scanner.nextLine();

        System.out.print("새 상품 옵션 (기존: " + product.getProductOption() + "): ");
        String productOption = scanner.nextLine();

        System.out.print("새 가격 (기존: " + product.getPrice() + "): ");
        double price = scanner.nextDouble();

        System.out.print("새 재고 수량 (기존: " + product.getStock() + "): ");
        int stock = scanner.nextInt();
        scanner.nextLine();  // consume newline character

        System.out.print("새 카테고리 ID (기존: " + product.getCategoryId() + "): ");
        String categoryId = scanner.nextLine();

        product.setProductName(productName);
        product.setProductOption(productOption);
        product.setPrice(price);
        product.setStock(stock);
        product.setCategoryId(categoryId);

        product.updateProduct(product);
        System.out.println("상품이 수정되었습니다.");
    }

    private void deleteProduct() {
        System.out.println("상품 삭제");

        System.out.print("삭제할 상품 ID: ");
        String productId = scanner.nextLine();

        productDAO.deleteProduct(productId);
        System.out.println("상품이 삭제되었습니다.");
    }




}
}
	


	
	
	    

	    
