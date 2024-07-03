package com.soso;

import java.util.Scanner;

public class CustomerManagement {
	Scanner scanner = new Scanner(System.in);
	public void manageCustomers() {
		while (true) {
            System.out.println("고객 관리");
            System.out.println("1. 고객 정보 조회");
            System.out.println("2. 고객 정보 삭제");
            System.out.println("3. 고객 리스트");
            System.out.println("4. 돌아가기");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline character
            switch (choice) {
                case 1:
                	viewCustomer();
                     break;
                case 2:
                	deleteCustomer();
                    break;
                case 3:
                	listCustomers();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도해주세요.");
            }
        }
	}
	private void viewCustomer() {
        System.out.println("고객 정보 조회");

        System.out.print("조회할 고객 ID: ");
        String customerId = scanner.nextLine();

        Customer customer = customerDAO.getCustomerById(customerId);
        if (customer != null) {
            System.out.println(customer);
        } else {
            System.out.println("해당 ID의 고객이 존재하지 않습니다.");
        }
    }

    private void deleteCustomer() {
        System.out.println("고객 정보 삭제");

        System.out.print("삭제할 고객 ID: ");
        String customerId = scanner.nextLine();

        boolean success = customerDAO.deleteCustomer(customerId);
        if (success) {
            System.out.println("고객 정보가 삭제되었습니다.");
        } else {
            System.out.println("해당 ID의 고객이 존재하지 않습니다.");
        }
    }

    private void listCustomers() {
        System.out.println("고객 리스트");
        List<Customer> customers = customerDAO.getAllCustomers();
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

}
