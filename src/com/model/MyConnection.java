package com.model;

import java.sql.Connection;
import java.sql.DriverManager;

public class MyConnection {
	static Connection conn;
	
	public static Connection getconnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3305/root,root");//取得驅動經理人
		}catch (Exception e) {
			System.out.println("getconnection出現錯誤:" +e.toString());
		}
		return conn;
	}
}
