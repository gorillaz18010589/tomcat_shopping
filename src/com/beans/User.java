package com.beans;

import java.util.ArrayList;

public class User {
	private int id;
	private String name; //�m�W
	private String address; //�a�}
	private String email; //�H�c
	private String username; //�b��
	private String password	; //�K�X
	private String hash; //���ҽX
	private int active; //�E�����A
	
	
	//1.���o�ݩʤ�k,�]�w�ݩʤ�k
	public int getId() {
		return id;
	}
	public void setId(int id) { //�]�mid
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
	
	//��J�Τ�b�����o�a�}
	public Object fetchadd(ArrayList<User> userList,String username) {//�o�Ӱ}�C�ڼg��User���O
		for(User u : userList) {
			if(u.getUsername().equals(username))  //�p�G���o���ϥγo�b���t��username����
				return u.getAddress(); //�^�ǨϥΪ̦a�}	
		}
		return "";//��L���p�^�ǪŦr��
				
	}
	//��J�Τ�b�����oemail
	public Object fetchemail(ArrayList<User> userList, String username) { 
		for(User u : userList) {
			if(u.getUsername().equals(username)) 
				return u.getEmail();
		}
		return "";
	}
	//��J�Τ�b�����m�W
	public Object fetchname(ArrayList<User>userList, String username) {
		for(User u: userList) {
			if(u.getUsername().equals(username)) 
				return u.getName();
		}
		return "";
	}
	
}
