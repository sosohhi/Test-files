package com.sohee;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.*;


public class Planner {	
	
	private List<Menu> listmenu = new ArrayList<>();
	
	
	Scanner scanner = new Scanner(System.in);
	private int counter=0;
	
	Date today = new Date();
	SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd E요일");
	Menu menu = new Menu();
	int balance = 0;
		
	public void showMenu() throws Exception {
		while(true) {
			System.out.println("---------------------------------");
			System.out.print(day.format(today));
			System.out.println();
			System.out.println("1.오늘의 목표사용금액 :  ");
			System.out.println();
			System.out.println(
								"2. 등록"+ "\n"+
								"3. 수정" + "\n"+
								"4. 삭제" + "\n"+
								"5. 파일저장" + "\n" +
								"6. 파일불러오기" + "\n" +
								"7. 사용내역조회"+"\n"+ 
								"8. 잔액확인" + "\n" + 
								"9. 종료");
			System.out.println("---------------------------------");
			System.out.println("선택 > ");
			
			//키보드 입력
			String selectNo = scanner.nextLine();
			
			switch (selectNo){
				case "1":
					target();	//목표금액
					break;
				case "2":
					register(); //등록
					break;
				case "3":
					modified(); //수정
					break;
				case "4":
					delete(); //삭제
					break;
				case "5":
					saveFile(); //저장
					break;
				case "6":
					readFile(); // 불러오기
					break;
				case "7":		//조회
					showList();
					break;
				case "8":
					balcheck(); //잔액확인
					break;
				case "9":		//종료
					System.out.println("프로그램이 종료되었습니다.");
					return;						
			}
			
		}
		
	}
	
	// 목표금액 
	public void target() {
		
		System.out.print("오늘의 목표 금액은? :  ");
		menu.setGoal(Integer.parseInt(scanner.nextLine()));
		
	}
	
	public void register() {
		try {
			Menu menu = new Menu();
			menu.setPno(++counter);
		
			System.out.print("사용범주: ");
			menu.setCategory(scanner.nextLine());
			
			System.out.print("상품명: ");
			menu.setName(scanner.nextLine());
		
			System.out.print("가격: ");
			menu.setPrice(Integer.parseInt(scanner.nextLine()));
			
			listmenu.add(menu);
		}catch (Exception e) {
			System.out.println("등록 에러: " + e.getMessage());
		}
	}
	
	public void modified() {
		try {
			System.out.println("수정할 메뉴를 입력하세요 ");
			System.out.println("선택 : '목표/사용내역' ");
			String menuselect = scanner.nextLine();
			
			if (menuselect.equals("목표")) {
				System.out.print("새로운 목표 금액: ");
				Integer newGoal = Integer.parseInt(scanner.nextLine());
				menu.setGoal(newGoal);
			}else {
				System.out.println("수정할 등록 번호를 입력하세요: ");
				int Num = Integer.parseInt(scanner.nextLine());
				for (Menu menu : listmenu) {
					if(menu.getPno()==Num) {
						System.out.print("새로운 사용범주: ");
						String newCategory = scanner.nextLine();
						menu.setCategory(newCategory);
						
						System.out.print("새로운 상품명: ");
						String newName = scanner.nextLine();
						menu.setName(newName);
						
						System.out.print("새로운 가격: ");
						Integer newPrice = Integer.parseInt(scanner.nextLine());
						menu.setPrice(newPrice);
						
						System.out.print("사용내역이 수정되었습니다.");
						break;
						
					}
				}
			}
			
		}catch(Exception e){
			System.out.println("등록 에러: " + e.getMessage());
			
		}
		
			
	}
	
	public void delete() {
		System.out.println("입력 >> 전체삭제(a/A)/선택삭제(p/P)");
		String delSelect = scanner.nextLine();
		if (delSelect.equals("전체삭제")||delSelect.equals("a")||delSelect.equals("A")) {
			System.out.println("정말 삭제하시겠습니까? y/n");
			String delSelect_yn = scanner.nextLine();
			if (delSelect_yn.equals("y")||delSelect_yn.equals("Y")) {
				listmenu.clear();
				System.out.println("모든 내용이 삭제되었습니다.");
			}
			
			
		}else if(delSelect.equals("p")||delSelect.equals("P")) {
			try {
			System.out.println("삭제할 등록 번호를 입력하세요: ");
			int delNum = Integer.parseInt(scanner.nextLine());
			for (Menu menu : listmenu) {
				if(menu.getPno()==delNum) {
					listmenu.remove(menu);
					System.out.println(delNum + "번 내역이 삭제되었습니다.");					
				}
			}
			
			}catch(Exception e) {
				System.out.println("삭제에러" + e.getMessage());
				
			}
		}
		
	}
	
	public void saveFile() throws Exception {
		
		FileOutputStream fos = new FileOutputStream("./bin/com/Sohee/Planner.txt");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(listmenu);
		System.out.println("파일이 저장되었습니다.");
		oos.flush();
		oos.close();
		
	}
	
	public List<Menu> readFile() throws Exception{
			
		try( 
			FileInputStream fis = new FileInputStream("./bin/com/Sohee/Planner.txt");
			ObjectInputStream ois = new ObjectInputStream(fis)){
				
					
			listmenu = (List<Menu>) ois.readObject();
			counter = listmenu.size();
			System.out.println("파일을 가져오는데 성공했습니다.");
			return listmenu;
			
			
		}catch (Exception e) {
			
			return new ArrayList<>();
			
		}	
	}
	
	
	public void showList() {
		for (Menu m : listmenu) {
			System.out.println(
					m.getPno() + "\t" +
					m.getCategory()+ "\t" +
					m.getName()+ "\t" +
					m.getPrice()+ "\t"
					);
		}
		
	}
	
	public void balcheck() {
		
		int totalSpent = 0;
		for (Menu menu : listmenu) {
			totalSpent+=menu.getPrice();
		}
		
		int balance = menu.getGoal()-totalSpent;
		if (balance<0) {
			int bal_abs = Math.abs(balance);  
			System.out.println("목표금액을 " + bal_abs +"원 초과하였습니다.");
		}
		System.out.println("목표금액 : " + menu.getGoal());
		System.out.println("사용금액 : " + totalSpent);
		System.out.println("잔액은 " + balance + "원 입니다.");
		
	}
				
		
	

}
