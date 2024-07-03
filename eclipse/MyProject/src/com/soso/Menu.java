package com.soso;

import java.util.Scanner;

public class Menu {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		Customer customer = new Customer();
		Manager manager = new Manager();
		
		while(true) {
		System.out.println("┌──────────────────────────┐");
		System.out.println("│	Java ShoppingMall	│");
		System.out.println("└──────────────────────────┘");
		System.out.println("ID: ");
		String ID = scanner.nextLine().trim();
		System.out.println("PW: ");
		String Password = scanner.nextLine().trim();
		
		if (manager.login(ID, Password)) {
            manager.showManagerMenu();
        } else if (customer.login(ID, Password)) {
        	customer.showCustomerMenu();
        } else {
            System.out.println("로그인 실패. 회원가입을 진행할까요?(Y/N)");
            String registerYN = scanner.nextLine().trim().toUpperCase();
            if (registerYN.equals("Y")) {
            	customer.registerCustomer();
            }else {
            	return;
            }
            
        }
		}
	}
	
	
	
}
		
		

