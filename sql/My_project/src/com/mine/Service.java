package com.mine;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

import com.mine.utils.DBManager;

public class Service {
	Scanner scanner = new Scanner(System.in);
	
	private String selectDate() {
		return """
				SELECT e.startdate, e.enddate, e.hourlywave FROM employeesManager e
                JOIN monthlySalary m ON e.seq = m.employeesManager_seq
                WHERE e.name = ?
				""";
	}	
	
	/**
	 * 설명 : 직원 등록
	 */
	
	public int register() {
		//1. 개인정보 입력
		
		Connection conn = DBManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int resultRows =0;
			
		System.out.println("성명: ");
		String name = scanner.nextLine();
		
		int age = 0;
		while(true) {
			System.out.println("나이:  ");
			try {
				age = Integer.parseInt(scanner.nextLine());
				break;
			}catch(NumberFormatException e) {
				System.out.println("잘못된 값을 입력하셨습니다. 숫자를 입력해주세요");
			}
		}
		
		
		
		System.out.println("주소: ");
		String address = scanner.nextLine();
		
		String phoneNum = null;
		while(true) {
			System.out.println("연락처('-'없이 11자리 입력):  ");
			try {
				phoneNum = scanner.nextLine();
				break;
			}catch(Exception e) {
				System.out.println("잘못된 값을 입력하셨습니다. 11자리 숫자를 입력해주세요");
			}
		}
		
		String registrasionNo = null;
		while(true) {
			System.out.println("주민등록번호/외국인등록번호('-'없이 13자리 입력): ");
			try {
				registrasionNo = scanner.nextLine();
				break;
			}catch(Exception e) {
				System.out.println("잘못된 값을 입력하셨습니다. 13자리 숫자를 입력해주세요");
			}
		}
		System.out.println("국적: ");
		String country = scanner.nextLine();
		
		String passportNo = null;
		String status = null;
		String stayin =null;
		
		if (!country.equals("한국") && !country.equals("대한민국")) {
			System.out.println("여권번호 : ");
			passportNo = scanner.nextLine();
			System.out.println("체류자격 : ");
			status = scanner.nextLine();
			System.out.println("체류기간 : ");
			stayin = scanner.nextLine();	
		}
		String startdatestr = null;
		LocalDate startdate = null;
		while(true) {
			System.out.println("근무시작일자('yyyyMMdd'): ");
			try {
				startdatestr = scanner.nextLine();
				startdate = LocalDate.parse(startdatestr, DateTimeFormatter.ofPattern("yyyyMMdd"));
				break;
			}catch(Exception e) {
				System.out.println("잘못된 값을 입력하셨습니다. 형식에 맞게 입력해주세요[yyyyMMdd]");
			}
		} 
		String enddatestr = null;
		LocalDate enddate = null;
		while(true) {
			System.out.println("근무종료일자('yyyyMMdd'): ");
			try {
				enddatestr = scanner.nextLine();
				enddate = LocalDate.parse(enddatestr, DateTimeFormatter.ofPattern("yyyyMMdd"));
				break;
			}catch(Exception e) {
				System.out.println("잘못된 값을 입력하셨습니다. 형식에 맞게 입력해주세요[yyyyMMdd]");
			}
		}
		
		System.out.print("시급: ");
        double hourlywave = scanner.nextDouble();
        scanner.nextLine(); 
         
        System.out.print("관련 경력: ");
        String career = scanner.nextLine(); 
       
        // 직원 정보를 데이터베이스에 저장
        String insertSql = """
				INSERT INTO employeesManager(seq, name, age, address, phoneNum,
											registrasionNo, country, passportNo,
											status, stayin, startdate, enddate, hourlywave,career) 
				VALUES (seq_employeesManager_no.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)				
				""";
        try {
        pstmt = conn.prepareStatement(insertSql,new String[] {"seq"});
		pstmt.setString(1, name);		
		pstmt.setInt(2, age);
		pstmt.setString(3, address);
		pstmt.setString(4, phoneNum);
		pstmt.setString(5, registrasionNo);
		pstmt.setString(6, country);
		pstmt.setString(7, passportNo);
		pstmt.setString(8, status);
		pstmt.setString(9, stayin);
		pstmt.setDate(10, Date.valueOf(startdate));
        pstmt.setDate(11, Date.valueOf(enddate));
        pstmt.setDouble(12, hourlywave);
        pstmt.setString(13, career);
        
		resultRows = pstmt.executeUpdate();
		if (resultRows > 0) {
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int empSeq = rs.getInt(1);
                calculateMonthlySalary(empSeq, hourlywave, startdate, enddate);
            }
            System.out.println("직원 정보가 성공적으로 등록되었습니다.");
        }
		
		}catch(SQLException se) {
			System.out.println("등록에러 발생 -> " + se.getMessage());
		} finally {
			DBManager.dbClose(conn, pstmt,rs);
		}
		return resultRows;
		}
			
	  private void calculateMonthlySalary(int empSeq,double hourlywave, LocalDate startdate, LocalDate enddate)  {
	        String insertSQL = """ 
	        	INSERT INTO monthlySalary (seq, employeesManager_seq ,year,month, totalSalary) 
	            VALUES (seq_month_no.NEXTVAL, ?,?, ?,?) """;
	        
	        Connection conn = null;
			PreparedStatement pstmt = null;

	        try {
	        	conn = DBManager.getConnection();
	        	pstmt = conn.prepareStatement(insertSQL);
	        	
	        	LocalDate currentdate = startdate;
	            while (!currentdate.isAfter(enddate)) {
	                int year = currentdate.getYear();
	                int month = currentdate.getMonthValue();
	                LocalDate startOfMonth = LocalDate.of(year, month, 1);
	                LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
	                
	                if (startOfMonth.isBefore(startdate)) {
	                    startOfMonth = startdate;
	                }
	                if (endOfMonth.isAfter(enddate)) {
	                    endOfMonth = enddate;
	                }
	                
	                long daysWorked = ChronoUnit.DAYS.between(startOfMonth, endOfMonth) + 1;
	                double basicSalary = hourlywave * daysWorked * 8;;
	                double overtimePay = daysWorked * hourlywave * 0.5;
	                double totalSalary = basicSalary + overtimePay;
	                
	                pstmt.setInt(1, empSeq);
	                pstmt.setInt(2, year);
	                pstmt.setInt(3, month);
	                pstmt.setDouble(4, totalSalary);

	                pstmt.executeUpdate();
	                
	                currentdate  = currentdate.plusMonths(1).withDayOfMonth(1);
	            }
	            System.out.println("월별 급여가 성공적으로 계산되어 저장되었습니다.");
	
	        }catch(SQLException se) {
	        System.out.println("급여 계산 중 에러 발생 -> " + se.getMessage());
	        }finally {
			  DBManager.dbClose(conn, pstmt, null);
	        }
			
	  	}
	
	/**
	 * 설명 : 직원목록조회
	 */
	public void All_list() {
		
		Connection conn = DBManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int countRows = 0;
		
		// 게시판 목록조회 화면구성
		System.out.println("-------------------------------------------------------------");
		System.out.println("사원번호		성명    	   	국적        	  연락처         	  ");
		System.out.println("-------------------------------------------------------------");
		String selectSql = "SELECT seq, name, country, phoneNum FROM employeesManager ORDER BY seq";
		try {
			pstmt = conn.prepareStatement(selectSql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				countRows++;
				
				String phoneNum = rs.getString("phoneNum");
				
				if (phoneNum.length() == 11) {
				    phoneNum = phoneNum.substring(0, 3) + "-" +
				               phoneNum.substring(3, 7) + "-" +
				               phoneNum.substring(7, 11);
				}
				System.out.println(rs.getString("seq") +"\t" +  "    "+
						rs.getString("name") + "\t" +"        "+
						rs.getString("country")+"\t" +"     "+
						phoneNum
						) ;
			}
			if (countRows ==0) {
				System.out.println("직원목록이 존재하지 않습니다.");
			}
		}catch(SQLException se) {
			se.printStackTrace();
			System.out.println("직원목록 불러오기오류 -> " + se.getMessage());
		} finally {
			DBManager.dbClose(conn, pstmt,rs);
		}
	}
	
	/**
	 * 설명 : 직원상세조회
	 */
	public void inquiry_emp() {
		System.out.println("조회할 직원의 이름을 입력하시오: ");
	    String selname = scanner.nextLine();
	    Connection conn = null;
	    PreparedStatement pstmt1 = null;
	    PreparedStatement pstmt2 = null;
	    PreparedStatement pstmt3 = null;
	    PreparedStatement pstmt4 = null;
	    ResultSet rs1 = null;
	    ResultSet rs2 = null;
	    ResultSet rs3 = null;
	    ResultSet rs4 = null;
	    
	    try {
	        conn = DBManager.getConnection();
	       
	        // 내국인 상세조회 화면
	        String selectSql1 = """
	                SELECT seq,name,age,address,phoneNum,registrasionNo,country,startdate,enddate,hourlywave,career
	                FROM employeesManager 
	                WHERE name = ? AND country IN ('한국', '대한민국')
	                """;
	        
	        pstmt1 = conn.prepareStatement(selectSql1);
	        pstmt1.setString(1,selname);
	        rs1 = pstmt1.executeQuery();
	        StringBuilder result = new StringBuilder();
	        
	        int rowCount1 = 0;
	        
	        while (rs1.next()) {
	            rowCount1++;
	            String phoneNum = rs1.getString("phoneNum");
	            if (phoneNum.length() == 11) {
	                phoneNum = phoneNum.substring(0, 3) + "-" +
	                           phoneNum.substring(3, 7) + "-" +
	                           phoneNum.substring(7, 11);
	            }
	            String regNo =  rs1.getString("registrasionNo");
	            char gender =  regNo.charAt(6);
	            String genderStr = (gender == '1' || gender == '3') ? "Male" : "Female";
	            if (regNo.length() == 13) {
	                regNo = regNo.substring(0, 6) + "-" +
	                        regNo.substring(6, 13);
	            }
	            System.out.println("----------------------------------------------------------------");
	            System.out.print("사번 " + rs1.getInt("seq")+"\t");
	            System.out.print("성명 " + rs1.getString("name")+"  \t");
	            System.out.print("나이 " + rs1.getInt("age")+"\t " + "\t");
	            System.out.println("주민번호 " + regNo + "  성별: "+genderStr );
	            System.out.println();
	            System.out.print("연락처 " + phoneNum +"\t"+"\t");
	            System.out.println("거주지 " + rs1.getString("address"));
	            System.out.println();
	            System.out.print("고용일자 " +  rs1.getString("startdate")+"\t");
	            System.out.println("고용만료일자 " + rs1.getString("enddate"));
	            System.out.println();
	            System.out.print("시급 " + rs1.getInt("hourlywave")+"\t");
	            System.out.println("경력 " + rs1.getString("career"));
	            System.out.println("----------------------------------------------------------------");
	        }
	        
	        if(rowCount1 == 0){
	            System.out.println("내국인 직원 상세조회 결과가 없습니다.");
	        }else if(rowCount1 >1){
	        	System.out.println("동명이인이 존재합니다. 조회할 직원의 사번을 입력해주세요.");
	        	int seq = Integer.parseInt(scanner.nextLine());
	        	
	        	String selectSql2 = """
		                SELECT seq,name,age,address,phoneNum,registrasionNo,country,startdate,enddate,hourlywave,career
		                FROM employeesManager 
		                WHERE seq = ?
		                """;
		        
		        pstmt2 = conn.prepareStatement(selectSql2);
		        pstmt2.setInt(1,seq);
		        rs2 = pstmt2.executeQuery();    
		        
		        if (rs2.next()) {
		            String phoneNum = rs2.getString("phoneNum");
		            if (phoneNum.length() == 11) {
		                phoneNum = phoneNum.substring(0, 3) + "-" +
		                           phoneNum.substring(3, 7) + "-" +
		                           phoneNum.substring(7, 11);
		            }
		            String regNo =  rs2.getString("registrasionNo");
		            char gender =  regNo.charAt(6);
		            
		            String genderStr = (gender == '1' || gender == '3') ? "Male" : "Female";
		            String formattedRegNo;
		            if (regNo.length() == 13) {
		                regNo = regNo.substring(0, 6) + "-" +
		                        regNo.substring(6, 13);
		            }
		            System.out.println("-----------------------------------------------------------");
		            System.out.print("사번 " + rs2.getInt("seq")+"\t");
		            System.out.print("성명 " + rs2.getString("name")+"  \t");
		            System.out.print("나이 " + rs2.getInt("age")+"\t " + "\t");
		            System.out.println("주민번호 " + regNo + "  성별: "+genderStr );
		            System.out.println();
		            System.out.print("연락처 " + phoneNum +"\t"+"\t");
		            System.out.println("거주지 " + rs2.getString("address"));
		            System.out.println();
		            System.out.print("고용일자 " +  rs2.getString("startdate")+"\t");
		            System.out.println("고용만료일자 " + rs2.getString("enddate"));
		            System.out.println();
		            System.out.print("시급 " + rs2.getInt("hourlywave")+"\t");
		            System.out.println("경력 " + rs2.getString("career"));
		            System.out.println("-----------------------------------------------------------");
		        }else {
		        	System.out.println("해당 사번을 가진 직원이 없습니다.");
		        }
	        }
	        
	    } catch (SQLException se) {
	        se.printStackTrace();
	        System.out.println("내국인 상세조회 실행오류 -> " + se.getMessage());
	    } finally {
	        // 첫 번째 SQL 쿼리에 대한 리소스 정리
	        DBManager.dbClose(null, pstmt1, rs1);
	        DBManager.dbClose(null, pstmt2, rs2);
	    }
	    
	    // 외국인 상세조회 화면
	    try {
	        conn = DBManager.getConnection(); // 새로운 연결 생성
	    
	        String selectSql3 = """
	            SELECT seq, name, age, address, phoneNum, registrasionNo, passportNo, status, stayin, startdate, enddate, hourlywave, career
	            FROM employeesManager 
	            WHERE name = ? AND country NOT IN('한국','대한민국')
	            """;
	        pstmt3 = conn.prepareStatement(selectSql3);
	        pstmt3.setString(1,selname);
	        rs3 = pstmt3.executeQuery();
	        
	        int rowCount2 = 0;
	        
	        while (rs3.next()) {
	            rowCount2++;
	            String phoneNum = rs3.getString("phoneNum");
	            if (phoneNum.length() == 11) {
	                phoneNum = phoneNum.substring(0, 3) + "-" +
	                           phoneNum.substring(3, 7) + "-" +
	                           phoneNum.substring(7, 11);
	            }
	            String regNo =  rs3.getString("registrasionNo");
	            char gender =  regNo.charAt(6);
	           
	            String genderStr = (gender == '7') ? "Male" : "Female";
	            if (regNo.length() == 13) {
	                regNo = regNo.substring(0, 6) + "-" +
	                        regNo.substring(6, 13);
	            }
	            System.out.println("-----------------------------------------------------------");
	            System.out.print("사번 " + rs3.getInt("seq")+"\t");
	            System.out.print("성명 " + rs3.getString("name")+"\t" +"\t");
	            System.out.print("나이 " + rs3.getInt("age") + "\t" + "\t");
	            System.out.println("외국인등록번호 " + regNo + "  성별: " + genderStr);
	            System.out.println();
	            System.out.print ("여권번호 " + rs3.getString("passportNo") + "\t" + "\t");
	            System.out.print("체류자격 " + rs3.getString("status")+"\t");
	            System.out.println("체류기간 " + rs3.getString("stayin"));
	            System.out.println();
	            System.out.print("연락처 " + phoneNum +"\t"+"\t");
	            System.out.println("거주지 " + rs3.getString("address"));
	            System.out.println();
	            System.out.print("고용일자 " +  rs3.getString("startdate")+"\t");
	            System.out.println("고용만료일자 " +rs3.getString("enddate"));
	            System.out.println();
	            System.out.print("시급 " + rs3.getInt("hourlywave")+"\t"+"\t");
	            System.out.println("경력 " + rs3.getString("career"));
	            System.out.println("-----------------------------------------------------------");
	        }
	        
	        if (rowCount2 == 0) {
	            System.out.println("외국인 직원 상세조회 결과가 없습니다");
	        }else if (rowCount2 >1) {
	        	System.out.println("동명이인이 존재합니다. 조회할 직원의 사번을 입력해주세요.");
	        	int seq = Integer.parseInt(scanner.nextLine());
	        	
	        	String selectSql4 = """
	    	            SELECT seq, name, age, address, phoneNum, registrasionNo, passportNo, status, stayin, startdate, enddate, hourlywave, career
	    	            FROM employeesManager 
	    	            WHERE seq = ?;
	    	            """;
	    	        pstmt4 = conn.prepareStatement(selectSql4);
	    	        pstmt4.setInt(1,seq);
	    	        rs4 = pstmt4.executeQuery();    
	    	        
	    	        while (rs4.next()) {
	    	            String phoneNum = rs4.getString("phoneNum");
	    	            if (phoneNum.length() == 11) {
	    	                phoneNum = phoneNum.substring(0, 3) + "-" +
	    	                           phoneNum.substring(3, 7) + "-" +
	    	                           phoneNum.substring(7, 11);
	    	            }
	    	            String regNo =  rs4.getString("registrasionNo");
	    	            char gender =  regNo.charAt(6);
	    	            String genderStr = (gender == '7') ? "Male" : "Female";
	    	            if (regNo.length() == 13) {
	    	                regNo = regNo.substring(0, 6) + "-" +
	    	                        regNo.substring(6, 13);
	    	            }
	    	            System.out.println("-----------------------------------------------------------");
	    	            System.out.print("사번 " + rs4.getInt("seq")+"\t");
	    	            System.out.print("성명 " + rs4.getString("name")+"\t" +"\t");
	    	            System.out.print("나이 " + rs4.getInt("age") + "\t" + "\t");
	    	            System.out.println("외국인등록번호 " + regNo + "  성별: " + genderStr);
	    	            System.out.println();
	    	            System.out.print ("여권번호 " + rs4.getString("passportNo") + "\t" + "\t");
	    	            System.out.print("체류자격 " + rs4.getString("status")+"\t");
	    	            System.out.println("체류기간 " + rs4.getString("stayin"));
	    	            System.out.println();
	    	            System.out.print("연락처 " + phoneNum +"\t"+"\t");
	    	            System.out.println("거주지 " + rs4.getString("address"));
	    	            System.out.println();
	    	            System.out.print("고용일자 " +  rs4.getString("startdate")+"\t");
	    	            System.out.println("고용만료일자 " +rs4.getString("enddate"));
	    	            System.out.println();
	    	            System.out.print("시급 " + rs4.getInt("hourlywave")+"\t"+"\t");
	    	            System.out.println("경력 " + rs4.getString("career"));
	    	            System.out.println("-----------------------------------------------------------");
	    	        }
	        }
	    } catch (SQLException se) {
	        se.printStackTrace();
	        System.out.println("외국인 상세조회 실행오류 -> " + se.getMessage());     
	    } finally {
	        // 두 번째 SQL 쿼리에 대한 리소스 정리
	        DBManager.dbClose(conn, pstmt3, rs3);
	        DBManager.dbClose(conn, pstmt4, rs4);
	    }
	}

	/**
	 * 설명 : 직원 이력 수정
	 */
	
	public int update_emp() {
	    Connection conn = DBManager.getConnection();
	    
	    String conSqlByName  = "SELECT seq FROM employeesManager WHERE name = ?";
	    String selectSQL = selectDate();
	    
	    
	    String updateAddress = """
	             UPDATE employeesManager SET address = ? WHERE seq = ? """;
	    String updatePhoneNum = """
				 UPDATE employeesManager SET phoneNum = ? WHERE seq = ? """;
		String updateCountry = """
				 UPDATE employeesManager SET country = ? WHERE seq = ? """;
		String updateStatus= """
				 UPDATE employeesManagerSET status = ? WHERE seq = ? """;
		String updateStayin = """
				 UPDATE employeesManagerSET stayin = ? WHERE seq = ? """;
		String updateStartDate = """
				 UPDATE employeesManager SET startdate = ?  WHERE seq = ? """;
		String updateEndDate = """
				 UPDATE employeesManager SET enddate = ? WHERE seq = ? """;
		String updateHourlyWave = """
				 UPDATE employeesManager SET hourlywave = ? WHERE seq = ? """;
		String updateCareer = """
				 UPDATE employeesManagerSET career = ? WHERE seq = ? """;
		
	    System.out.println("수정할 직원의 이름을 입력하시오. ");
	    String selectName = scanner.nextLine();

	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    int resultRows = 0;
	    int seq = 0;

	    try {
	        pstmt = conn.prepareStatement(conSqlByName);
	        pstmt.setString(1, selectName);
	        rs = pstmt.executeQuery();
	        
	        if(!rs.next()) {
        		System.out.println("수정할 직원의 이름을 찾을 수 없습니다");
        		return -1;
	        }else {
	        	seq = rs.getInt("seq");
	        	
	        	// 기존의 값 가져오기
	    	    LocalDate startDate = null;
	    	    LocalDate endDate = null;
	    	    int hourlyWave = 0;

	    	    pstmt = conn.prepareStatement(selectSQL);
	    	    pstmt.setString(1, selectName);
	    	    rs = pstmt.executeQuery();
	    	    if (rs.next()) {
	    	        startDate = rs.getDate("startdate").toLocalDate();
	    	        endDate = rs.getDate("enddate").toLocalDate();
	    	        hourlyWave = rs.getInt("hourlywave");
	    	    }
	    	    while (true) {
	    	        System.out.println("수정하고자 하는 메뉴를 선택해주세요");
	    	        System.out.println("--------------------------------");
	    	        System.out.println("1. 주소   2. 연락처   3. 국적");
	    	        System.out.println("4. 체류자격   5. 체류기간");
	    	        System.out.println("6. 고용일자   7. 고용만료일자");
	    	        System.out.println("8. 시급   9. 경력");
	    	        System.out.println("10. 수정 종료");
	    	        System.out.println("--------------------------------");
	    	        
	    	        int selectUpNum = scanner.nextInt();
	    	        scanner.nextLine();

	    	        switch (selectUpNum) {
	    	            case 1:
	    	               pstmt = conn.prepareStatement(updateAddress);
	    	               System.out.println("변경된 주소를 입력하시오: ");
	    	               String newaddress = scanner.nextLine();
	    	               pstmt.setString(1, newaddress); // address 매개변수
	    	               pstmt.setInt(2, seq);
	    	               break;
	    	             case 2:
	    	               pstmt = conn.prepareStatement(updatePhoneNum);
	    	               System.out.println("변경된 전화번호를 입력하시오: ");
	    	               String newphoneNum = scanner.nextLine();
	    	               pstmt.setString(1, newphoneNum); // phoneNum 매개변수
	    	               pstmt.setInt(2, seq);
	    	               break;
	    	              case 3:
	    	               pstmt = conn.prepareStatement(updateCountry);
	    	               System.out.println("변경된 국적을 입력하시오: ");
	    	               String newcountry = scanner.nextLine();
	    	               pstmt.setString(1, newcountry); // country 매개변수
	    	               pstmt.setInt(2, seq);
	    	               break;
	    	              case 4:
	    	                pstmt = conn.prepareStatement(updateStatus);
	    	                System.out.println("변경된 체류자격 정보를 입력하시오: ");
	    	                String newstatus = scanner.nextLine();
	    	                pstmt.setString(1, newstatus); // status 매개변수
	    	                pstmt.setInt(2, seq);
	    	                break;
	    	              case 5:
	    	                pstmt = conn.prepareStatement(updateStayin);
	    	                System.out.println("변경된 체류기간을 입력하시오: ");
	    	                String newstay_in = scanner.nextLine();
	    	                pstmt.setString(1, newstay_in); // stayin 매개변수
	    	                pstmt.setInt(2, seq);
	    	                break;
	    	              case 6:
	    	                pstmt = conn.prepareStatement(updateStartDate);
	    	                System.out.println("변경된 고용일자를 입력하시오: ");
	    	                String newStartdatestr = scanner.nextLine();
	    	                LocalDate newStartDate = LocalDate.parse(newStartdatestr, DateTimeFormatter.ofPattern("yyyyMMdd"));
	    	                pstmt.setDate(1, Date.valueOf(newStartDate)); // startdate 매개변수
	    	                pstmt.setInt(2, seq);
	    	                break;
	    	              case 7:
	    	            	pstmt = conn.prepareStatement(updateEndDate);
	    	                System.out.println("변경된 고용만료일자를 입력하시오: ");
	    	                String newEndDateStr = scanner.nextLine();
	    	                LocalDate newEndDate = LocalDate.parse(newEndDateStr, DateTimeFormatter.ofPattern("yyyyMMdd"));
	    	                pstmt.setDate(1, Date.valueOf(newEndDate)); // enddate 매개변수
	    	                pstmt.setInt(2, seq);
	    	                break;
	    	              case 8:
	    	                pstmt = conn.prepareStatement(updateHourlyWave);
	    	                System.out.println("변경된 시급을 입력하시오: ");
	    	                String newhourlywave = scanner.nextLine();
	    	                pstmt.setInt(1, Integer.parseInt(newhourlywave)); // hourlywave 매개변수
	    	                pstmt.setInt(2, seq);
	    	                break;
	    	              case 9:
	    	                pstmt = conn.prepareStatement(updateCareer);
	    	                System.out.println("변경된 경력을 입력하시오: ");
	    	                String newcareer = scanner.nextLine();
	    	                pstmt.setString(1, newcareer); // career 매개변수
	    	                pstmt.setInt(2, seq);
	    	                break;
	    	              case 10:
	    	                System.out.println("[수정 종료]");
	    	                return -1;
	    	              default:
	    	                System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
	    	                continue;
	    	            }
	    	        	resultRows = pstmt.executeUpdate(); // 수정 쿼리 실행
	    	        	System.out.println("직원 정보가 성공적으로 수정되었습니다.");
	    	    	}		
	        	}

	    } catch (SQLException se) {
	        System.out.println("수정 도중 에러 발생 -> " + se.getMessage());
	    } finally {
	        DBManager.dbClose(conn, pstmt, null);
	    }
	    return resultRows;
	}

	 
	/*
	 * 설명 : 직원 정보 삭제		
	 */
	public int delete_emp() {
		
		System.out.println("전체삭제(A) / 부분삭제(P)");
		String select_ap = scanner.nextLine().trim().toUpperCase();;
		
		Connection conn = DBManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int resultRow = 0;
		
		try {
			if (select_ap.equals("A")) {
				System.out.println("정말 전체 직원목록을 삭제하시겠습니까?(Y/N)" );
				String confirmYN = scanner.nextLine().trim().toUpperCase();
				if (!confirmYN.equals("Y"))
					return-1;
				
				String deleteSqlSal = "DELETE FROM monthlySalary";
				pstmt = conn.prepareStatement(deleteSqlSal);
				resultRow += pstmt.executeUpdate();
				
				String deleteSql = "DELETE From employeesManager";
				pstmt = conn.prepareStatement(deleteSql);
				resultRow += pstmt.executeUpdate();	
				
				System.out.println(resultRow +"개의 직원목록이 삭제되었습니다.");		
				
			}else if (select_ap.equals("P")) {
				System.out.println("삭제할 직원의 이름을 입력하시오. ");
				String selName = scanner.nextLine();
				
				String conSql = "SELECT COUNT(*) FROM employeesManager WHERE name = ?";
				pstmt = conn.prepareStatement(conSql);
		        pstmt.setString(1, selName);
		        rs = pstmt.executeQuery();
		        
		        if (rs.next()) {
		            resultRow = rs.getInt(1);    // count(*)값 가져오기

		            if (resultRow == 0) {
		                System.out.println("삭제할 직원의 이름을 찾을 수 없습니다.");
		                return-1;
		                
		            } else if(resultRow > 1) {
		            	System.out.println("동명이인이 존재합니다.삭제할 직원의 사번을 입력해주세요.");
		            	int seq = Integer.parseInt(scanner.nextLine());
		            	
		            	String conSql2 = "SELECT COUNT(*) FROM employeesManager WHERE seq = ?";
		            	pstmt = conn.prepareStatement(conSql2);
		     	        pstmt.setInt(1, seq);
		     	        rs = pstmt.executeQuery();
		            	
		            	if(!rs.next()||rs.getInt(1) == 0) {
		            		System.out.println("해당 사번을 가진 직원이 없습니다");
		            		return-1;
		            	}
		            	
		            	System.out.println("정말 " +selName + "의 정보를 삭제하시겠습니까?(Y/N)" );
						String confirmYN = scanner.nextLine().trim().toUpperCase();
						if (!confirmYN.equals("Y"))
							return-1;
						
						 // 해당 직원의 월급 정보 삭제
			            String deleteSalSql = "DELETE FROM monthlySalary WHERE employeesManager_seq = ?";
			            pstmt = conn.prepareStatement(deleteSalSql);
			            pstmt.setInt(1, seq);
			            resultRow += pstmt.executeUpdate();

			            // 직원 정보 삭제
			            String deleteSql = "DELETE FROM employeesManager WHERE seq = ?";
			            pstmt = conn.prepareStatement(deleteSql);
			            pstmt.setInt(1, seq);
			            resultRow += pstmt.executeUpdate();
			            
			            if (resultRow > 0) {
			                System.out.println(selName + "의 정보가 성공적으로 삭제되었습니다.");
			            } else {
			                System.out.println("해당 사번을 가진 직원을 찾을 수 없습니다.");	         
						}
			            
		            } else {
		            	System.out.println("정말 " +selName + "의 정보를 삭제하시겠습니까?(Y/N)" );
						String confirmYN = scanner.nextLine().trim().toUpperCase();
						if (!confirmYN.equals("Y"))
							return-1;
						
						String deleteSalSql = "DELETE FROM monthlySalary WHERE employeesManager_seq = (SELECT seq FROM employeesManager WHERE name = ?)";
                        pstmt = conn.prepareStatement(deleteSalSql);
                        pstmt.setString(1, selName);
                        resultRow = pstmt.executeUpdate();

                        String deleteSql = "DELETE FROM employeesManager WHERE name = ?";
                        pstmt = conn.prepareStatement(deleteSql);
                        pstmt.setString(1, selName);
                        resultRow = pstmt.executeUpdate();
                        
                        if (resultRow >0) {
                        	System.out.println(selName + "의 정보가 성공적으로 삭제되었습니다.");
                        }else {
                        	System.out.println("해당 이름의 직원을 찾을 수 없습니다.");
                        }	
		            }
		        } 
			}
		}catch(SQLException se) {
			se.printStackTrace();
			System.out.println("삭제 실행오류 -> " + se.getMessage());
		}finally {
				DBManager.dbClose(conn, pstmt,rs);
		}
		return resultRow;
	}
	
	public void salary_emp() {
		// 1. 급여 전체조회 / 상세조회 여부 확인
		System.out.println("이번달 전체 직원 급여내역 확인(A) / 급여 내역 선택 확인(P)");
		String select_ap = scanner.nextLine().trim().toUpperCase();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int countRows = 0;

		
	    //현재 날짜
	    LocalDate currentDate = LocalDate.now();
        Month currentMonth = currentDate.getMonth();
        int currentYear = currentDate.getYear();	// int연도 가져오기
        
		// 급여 목록조회 화면구성
		
		try {
			conn =  DBManager.getConnection();
			
			if (select_ap.equals("A")) {
			
			String selectAll = """
	                 SELECT b.seq As b_seq, a.seq AS a_seq, a.name, b.totalSalary,b.month
					 FROM employeesManager a
					 JOIN monthlySalary b ON a.seq = b.employeesManager_seq
					 WHERE b.year = ? AND b.month = ? 
					 ORDER BY b.seq
					 """;
			pstmt = conn.prepareStatement(selectAll);
			pstmt.setInt(1, currentYear);
			pstmt.setInt(2, currentMonth.getValue());
			rs = pstmt.executeQuery();
			
			// NumberFormat 인스턴스 생성
			NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.KOREA);
			
			while(rs.next()) {
				if (countRows == 0) {
				System.out.println("-----------------------------------------------------");
				System.out.println( currentYear +"년" + currentMonth.getValue() + "월달 급여 목록");
				System.out.println("번호	사원번호	성명		급여");
				System.out.println("-----------------------------------------------------");
				}
				
				countRows++;
				int totalSalary = rs.getInt("totalSalary");
                String formattedSalary = numberFormat.format(totalSalary) + "원";
				
                System.out.println(rs.getString("b_seq") + "\t" +
	                        	   rs.getString("a_seq") + "\t" +
	                        	   rs.getString("name") + "\t\t" +
	                        	   formattedSalary);
			}
			
			if (countRows ==0) {
				System.out.println("이번달 급여 내역이 존재하지 않습니다.");
			}
			
		}else if (select_ap.equals("P")) {
			System.out.println("조회할 직원의 이름을 입력하세요: ");
			String employeeName = scanner.nextLine();
			
			String conSql = "SELECT COUNT(*) FROM employeesManager WHERE name = ?";
		    String conSql2 = "SELECT COUNT(*) FROM employeesManager WHERE seq = ?";

			try {
			     pstmt = conn.prepareStatement(conSql);
			     pstmt.setString(1, employeeName);
			     rs = pstmt.executeQuery();
			        
			     if (rs.next()) {
			         countRows = rs.getInt(1);    // count(*)값 가져오기

		            if (countRows == 0) {
		                System.out.println("조회할 직원의 이름을 찾을 수 없습니다.");
		                return;
			                
		            }else if(countRows > 1) {
		            	System.out.println("동명이인이 존재합니다.조회할 직원의 사번을 입력해주세요.");
		            	int seq = Integer.parseInt(scanner.nextLine());
			            	
			            pstmt = conn.prepareStatement(conSql2);
			     	    pstmt.setInt(1, seq);
			     	    rs = pstmt.executeQuery();
			            	
			            if(!rs.next()) {
			            	System.out.println("해당 사번을 가진 직원이 없습니다");
			            	return;
			            }
			         }
			      }
				}catch(SQLException e) {
					e.printStackTrace();
	                System.out.println("직원 조회 오류 -> " + e.getMessage());
	                return;
				}
			
				String selectOne = """
						SELECT b.seq AS b_seq, a.seq AS a_seq, a.name, b.year, b.month, b.totalSalary 
						FROM employeesManager a
						JOIN monthlySalary b ON a.seq = b.employeesManager_seq
						WHERE a.name = ?
						ORDER BY b.year DESC, b.month DESC 
						""";
				pstmt = conn.prepareStatement(selectOne);
				pstmt.setString(1, employeeName);
				rs = pstmt.executeQuery();
						
				NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.KOREA);
				int lastYear = -1;	//마지막 출력된 연도를 저장하는 변수
						
				while(rs.next()) {
					countRows++;
					int totalSalary = rs.getInt("totalSalary");
					int salaryYear = rs.getInt("year");
					int salaryMonth = rs.getInt("month");
					String formattedSalary = numberFormat.format(totalSalary) + "원";
							
					//연도가 바뀌면 헤더를 출략
					if (countRows == 1 ||salaryYear != lastYear) {
			             System.out.println("-----------------------------------------------------------");
			             System.out.println(salaryYear +"년 급여 목록");
			             System.out.println();
			             System.out.println("번호\t사원번호\t성명\t\t연도\t월\t\t급여");
			             System.out.println("-----------------------------------------------------------");
			             lastYear = salaryYear;
			            }
							
					System.out.println(
									rs.getString("b_seq") + "\t" +
			                        rs.getString("a_seq") + "\t" +
			                        rs.getString("name") + "\t\t" +
			                        rs.getString("year") +"년\t" +
			                        rs.getString("month") +"월\t" +
			                        formattedSalary
									) ;
					}	
					
					if (countRows ==0) {
						System.out.println("해당직원이 존재하지 않습니다.");
					}
				} else {
						System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
				}
			}catch (SQLException se) {
				se.printStackTrace();
				System.out.println("급여 조회 실행오류 -> " + se.getMessage());
			}finally {
				DBManager.dbClose(conn, pstmt,rs);
		}
	}
	
}
