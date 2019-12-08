package com.beans;
//註冊郵件的Bean屬性跟方法
public class RegisterBean {
	private String fname;
	private String lname;
	private String email;
	private String pword;
	private String hash;
	private int active; 
	
	public String getFName() {
		return fname;
	}
	public void setFName(String fname) {
		this.fname = fname;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getPword() {
		return pword;
	}
	public void setPword(String pword) {
		this.pword = pword;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String Hash) {
		this.hash = Hash;
	}
	
	public int getActive() {
		return active;
	}
	public void setHash(int active) {
		this.active =active;
	}
}
