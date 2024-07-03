package com.sohee2;

import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;


public class Plans {

	public static void main(String[] args) throws Exception {
		 try {
	            StudyPlan studyPlan = new StudyPlan();
	            List<String> planList = loadFile(); // 프로그램 시작 시 파일 불러오기
	            studyPlan.showMenu();

	            
	        } catch (Exception e) {
	            System.out.println("오류가 발생했습니다: " + e.getMessage());
	        }
	    }

	
	private static List<String> loadFile() throws Exception {
        File file = new File("C:\\Users\\win11\\Desktop\\이클립스\\eclipse\\s_p\\StudyPlanner.db");
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            List<String> planList = (List<String>) ois.readObject();
            ois.close();
            System.out.println("파일이 불러와졌습니다.");
            return planList;
        } else {
            System.out.println("저장된 파일이 없습니다.");
            return null;
        }

	}
	}
