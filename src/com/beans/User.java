package com.beans;

import java.util.ArrayList;

public class User {
	private int id;
	private String name; //姓名
	private String address; //地址
	private String email; //信箱
	private String username; //帳號
	private String password	; //密碼
	private String hash; //驗證碼
	private int active; //激活狀態
	
	
	//1.取得屬性方法,設定屬性方法
	public int getId() {
		return id;
	}
	public void setId(int id) { //設置id
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	
	//輸入用戶帳號取得地址
	public Object fetchadd(ArrayList<User> userList,String username) {//這個陣列我寫的User類別
		for(User u : userList) {
			if(u.getUsername().equals(username))  //如果取得的使用這帳號含有username的話
				return u.getAddress(); //回傳使用者地址	
		}
		return "";//其他狀況回傳空字串
				
	}
	//輸入用戶帳號取得email
	public Object fetchemail(ArrayList<User> userList, String username) { 
		for(User u : userList) {
			if(u.getUsername().equals(username)) 
				return u.getEmail();
		}
		return "";
	}
	//輸入用戶帳號取姓名
	public Object fetchname(ArrayList<User>userList, String username) {
		for(User u: userList) {
			if(u.getUsername().equals(username)) 
				return u.getName();
		}
		return "";
	}
	
}
