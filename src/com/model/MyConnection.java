package com.model;

import java.sql.Connection;
import java.sql.DriverManager;

public class MyConnection {
	static Connection conn;
	
	public static Connection getconnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3305/root,root");//���o�X�ʸg�z�H
		}catch (Exception e) {
			System.out.println("getconnection�X�{���~:" +e.toString());
		}
		return conn;
	}
}
