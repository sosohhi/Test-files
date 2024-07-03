package com.sohee2;

import java.io.Serializable;
import java.util.Date;


public class MyPlan implements Serializable {
	private int pno;
	private Date date;	// 날짜
	private String stime;	//공부 시작 시간
	private String etime;	//공부 끝난 시간
	private String subject;	//과목
	private String learning; //내용
	
	
	
	public int getPno() {
		return pno;
	}
	public void setPno(int pno) {
		this.pno = pno;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getLearning() {
		return learning;
	}
	public void setLearning(String learning) {
		this.learning = learning;
	}
	
	
	

}
