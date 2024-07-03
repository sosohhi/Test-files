package com.sohee2;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;


import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class StudyPlan {
	
	private List<MyPlan> planilst = new ArrayList<>();
	
	Scanner scanner = new Scanner(System.in);
	Date today = new Date();
	SimpleDateFormat day = new SimpleDateFormat("yyyy/MM/dd E요일");
	SimpleDateFormat time = new SimpleDateFormat("hh시 mm분");

	
	MyPlan myplan = new MyPlan();
	int counter = 0;

	private LocalDateTime stime;

	private DateTimeFormatter timeFormat;
	
	// 메뉴 선택
	public void showMenu() throws Exception {
		while(true) {
			System.out.println("----------------------------------");
			System.out.println(day.format(today));
			System.out.println("1. 등록 | 2. 수정 | 3. 삭제 | 4. 저장 ");
			System.out.println("5. 조회 | 6. 종료 ");
			System.out.println("----------------------------------");
			System.out.println("선택 >> ");
			
			//키보드 입력
			String selNO = scanner.nextLine();
			switch (selNO) {
				case "1":
					register();
					break;
				case "2":
					modify();
					break;
				case "3":
					delete();
					break;
				case "4":
					saveFile();
					break;
				case "5":
					showList();
					break;
				case "6":
					lastCheck();
					System.out.println("프로그램을 종료합니다.");
					return;
			}
		}
	}
	public void register() throws Exception {
		try {
			
			myplan.setPno(++counter);
			
			
			System.out.print("오늘 공부할 과목은? ");
			myplan.setSubject(scanner.nextLine());
			
			System.out.println("공부 시작시간은 " + time.format(today) +" 입니다" );
			
			System.out.println("오늘 공부 목표는?");
			myplan.setLearning(scanner.nextLine());
			
			System.out.print("몇 시까지 공부할 것인가요? (숫자만 입력) ");
	        int hours = Integer.parseInt(scanner.nextLine());
	        stime = LocalDateTime.now();
			LocalDateTime endTime = stime.plusHours(hours);
	        timeFormat = DateTimeFormatter.ofPattern("hh시 mm분");;
			myplan.setEtime(timeFormat.format(endTime));

			planilst.add(myplan);
			
		}catch(Exception e) {
			System.out.println("등록 오류" + e.getMessage());
		}
	}
	
	public void modify() throws Exception{
		try {
			System.out.println("수정할 등록 번호를 입력하세요: ");
			int Num = Integer.parseInt(scanner.nextLine());
			for (MyPlan myplan  : planilst) {
					if(myplan.getPno()==Num) {
						System.out.print("새로운 과목: ");
						String newSubject = scanner.nextLine();
						myplan.setSubject(newSubject);
						
						System.out.print("새로운 공부목표: ");
						String newLearning = scanner.nextLine();
						myplan.setLearning(newLearning);
						
						System.out.print("새로운 공부시간: ");
						String newEtime = scanner.nextLine();
						myplan.setEtime(newEtime);
						
						System.out.print("사용내역이 수정되었습니다.");
						break;
						
					}
				}
			}catch(Exception e) {
			System.out.println("수정 오류" + e.getMessage());	
			}
		
		}
	
	public void delete() {
		System.out.println("입력 >> 전체삭제(a/A)/선택삭제(p/P)");
		String delSelect = scanner.nextLine();
		if (delSelect.equals("전체삭제")||delSelect.equals("a")||delSelect.equals("A")) {
			System.out.println("정말 삭제하시겠습니까? y/n");
			String delSelect_yn = scanner.nextLine();
			if (delSelect_yn.equals("y")||delSelect_yn.equals("Y")) {
				planilst.clear();
				System.out.println("모든 내용이 삭제되었습니다.");
			}
			
			
		}else if(delSelect.equals("선택삭제")||delSelect.equals("p")||delSelect.equals("P")) {
			try {
			System.out.println("삭제할 등록 번호를 입력하세요: ");
			int delNum = Integer.parseInt(scanner.nextLine());
			for (MyPlan myplan : planilst) {
				if(myplan.getPno()==delNum) {
					planilst.remove(myplan);
					System.out.println(delNum + "번 내역이 삭제되었습니다.");					
				}
			}
			
			}catch(Exception e) {
				System.out.println("삭제에러" + e.getMessage());
				
			}
		}
		
	}
	
	public void saveFile() throws Exception{
		
		FileOutputStream fos = new FileOutputStream("C:\\Users\\win11\\Desktop\\이클립스\\eclipse\\s_p\\StudyPlanner.db");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(planilst);
		System.out.println("파일이 저장되었습니다.");
		oos.flush();
		oos.close();
	}
	
	public void showList() throws Exception{
		for (MyPlan m : planilst) {
			System.out.println(
					m.getPno() + "\t" +
					m.getDate()+ "\t" +
					m.getSubject()+ "\t" +
					m.getSubject()+ "\t" +
					m.getLearning()+ "\t" +
					m.getEtime()+ "\t"
					);
		}
		
		
	}
	
	public void lastCheck() throws Exception{
		System.out.println("오늘의 목표를 달성하셨습니까? (y/n)");
		String checkSel = scanner.nextLine();
		if (checkSel.equals("y")||checkSel.equals("Y")) {
			System.out.println("축하합니다. 오늘도 고생하셨습니다.");
		}else if (checkSel.equals("n")||checkSel.equals("N")) {
			System.out.println("금일 목표치의 몇 퍼센트를 달성하셨습니까?");
			String per = scanner.nextLine();
			System.out.println("금일 목표치의"+ per + " 를 달성하였습니다. 고생하셨습니다");
		}
	
	}
}

