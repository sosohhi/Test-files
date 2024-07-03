package com.sohee;


import java.io.FileInputStream;


public class Mini1Project {
	public static void main(String[] args) throws Exception{
		Planner planner = new Planner();
		
		planner.readFile();
		planner.showMenu();
	}
}
